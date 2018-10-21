import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addWindowController implements Initializable {
    Dictionary dict = new Dictionary();
    private appController appController ;
    String word;
    int word_index;
    Boolean replaced = null;
    boolean generated = false;
    Word addedWord = new Word();
    @FXML Button addConfirmButton = new Button();
    @FXML HTMLEditor htmlEditor = new HTMLEditor();
    @FXML BorderPane root;
    @FXML TextField field = new TextField();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image confirmImage = new Image(getClass().getResourceAsStream("confirm.png"));
        addConfirmButton.setGraphic(new ImageView(confirmImage));
    }

    public void addConfirmation(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) root.getScene().getWindow();
//            getappController();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Thêm từ");
            alert.setHeaderText("Thêm từ sẽ làm thay đổi dữ liệu từ điển vĩnh viễn. Bạn có chắc muốn thêm từ?");
            alert.setTitle("Add alert.");

            Optional<ButtonType> option = alert.showAndWait();
            if(option.isPresent()) {
                if (ButtonType.OK == option.get()) {
                    word = field.getText();
                    addedWord.setWord_target(field.getText());
                    String s = htmlEditor.getHtmlText();
                    addedWord.setWord_explain(s);
                    System.out.println(s);

                    if(word.equals("")){
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setContentText("Không thể thêm từ.");
                        alert1.setHeaderText("Từ chưa được nhập!");
                        alert1.setTitle("Chua nhap");
                        alert1.showAndWait();
                    }
                    else{
                        if(searchWord(word)){
                            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                            alert1.setContentText("Từ đã tồn tại");
                            alert1.setHeaderText("Từ đã tồn tại. Chọn OK để thay thế từ có sẵn.");
                            Optional<ButtonType> optionex = alert1.showAndWait();
                            if(optionex.get() == ButtonType.OK){
                                //TODO: return chen/thay the, index
                                replaced = true;
                                stage.close();
                            }
                        }
                        else{
                            //TODO: return chen/thay the, index
                            replaced = false;
                            stage.close();
                        }
                    }


                } else if (option.get() == ButtonType.CANCEL) {
//                    appController.label.setText("Hủy thêm từ.");
                }
            }else{
//                appController.label.setText("Thêm từ thất bại.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getDict(Dictionary dictionary){
        if(!generated)
            this.dict.dictionary.addAll(dictionary.dictionary);
    }

    public boolean searchWord(String target){
        int n = dict.dictionary.size();
        int left = 0, right = n-1;
        while(left <=right){
            int mid = (left + right) / 2;
            if(dict.dictionary.get(mid).getWord_target().equalsIgnoreCase(target)){
                String s = dict.dictionary.get(mid).getWord_explain();
                System.out.println("found");
                word_index = mid;
                return true;
            }
            if(dict.dictionary.get(mid).getWord_target().compareToIgnoreCase(target) > 0){
                right = mid -1;
            }
            if(dict.dictionary.get(mid).getWord_target().compareToIgnoreCase(target) < 0){
                left = mid + 1;
            }
        }
        System.out.println("Not found");
        word_index = left;
        return false;
    }

    void getappController(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("app.fxml"));
        try{
            loader.load();
        }catch (
                IOException e){
            Logger.getLogger(appController.class.getName()).log(Level.SEVERE,null,e);
        }
        appController = loader.getController();
    }


}
