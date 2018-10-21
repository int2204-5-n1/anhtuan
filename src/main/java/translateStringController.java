import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class translateStringController implements Initializable {
    @FXML TextArea in,out;
    @FXML Label from,to;
    @FXML Button swapButton;
    @FXML Button transButton;
    String langFrom,langTo;

    @Override
    public void initialize(URL URL, ResourceBundle rb){
        langFrom = "en";
        langTo = "vi";
        Image searchImage = new Image(getClass().getResourceAsStream("search.png"));
        Image swapImage = new Image(getClass().getResourceAsStream("swap.png"));
        transButton.setGraphic(new ImageView(searchImage));
        swapButton.setGraphic(new ImageView(swapImage));
    }

    public void swapLang(javafx.event.ActionEvent event) {
        String from1 = langFrom;
        String to1 = langTo;
        langTo = from1;
        langFrom = to1;

        String fromText = from.getText();
        String toText = to.getText();
        to.setText(fromText);
        from.setText(toText);
    }


    public void translate(ActionEvent event) throws IOException {
        String input = in.getText();
        String output = Translator.translate(langFrom,langTo,input);
        out.setText(output);
    }
}
