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
    @FXML Button a1,a2,a3,a4,a5,a6,a7,a8;
    int i = 0;
    ObservableList<Button> adds = FXCollections.observableArrayList();
    ObservableList<TextArea> fields = FXCollections.observableArrayList();
    ObservableList<ChoiceBox> boxes = FXCollections.observableArrayList();
    ObservableList<Label> defs = FXCollections.observableArrayList();

    @FXML public void more(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adds.add( a1);adds.add(a2);adds.add(a3);adds.add(a4);adds.add(a5);adds.add(a6);adds.add(a7);adds.add(a8);
        fields.addAll(f1,f2,f3,f4,f5,f6,f7,f8);
        boxes.addAll(b1,b2,b3,b4,b5,b6,b7,b8);
        defs.addAll(d1,d2,d3,d4,d5,d6,d7,d8);
        for(int i = 1;i<8;i++){
            adds.get(i).setVisible(false);
            fields.get(i).setVisible(false);
            boxes.get(i).setVisible(false);
            defs.get(i).setVisible(false);
        }
    }
}
