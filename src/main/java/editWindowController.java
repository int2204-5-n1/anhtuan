import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class editWindowController implements Initializable {
    private appController appController ;
    String word;
    Word editedWord = new Word();
    private Image confirmImage ;
    @FXML Button editConfirmButton = new Button();
    @FXML HTMLEditor htmlEditor = new HTMLEditor();
    @FXML BorderPane root = new BorderPane();
    @FXML Label label = new Label();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmImage = new Image(getClass().getResourceAsStream("confirm.png"));
        editConfirmButton.setGraphic(new ImageView(confirmImage));
    }

    public void editConfirmation(javafx.event.ActionEvent event) {
        try{
            Stage stage = (Stage) root.getScene().getWindow();
//            getappController();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Sửa từ");
            alert.setHeaderText("Sửa từ sẽ làm thay đổi dữ liệu từ điển vĩnh viễn. Bạn có chắc muốn sửa từ?");
            alert.setTitle("Edit alert.");

            Optional<ButtonType> option = alert.showAndWait();
            if(option.isPresent()) {
                if (ButtonType.OK == option.get()) {
                    editedWord.setWord_target(word);
                    String s = htmlEditor.getHtmlText();
                    editedWord.setWord_explain(s);
                    stage.close();
                } else if (option.get() == ButtonType.CANCEL) {
//                    appController.label.setText("Hủy sửa từ.");
                }
            }else{
//                appController.label.setText("Sửa từ thất bại.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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

    void getTarget(String target){
        this.word = target;
    }
}
