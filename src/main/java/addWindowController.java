import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class addWindowController implements Initializable {
    public addWindowController() throws IOException { }
    @FXML GridPane grid = new GridPane();
    @FXML Label d1,d2,d3,d4,d5,d6,d7,d8;
    @FXML ChoiceBox<String> b1,b2,b3,b4,b5,b6,b7,b8;
    @FXML TextArea f1,f2,f3,f4,f5,f6,f7,f8;
    @FXML Button a1;
    int numberofdefinition = 0;
    ObservableList<TextArea> fields = FXCollections.observableArrayList();
    ObservableList<ChoiceBox> boxes = FXCollections.observableArrayList();
    ObservableList<Label> defs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fields.addAll(f1,f2,f3,f4,f5,f6,f7,f8);
        boxes.addAll(b1,b2,b3,b4,b5,b6,b7,b8);
        defs.addAll(d1,d2,d3,d4,d5,d6,d7,d8);
        for(int i = 1;i<8;i++){
            fields.get(i).setVisible(false);
            boxes.get(i).setVisible(false);
            defs.get(i).setVisible(false);
        }
    }

    @FXML public void more(){
        fields.get(numberofdefinition).setVisible(true);
        boxes.get(numberofdefinition).setVisible(true);
        defs.get(numberofdefinition).setVisible(true);
        numberofdefinition++;
    }
}
