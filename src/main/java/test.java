import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class test implements Initializable {
    private int rowIndex= 2,defnumber = 2;
    private ObservableList<String> choiceItems = FXCollections.observableArrayList();

    public ArrayList<String> selectedWordClasses = new ArrayList<>();

    @FXML GridPane grid = new GridPane();
    @FXML Button moreButton = new Button();
    @FXML Button addConfirmButton = new Button();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceItems.addAll("giới từ","tính từ","trạng từ","danh từ","động từ","đại từ","liên từ","từ hạn định","thán từ");

    }

    @FXML public void more(){
        Label label = new Label("ĐN" + defnumber);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setPrefWidth(150);
        choiceBox.getItems().addAll(choiceItems);

        TextArea textArea = new TextArea();

        grid.add(label,0,rowIndex);
        grid.add(choiceBox,1,rowIndex);
        grid.add(textArea,2,rowIndex);
        rowIndex++;
        defnumber++;
    }
}
