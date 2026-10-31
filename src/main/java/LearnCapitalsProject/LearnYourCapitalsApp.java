package LearnCapitalsProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LearnYourCapitalsApp extends Application{

    public static void main(String[] args) {
        LearnYourCapitalsApp.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("LearnYourCapitals");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/LearnCapitalsProjectFXML/learncapitalsfrontpage.fxml"))));
        primaryStage.show();
    }
}



