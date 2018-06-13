package View;

import Server.Server;
import ViewModel.MyViewModel;
import algorithms.search.Solution;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseDragEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyEvent;
import sample.Main;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class MyViewController implements IView, Observer {

    private MyViewModel viewModel;
    @FXML
    public Display mazeDisplay;
    public Display solutionDisplay;
    public Display playerDisplay;
    public javafx.scene.control.TextField txt_rowsFromUser;
    public javafx.scene.control.TextField txt_colsFromUser;
    public javafx.scene.control.Button btn_generateButton;
    public javafx.scene.control.Button btn_solveButton;
    public javafx.scene.control.Label lbl_playerRow;
    public javafx.scene.control.Label lbl_playerCol;

    public void SetStageAboutEvent(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("ViewStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.setContentText("Welcome to our maze application!" + '\n' + "In this application you can generate a random maze and try to solve it your own, or you can ask the application to do it." + '\n'
        + "Maze generation made by Prim's algorithm and solved by DFS algorithm." + '\n' + "The programmers behind this application:" + '\n' + "Hagai Kalinhoff and Omri Naor");
        alert.setHeaderText("                    About");
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
        alert.setHeaderText("                    Help");
        alert.setTitle("Help");
        Optional<ButtonType> result = alert.showAndWait();
    }

    public void SetStagePropertiesEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("ViewStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.setHeaderText("             Properties");
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
        alert.setHeaderText("              New Game");
        alert.setTitle("New Game");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            generateMaze();
        } else {
            actionEvent.consume();
        }
    }


    /**
     * a method to set the ViewModel of the application
     * @param given - the given ViewModel
     */
    public void setViewModel(MyViewModel given){
        this.viewModel = given;
        setProperties();
    }

    /**
     * a method to bind the properties for display
     */
    private void setProperties() {
        lbl_playerRow.textProperty().bind(viewModel.playerRowPropertyProperty());
        lbl_playerCol.textProperty().bind(viewModel.playerColPropertyProperty());
    }

    /**
     * a method to generate a maze with the sizes inserted
     */
    public void generateMaze() {
        try{
            int row = Integer.valueOf(txt_rowsFromUser.getText());
            int col = Integer.valueOf(txt_colsFromUser.getText());
            btn_generateButton.setDisable(true);
            viewModel.generateMaze(row, col);
            btn_generateButton.setDisable(false);
            btn_solveButton.setDisable(false);

        } catch (NumberFormatException e){
            //e.printStackTrace();
            popProblem("Please insert a numeric value to maze sizes!");
        }


    }

    /**
     * a method that will move the player
     * @param pressed - the key pressed to move the player
     */
    public void movePlayer(KeyEvent pressed){
        viewModel.movePlayer(pressed.getCode());
        pressed.consume();
    }

    @Override
    public void update(Observable o, Object arg) {
        String args = (String) arg;
        if (o == viewModel && args.contains("mazeDisplay"))
            mazeDisplay.display(viewModel.getMaze());

        if (o == viewModel && args.contains("solutionDisplay"))
            solutionDisplay.display(viewModel.getMaze(), viewModel.getSolution());

        if (o == viewModel && args.contains("playerDisplay"))
            playerDisplay.display(viewModel.getMaze(), viewModel.getPlayerRow(), viewModel.getPlayerCol());

        if (o == viewModel && args.contains("WINNER")){
            /* functionality for finished game!
             * maybe cancel all other current maze related operations
             */
            Alert goodJob = new Alert(Alert.AlertType.INFORMATION);
            goodJob.setContentText("NICE :)\n You Win!");
            goodJob.showAndWait();


        }
    }

    /**
     * a method to pop errors with a description
     * @param description - of the error occured
     */
    private void popProblem(String description){
        Alert prob = new Alert(Alert.AlertType.ERROR);
        prob.setContentText(description);
        prob.showAndWait();
    }

    /**
     * a method to solve the maze
     * @param actionEvent - ignored click event
     */
    public void solveMaze(ActionEvent actionEvent) {
        btn_solveButton.setDisable(true);
        btn_solveButton.setDisable(true);
        viewModel.solve();
        btn_solveButton.setDisable(false);
        btn_generateButton.setDisable(false);
    }

    /**
     * a method to save the current game
     * @param actionEvent - ignored
     */
    public void saveGame(ActionEvent actionEvent) {
        if (viewModel.getMaze() == null)
            popProblem("You must generate a maze before saving it!");
        else
            viewModel.saveGame();
    }

    /**
     * a method to load a previously saved game
     */
    public void loadGame(){
        viewModel.loadGame();
    }


    public void dragOver(MouseDragEvent mouseDragEvent) {
        if (viewModel.getMaze()==null)
            return;
        System.out.println("In the method");
        double mouseX = mouseDragEvent.getX() / playerDisplay.getWidth();
        double mouseY = mouseDragEvent.getY() / playerDisplay.getHeight();

        if (Math.abs(viewModel.getPlayerRow() - mouseX) < 2 || Math.abs(viewModel.getPlayerCol() - mouseY) < 2){
            if (mouseX < viewModel.getPlayerCol())
                viewModel.movePlayer(KeyCode.LEFT);

            if (mouseX > viewModel.getPlayerCol())
                viewModel.movePlayer(KeyCode.RIGHT);

            if (mouseY < viewModel.getPlayerRow())
                viewModel.movePlayer(KeyCode.UP);

            if (mouseY > viewModel.getPlayerCol())
                viewModel.movePlayer(KeyCode.DOWN);
        }

    }
}

