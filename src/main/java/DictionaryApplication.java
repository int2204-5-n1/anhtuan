import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryApplication extends Application{
    Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
    Scene scene = new Scene(root,600,600);
    public DictionaryApplication() throws IOException {
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(300);
//        primaryStage.setMaxHeight(470);
        primaryStage.show();
    }

    @Override
    public void stop()throws Exception{
        //Closing synthesizer for speaker
        for (Synthesizer synthesizer: appController.loadedSynthesizers ) {
            try {
                synthesizer.deallocate();
            } catch (EngineException ee) {
                System.out.println("Trouble closing the synthesizer: " + ee);
                ee.printStackTrace();
            }
        }
        appController controller= new appController();
        controller.writeFile(appController.filepath);

//        //Luu thay doi ra file
//        DictionaryManagement mng = new DictionaryManagement();
//        mng.dictionaryExportToFile();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
