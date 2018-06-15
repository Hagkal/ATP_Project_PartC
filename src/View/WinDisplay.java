package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WinDisplay extends Display {

    private StringProperty imageFileName = new SimpleStringProperty();

    public String getImageFileName() {
        return imageFileName.get();
    }

    public StringProperty imageFileNameProperty() {
        return imageFileName;
    }

    @Override
    public void display(Object... o) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0,0, getWidth(), getHeight());

        String s = (String) o[0];
        if (s.contains("Won"))
        try {
            String path = System.getProperty("user.dir");



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
