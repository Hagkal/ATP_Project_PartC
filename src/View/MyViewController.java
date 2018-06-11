package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class MyViewController implements IView {

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
}

