import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import java.io.IOException;
import java.util.Iterator;

public class DictionaryApplication extends Application{
    public DictionaryApplication() throws IOException {
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(300);
//        primaryStage.setMaxHeight(470);
        primaryStage.show();
        System.out.println(root);
    }
    @Override
    public void stop()throws Exception{
        for (Synthesizer synthesizer: appController.loadedSynthesizers ) {
            try {
                synthesizer.deallocate();
            } catch (EngineException ee) {
                System.out.println("Trouble closing the synthesizer: " + ee);
                ee.printStackTrace();
            }
        }
    }
    Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
    Scene scene = new Scene(root,600,600);


    public static void main(String[] args) {
        launch(args);
    }
}
