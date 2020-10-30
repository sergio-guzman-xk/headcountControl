package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class interactionWindowController {

    @FXML
    private DialogPane interactionWindowDiag;

    public void employeeChangeRequest () throws IOException {
        System.out.println("Employee Button was pressed. Do something");
        sample.Controller.sceneChange(interactionWindowDiag.getScene(), "empl_update_delete.fxml");
    }


//    public void sceneChange (Scene currentScene, String newFXML) throws IOException {
//        Window currentWindow = currentScene.getWindow();
//        Stage currentStage = (Stage) currentWindow;
//        currentStage.hide();
//        Parent root = FXMLLoader.load(getClass().getResource(newFXML));
//        Stage newStage = new Stage();
//        Scene newScene = new Scene(root);
//        newStage.setScene(newScene);
//        newStage.show();
//
//    }
}
