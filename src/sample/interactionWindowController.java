package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/* -- This class is the controller for the intermediary window.
* It controls the scene change between the main window and secondary windows */
public class interactionWindowController {

    @FXML
    private DialogPane interactionWindowDiag;

    // -- Employee Button. Displays the employees window UI --
    public void employeeChangeRequest() throws IOException {
        sample.AppData.sceneChange(interactionWindowDiag.getScene(), "empl_update_delete.fxml");
    }

    public void campaignChangeRequest() throws IOException {
        sample.AppData.sceneChange(interactionWindowDiag.getScene(), "camp_update_delete.fxml");
    }

    public void assignmentChangeRequest() throws IOException {
        sample.AppData.sceneChange(interactionWindowDiag.getScene(), "assign_update_delete.fxml");
    }

    public void shiftChangeRequest() throws IOException {
        sample.AppData.sceneChange(interactionWindowDiag.getScene(), "shifts_update_delete.fxml");
    }

    public void positionChangeRequest() throws IOException {
        sample.AppData.sceneChange(interactionWindowDiag.getScene(), "pos_update_delete.fxml");
    }

    public void cancelChangeRequest() throws IOException {
        sample.AppData.sceneChange(interactionWindowDiag.getScene(), "mainWindow.fxml");
    }

}
