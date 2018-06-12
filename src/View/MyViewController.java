package View;

import Server.Server;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public class MyViewController implements IView {

    public javafx.scene.control.Button btn_generateMaze;
    public javafx.scene.control.Button btn_solveMaze;

    public void SetStageAboutEvent(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("ViewStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.setContentText("Welcome to our maze application!" + '\n' + "In this application you can generate a random maze and try to solve it your own, or you can ask the application to do it." + '\n'
        + "Maze generation made by Prim's algorithm and solved by DFS algorithm." + '\n' + "The programmers behind this application:" + '\n' + "Hagai Kalinhoff and Omri Naor");
        alert.setHeaderText("About");
        alert.setTitle("About");
        Optional<ButtonType> result = alert.showAndWait();
    }

    public void SetStageHelpEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("ViewStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.setContentText("Maze Rules:" + '\n' + "1. The character can be moved only to empty cells (non-wall cells)" + '\n' +
                "2. In order to solve the maze, you need to reach the goal cell" + '\n' + '\n' +
        "Game Instructions:" + '\n' + "Use the NumPad numbers to move the character:" + '\n' +
        "UP - 8" + '\n' + "DOWN - 2" + '\n' + "RIGHT - 6" + '\n' + "LEFT - 4" + '\n'
        + "Diagonal Moves:" + '\n' + "UP-LEFT - 7" + '\n' + "DOWN-LEFT - 1" + '\n' + "UP-RIGHT - 9" + '\n' + "DOWN-RIGHT - 3" + '\n');
        alert.setHeaderText("Help");
        alert.setTitle("Help");
        Optional<ButtonType> result = alert.showAndWait();
    }

    public void SetStagePropertiesEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("ViewStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.setHeaderText("Properties");
        alert.setTitle("Properties");
        String mazeType = Server.Configurations.getProperty("mazeType");
        String searchingAlgorithm = Server.Configurations.getProperty("searchingAlgorithm");
        alert.setContentText("Maze type: " + mazeType + '\n' + "Searching Algorithm: " + searchingAlgorithm);
        Optional<ButtonType> result = alert.showAndWait();
    }

    public void SetStageNewEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("ViewStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.setContentText("Are you sure you want to generate a new maze?");
        alert.setHeaderText("New Game");
        alert.setTitle("New Game");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            generateMaze();
        } else {
            actionEvent.consume();
        }
    }

    public void generateMaze() {
        /*int heigth = Integer.valueOf(txtfld_rowsNum.getText());
        int width = Integer.valueOf(txtfld_columnsNum.getText());
        btn_generateMaze.setDisable(true);
        viewModel.generateMaze(width, heigth);
        */
        btn_solveMaze.setDisable(false);
    }
}

