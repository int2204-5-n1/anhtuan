import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryAddress implements Initializable {
    @FXML TextField add;
    static boolean typed = false;
    public void initialize(URL url, ResourceBundle rb) {

    }
    public void getAddress(ActionEvent event) {
        appController.filepath = add.getText();
        System.out.println(appController.filepath);
        typed = true;
        Stage stage = (Stage) add.getParent().getScene().getWindow();
        stage.close();
    }
}
