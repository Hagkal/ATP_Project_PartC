package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/MyView.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("../View/ViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        /** Background music **/
        /*Media sound = new Media(new File("Resources/Muse.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();*/


        /** Change scene dimensions **/
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = (double) newValue / 2;
            root.prefHeight(height);
        });
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = (double) newValue / 2;
            root.prefWidth(width);
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
