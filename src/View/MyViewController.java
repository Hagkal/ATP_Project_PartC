package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class MyViewController implements IView, Observer {

    private MyViewModel viewModel;
    @FXML
    public Displayer display;
    public javafx.scene.control.TextField txt_rowsFromUser;
    public javafx.scene.control.TextField txt_colsFromUser;
    public javafx.scene.control.Button btn_generateButton;
    public javafx.scene.control.Button btn_solveButton;



    public void SetStageAboutEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Hagai and Omri");
        Optional<ButtonType> result = alert.showAndWait();
        /**if (result.get() == ButtonType.OK){
         // ... user chose OK
         // Close program
         } else {
         // ... user chose CANCEL or closed the dialog
         actionEvent.consume();
         }**/
    }

    public void SetStageHelpEvent(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Hagai and Omri");
        Optional<ButtonType> result = alert.showAndWait();
        /**if (result.get() == ButtonType.OK){
         // ... user chose OK
         // Close program
         } else {
         // ... user chose CANCEL or closed the dialog
         actionEvent.consume();
         }**/
    }

    /**
     * a method to set the ViewModel of the application
     * @param given - the given ViewModel
     */
    public void setViewModel(MyViewModel given){this.viewModel = given;}

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
        if (o == viewModel){
            display.display(viewModel.getMaze());
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



}

