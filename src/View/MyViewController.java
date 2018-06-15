package View;

import Server.Server;
import ViewModel.MyViewModel;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseDragEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
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
    public Display winDisplay;
    public Pane pane;
    public javafx.scene.control.TextField txt_rowsFromUser;
    public javafx.scene.control.TextField txt_colsFromUser;
    public javafx.scene.control.Button btn_generateButton;
    public javafx.scene.control.Button btn_solveButton;
    public javafx.scene.control.Label lbl_playerRow;
    public javafx.scene.control.Label lbl_playerCol;
    public javafx.scene.layout.BorderPane lyt_mainPane;

    public void SetStageAboutEvent(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            stage.setTitle("About");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            scene.getStylesheets().add(getClass().getResource("About.css").toExternalForm());
            stage.setScene(scene);
            AboutController a = fxmlLoader.getController();
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }

    public void SetStageHelpEvent(ActionEvent actionEvent) {
        String content = "Maze Rules:" + '\n' + "1. The character can be moved only to empty cells (non-wall cells)" + '\n' +
                "2. In order to solve the maze, you need to reach the goal cell" + '\n' + '\n' +
                "Game Instructions:" + '\n' + "Use the NumPad numbers to move the character:" + '\n' +
                "UP - 8       DOWN - 2" + '\n' + "RIGHT - 6       LEFT - 4" + '\n'
                + "Diagonal Moves:" + '\n' + "UP-LEFT - 7       DOWN-LEFT - 1" + '\n' + "UP-RIGHT - 9       DOWN-RIGHT - 3" + '\n';
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setContent(new Label(content));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        dialogPane.getStylesheets().add(
                getClass().getResource("Help.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.setResizable(true);
        alert.setHeaderText("                    Help");
        alert.setTitle("Help");

        alert.show();
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
        if (result.get() == ButtonType.OK) {
            generateMaze();
        }
        actionEvent.consume();
    }

    public void setOnCloseRequest(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to leave?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            viewModel.exitGame();
            System.exit(0);
        } else {
            // ... user chose CANCEL or closed the dialog
            actionEvent.consume();
        }

    }


    /**
     * a method to set the ViewModel of the application
     *
     * @param given - the given ViewModel
     */
    public void setViewModel(MyViewModel given) {
        this.viewModel = given;
        setProperties();
    }

    /**
     * a method to bind the properties for display
     */
    private void setProperties() {
        lbl_playerRow.textProperty().bind(viewModel.playerRowPropertyProperty());
        lbl_playerCol.textProperty().bind(viewModel.playerColPropertyProperty());
/*
        btn_generateButton.prefHeightProperty().bind(lyt_mainPane.getLeft().layoutYProperty());
        btn_generateButton.prefWidthProperty().bind(lyt_mainPane.getLeft().layoutXProperty());
        */
    }

    /**
     * a method to generate a maze with the sizes inserted
     */
    private void generateMaze() {
        try {
            int row = Integer.valueOf(txt_rowsFromUser.getText());
            int col = Integer.valueOf(txt_colsFromUser.getText());
            btn_generateButton.setDisable(true);
            viewModel.generateMaze(row, col);
            btn_generateButton.setDisable(false);
            btn_solveButton.setDisable(false);


        } catch (NumberFormatException e) {
            //e.printStackTrace();
            popProblem("Please insert a numeric value to maze sizes!");
        }


    }

    /**
     * a method that will move the player
     *
     * @param pressed - the key pressed to move the player
     */
    public void movePlayer(KeyEvent pressed) {
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

        if (o == viewModel && args.contains("WINNER")) {
            /* functionality for finished game!
             * maybe cancel all other current maze related operations
             */

            if (args.contains("Paint")) {
                winDisplay.display("Won");
                Alert goodJob = new Alert(Alert.AlertType.INFORMATION);
                goodJob.setContentText("NICE :)\n You Win!");
                goodJob.showAndWait();
            }
            else
                winDisplay.display("clear");
        }
    }

    /**
     * a method to pop errors with a description
     *
     * @param description - of the error occured
     */
    private void popProblem(String description) {
        Alert prob = new Alert(Alert.AlertType.ERROR);
        prob.setContentText(description);
        prob.showAndWait();
    }

    /**
     * a method to solve the maze
     *
     * @param actionEvent - ignored click event
     */
    public void solveMaze(ActionEvent actionEvent) {
        btn_solveButton.setDisable(true);
        btn_solveButton.setDisable(true);
        viewModel.solve();
        btn_solveButton.setDisable(false);
        btn_generateButton.setDisable(false);
        actionEvent.consume();
    }

    /**
     * a method to save the current game
     *
     * @param actionEvent - ignored
     */
    public void saveGame(ActionEvent actionEvent) {
        if (viewModel.getMaze() == null)
            popProblem("You must generate a maze before saving it!");
        else
            viewModel.saveGame();

        actionEvent.consume();
    }

    /**
     * a method to load a previously saved game
     */
    public void loadGame(ActionEvent actionEvent) {
        if (viewModel.loadGame())
            btn_solveButton.setDisable(false);
        actionEvent.consume();
    }


    public void dragOver(MouseDragEvent mouseDragEvent) {
        if (viewModel.getMaze() == null)
            return;
        System.out.println("In the method");
        double mouseX = mouseDragEvent.getX() / playerDisplay.getWidth();
        double mouseY = mouseDragEvent.getY() / playerDisplay.getHeight();

        if (Math.abs(viewModel.getPlayerRow() - mouseX) < 2 || Math.abs(viewModel.getPlayerCol() - mouseY) < 2) {
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


    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                display(newSceneWidth.doubleValue(), scene.getHeight());
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                display(scene.getWidth(), newSceneHeight.doubleValue());
            }
        });
    }


    public void setMaxMinEvent(Stage stage){
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> display(0, 0));
    }

    private void display(double width, double height) {
        mazeDisplay.display(viewModel.getMaze());
        solutionDisplay.display(viewModel.getMaze(), viewModel.getSolution());
        playerDisplay.display(viewModel.getMaze(), viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }
}

