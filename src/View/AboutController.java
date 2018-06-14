package View;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    public javafx.scene.layout.BorderPane bp;
    public javafx.scene.control.Label lbl_about;
    public javafx.scene.image.ImageView img_about;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String content = "Welcome to our maze application!" + '\n' + "In this application you can generate a random maze and try to solve it your own, or you can ask the application to do it." + '\n' +
                "Maze generation made by Prim's algorithm and solved by DFS algorithm." + '\n' + "The programmers behind this application:" + '\n' + "Hagai Kalinhoff and Omri Naor";
        lbl_about.setText(content);
        lbl_about.setMinWidth(Region.USE_PREF_SIZE);
        Image img = new Image("file:Resources/aboutus.jpeg");
        img_about.setImage(img);
    }
}
