import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class appController implements Initializable {
    Dictionary dict = new Dictionary();

    @FXML private ListView<Word> wordList = new ListView<Word>(dict.dictionary);
    @FXML private WebView webView = new WebView();
    @FXML private TextField search = new TextField();
    @FXML private Button searchButton = new Button();
    @FXML Button speakerButton = new Button();
    @FXML Label label = new Label();
    @FXML Button deleteButton = new Button(), addButton = new Button();

    static Synthesizer synthesizer;
    static Set<Synthesizer> loadedSynthesizers = new HashSet<>();
    WebEngine engine;
    private Image searchImage,speakerImage;

    private String word;
    private int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //TODO: Adding word target to wordList from file
            loadData("ev");
            wordList.getItems().addAll(dict.dictionary);
            //Set image for search button
            searchImage = new Image(getClass().getResourceAsStream("search.png"));
            searchButton.setGraphic(new ImageView(searchImage));

            //Set image for speaker button
            speakerImage = new Image(getClass().getResourceAsStream("speaker.png"));
            speakerButton.setGraphic(new ImageView(speakerImage));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void wordItemClicked(MouseEvent event) {
        engine = webView.getEngine();
        String s = wordList.getSelectionModel().getSelectedItems().get(0).getWord_explain();
        engine.loadContent(s);
        word = wordList.getSelectionModel().getSelectedItems().get(0).getWord_target();
        index = wordList.getSelectionModel().getSelectedIndex();
    }

    @FXML
    public void searchByTextField(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            WebEngine engine = webView.getEngine();
            engine.loadContent(search.getText());
            searchWord(search.getText());
            word = search.getText();
        }
    }

    @FXML
    public void searchByButton(ActionEvent event) {
        searchButton.setFocusTraversable(false);
        String target = search.getText();
        searchWord(target);
        word = target;
    }

    @FXML
    public void deleteConfirmation(ActionEvent event){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Xóa từ: " + word + ".Khởi động lại từ điển để lưu thay đổi.");
            alert.setHeaderText("Xóa từ sẽ làm thay đổi dữ liệu từ điển vĩnh viễn. Bạn có chắc muốn xóa từ: " + word + "?");
            alert.setTitle("Delete alert.");

            Optional<ButtonType> option = alert.showAndWait();
            if (ButtonType.OK == option.get()) {
                deleteWord();
                this.label.setText("Đã xóa " + word);
            } else if (option.get() == ButtonType.CANCEL) {
                this.label.setText("Hủy xóa từ.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void addConfirmation(ActionEvent event){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Thêm từ. Khởi động lại từ điển để lưu thay đổi.");
            alert.setHeaderText("Thêm từ sẽ làm thay đổi dữ liệu từ điển vĩnh viễn. Bạn có chắc muốn thêm từ?");
            alert.setTitle("Add alert.");

            Optional<ButtonType> option = alert.showAndWait();
            if (ButtonType.OK == option.get()) {
                //TODO: cửa sổ thêm từ
                addWordWindow();
            } else if (option.get() == ButtonType.CANCEL) {
                this.label.setText("Hủy thêm từ.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Code lấy từ bài viết https://www.geeksforgeeks.org/converting-text-speech-java/
     */
    @FXML
    void speak() {
        try
        {
            // set property as Kevin Dictionary
            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            // Register Engine
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            // Create a Synthesizer
            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
           synthesizer.resume();

            // speaks the given text until queue is empty.
            synthesizer.speakPlainText(word, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            loadedSynthesizers.add(synthesizer);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void loadData(String language) {
        DictionaryManagement.dictsize = 0;
        BufferedReader br = null;
        try {
            if (language.equalsIgnoreCase("ev")) {
                br = new BufferedReader(new FileReader("C:\\Users\\OS\\Desktop\\E_V.txt"));
            }
            if (language.equalsIgnoreCase("custom")) {
                br = new BufferedReader(new FileReader("C:\\Users\\OS\\Desktop\\dictionary.txt"));
            }
            String s = br.readLine();
            while (s != null) {
                Word word = new Word();
                word.setWord_target(s.split("<html>")[0]);
                word.setWord_explain("<html>" + s.split("<html>")[1]);
                dict.dictionary.add(word);
                s = br.readLine();
                DictionaryManagement.dictsize++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Binary search
    public void searchWord(String target) {
        WebEngine engine = webView.getEngine();
        int n = DictionaryManagement.dictsize;
        int left = 0, right = n-1;
        while(left <=right){
            int mid = (left + right) / 2;
            if(dict.dictionary.get(mid).getWord_target().equalsIgnoreCase(target)){
                String s = dict.dictionary.get(mid).getWord_explain();
                engine.loadContent(s);
                index = mid;
                return;
            }
            if(dict.dictionary.get(mid).getWord_target().compareToIgnoreCase(target) > 0){
                right = mid -1;
            }
            if(dict.dictionary.get(mid).getWord_target().compareToIgnoreCase(target) < 0){
                left = mid + 1;
            }
        }
        engine.loadContent("Not found.");
    }

    private void deleteWord(){
        try {
            wordList.getItems().remove(index);
            dict.dictionary.remove(index);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void addWord(Word word){
        dict.dictionary.add(word);
        dict.dictionary.sorted();
        wordList.getItems().add(word);
        wordList.getItems().sorted();
    }

    void addWordWindow() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Thêm từ");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addButton.getScene().getWindow());
        stage.showAndWait();
    }

}