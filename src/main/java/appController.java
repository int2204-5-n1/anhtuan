import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.textfield.TextFields;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class appController implements Initializable {
    static Dictionary dict = new Dictionary();

    /**
     * Dictionary application nodes
     */
    @FXML ListView<Word> wordList = new ListView<Word>(dict.dictionary);
    @FXML public  WebView webView = new WebView();
    @FXML private TextField search = new TextField();
    @FXML private Button searchButton = new Button();
    @FXML Button speakerButton = new Button();
    @FXML  Label label = new Label();
    @FXML Button deleteButton = new Button(), addButton = new Button();

    private static Synthesizer synthesizer;
    static Set<Synthesizer> loadedSynthesizers = new HashSet<>();
    WebEngine engine;
    private Image searchImage,speakerImage;
    ArrayList<String> possibleSearches;
    public static String filepath = "C:\\Users\\OS\\Documents\\E_V.txt";

    private String word="";
    public int index = -1;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //TODO: Adding word target to wordList from file
            loadData();
            wordList.getItems().addAll(dict.dictionary);
            //Set image for search button
            searchImage = new Image(getClass().getResourceAsStream("search.png"));
            searchButton.setGraphic(new ImageView(searchImage));

            //Set image for speaker button
            speakerImage = new Image(getClass().getResourceAsStream("speaker.png"));
            speakerButton.setGraphic(new ImageView(speakerImage));

            //Auto complete textfield
            possibleSearches = new ArrayList<>();
            for (Word word:dict.dictionary){
                possibleSearches.add(word.getWord_target());
            }
            TextFields.bindAutoCompletion(search,possibleSearches );
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
    public void add(ActionEvent event){
        try {
            addWordWindow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void edit(ActionEvent event){
        try{
            editWordWindow();
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
    @FXML
    void stringTranslate(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("translateString.fxml"));
        try{
            loader.load();
        }catch (IOException e){
            Logger.getLogger(appController.class.getName()).log(Level.SEVERE,null,e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    void loadData() {
        DictionaryManagement.dictsize = 0;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = null;
        BufferedReader br = null;
        try {
            File fileDir = new File(filepath);
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileDir), "UTF8"));
            String s = br.readLine();
            while (s != null) {
                Word word1 = new Word();
                word1.setWord_target(s.split("<html")[0]);
                word1.setWord_explain("<html" + s.split("<html")[1]);
                dict.dictionary.add(word1);
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
    boolean searchWord(String target) {
        WebEngine engine = webView.getEngine();
        int n = dict.dictionary.size();
        int left = 0, right = n-1;
        while(left <=right){
            int mid = (left + right) / 2;
            if(dict.dictionary.get(mid).getWord_target().equalsIgnoreCase(target)){
                String s = dict.dictionary.get(mid).getWord_explain();
                engine.loadContent(s);
                index = mid;
                return true;
            }
            if(dict.dictionary.get(mid).getWord_target().compareToIgnoreCase(target) > 0){
                right = mid -1;
            }
            if(dict.dictionary.get(mid).getWord_target().compareToIgnoreCase(target) < 0){
                left = mid + 1;
            }
        }
        engine.loadContent("<font color='#cc0000'><b>Not found.</b></font>");
        return false;
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

    void addWordWindow(){
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("addwindow.fxml"));
       try{
           loader.load();
       }catch (IOException e){
           Logger.getLogger(appController.class.getName()).log(Level.SEVERE,null,e);
       }
       addWindowController addWindowController = loader.getController();
       addWindowController.getDict(dict);
       addWindowController.generated = true;
       Parent root = loader.getRoot();
       Stage stage = new Stage();
       stage.setScene(new Scene(root));
       stage.show();
       stage.setOnHiding(new EventHandler< WindowEvent>(){
           @Override
           public void handle(WindowEvent event){
               //TODO: Apply thay doi len wordList
               if(addWindowController.replaced== null){
                   label.setText("Hủy thêm từ");
               }
               else if(addWindowController.replaced){
                   wordList.getItems().set(addWindowController.word_index,addWindowController.addedWord);
                   dict.dictionary.set(addWindowController.word_index, addWindowController.addedWord);
                   label.setText("Đã thay thế "+ addWindowController.addedWord.getWord_target());
               }else{
                   wordList.getItems().add(addWindowController.word_index, addWindowController.addedWord);
                   dict.dictionary.add(addWindowController.word_index, addWindowController.addedWord);
                   label.setText("Đã thêm "+ addWindowController.addedWord.getWord_target());
                   possibleSearches.add(addWindowController.addedWord.getWord_target());
                   TextFields.bindAutoCompletion(search,possibleSearches );
               }
           }
       });
    }

    void editWordWindow(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("editwindow.fxml"));
        try{
            loader.load();
        }catch (IOException e){
            Logger.getLogger(appController.class.getName()).log(Level.SEVERE,null,e);
        }
        editWindowController editWindowController = loader.getController();
        if(!word.equals("")) {
            editWindowController.label.setText(word);
            editWindowController.htmlEditor.setHtmlText(dict.dictionary.get(index).getWord_explain());
            editWindowController.editedWord.setWord_target(word);
            editWindowController.editedWord.setWord_explain(dict.dictionary.get(index).getWord_explain());
            editWindowController.getTarget(word);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Chưa có từ được chọn.");
            alert.setContentText("Không thể sửa từ.");
            alert.showAndWait();
            label.setText("Sửa từ thất bại");
            return;
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding(new EventHandler< WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
                //TODO: Apply thay doi len wordList
                wordList.getItems().set(index, editWindowController.editedWord);
                dict.dictionary.set(index, editWindowController.editedWord);
                label.setText("Đã sửa từ: "+editWindowController.editedWord.getWord_target());
//                System.out.println(editWindowController.editedWord.getWord_explain());
            }
        });
    }

    public  void writeFile(String filePath) throws IOException {
        FileOutputStream fo = new FileOutputStream(filePath,false);
        OutputStreamWriter streamWriter = new OutputStreamWriter(fo, StandardCharsets.UTF_8);
        for(Word word:wordList.getItems()){
            StringBuilder s = new StringBuilder();
            s.append(word.getWord_target()).append(word.getWord_explain());
            streamWriter.write(s.toString());
            streamWriter.write(System.getProperty("line.separator"));
        }
        streamWriter.flush();
        fo.close();
    }


}