package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class pos_update_deleteController {

    @FXML
    private AnchorPane positionsAnchorPane;

    @FXML
    private TableView<Positions> showPosTable;
    @FXML
    private TableColumn<Positions, String> colPositionsBatchUid;
    @FXML
    private TableColumn<Positions, String> colPositionsTitle;
    @FXML
    private TableColumn<Positions, String> colPositionDescription;

    @FXML
    private TextField showPositionsBatchUid;
    @FXML
    private TextField showPositionsTitle;
    @FXML
    private TextArea showPositionsDescription;

    private String posBatchUidData;
    private String posTitleData;
    private String posDescriptionData;

    AppData newData = new AppData();

    // -- Search Button --
    public void posPaneSearch() throws SQLException {
        showPosTable.getItems().clear();
        posBatchUidData = showPositionsBatchUid.getText().trim();
        posTitleData = showPositionsTitle.getText().trim();
        posDescriptionData = showPositionsDescription.getText().trim();

        // -- Define a list of Employee objects --
        List<Positions> position;
        // -- Checks if there are any search parameters --
        if (posBatchUidData.isEmpty() & posTitleData.isEmpty() & posDescriptionData.isEmpty()) {
            position = newData.queryPosition();
        } else {
            position = newData.queryPosition(posBatchUidData, posTitleData, posDescriptionData);
        }
        // -- Checks if there are any results being returned from the query --
        if (position.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("No results");
            alert.showAndWait();
        } else {
            for (Positions entry : position) {
                showPosTable.getItems().addAll(entry);
                colPositionsBatchUid.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBatch_uid()));
                colPositionsTitle.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTittle()));
                colPositionDescription.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));

            }
        }
    }

    // -- Something is selected in the table view --
    public void positionSelected() {
        Positions selectedPosition = showPosTable.getSelectionModel().getSelectedItem();
        if (selectedPosition != null) {
            showPositionsBatchUid.setText(selectedPosition.getBatch_uid());
            showPositionsTitle.setText(selectedPosition.getTittle());
            showPositionsDescription.setText(selectedPosition.getDescription());
        }
    }

    // -- Create Button --
    public void posPaneCreate() {
        // -- Creates and empty employee object --
        Positions newPosition = new Positions();
        posBatchUidData = showPositionsBatchUid.getText().trim();
        posTitleData = showPositionsTitle.getText().trim();
        posDescriptionData = showPositionsDescription.getText().trim();

        // -- Checks if there are any empty fields --
        if(posBatchUidData.isEmpty() || posTitleData.isEmpty() || posDescriptionData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("One or more fields are empty. The records was not created.");
            alert.showAndWait();
        } else {
            try {
                // -- Use set methods to set the the values of the employee object created before --
                newPosition.setBatch_uid(posBatchUidData);
                newPosition.setTittle(posTitleData);
                newPosition.setDescription(posDescriptionData);
                // -- Run the insertEmployee() to create the user in the DB. Assign the returned value to success --
                int success = newData.insertPosition(newPosition);
                // -- If it returns 0 is because 0 rows where modified thus the above was not execute --
                // -- If the above was a success then refresh the tableview to show the new data --
                if(success != 0) {
                    posPaneSearch();
                }
            } catch (Exception e) {
                System.out.println("Empty or invalid value" + e.getCause());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    // -- Update Button. Needs a selected item from the table view --
    public void posPaneUpdate() throws SQLException {
        Positions selectedPosition = showPosTable.getSelectionModel().getSelectedItem();
        if(selectedPosition != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("UPDATE RECORD");
            alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && (result.get() == ButtonType.OK)) {
                /* -- If the user hit ok in the confirmation box it calls the overload method updateEmployee()
                 * and stores the returned value -- */
                int success = newData.updatePosition(selectedPosition, showPositionsBatchUid.getText().trim(), showPositionsTitle.getText().trim()
                        , showPositionsDescription.getText().trim());
                // If the value is different from 0 it was success, then it will refresh the tableview to show the updated record --
                if(success != 0){
                    posPaneSearch();
                }
            }
        } else {
            System.out.println("Nothing is selected");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Nothing is selected");
            alert.showAndWait();
        }
    }

    // -- Delete Button. Requires an item selected in the tableview --
    public void posPaneDelete() throws SQLException {
        Positions selectedPosition= showPosTable.getSelectionModel().getSelectedItem();
        if(selectedPosition != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && (result.get() == ButtonType.OK)) {
                // -- Calls deleteEmployee() and then resets all the fields in the UI and refreshes the tableview --
                newData.deletePosition(selectedPosition);
                posPaneReset();
                posPaneSearch();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Nothing is selected");
            alert.showAndWait();
        }
    }

    // -- Reset Button --
    public void posPaneReset() {
        showPositionsBatchUid.clear();
        showPositionsDescription.clear();
        showPositionsTitle.clear();
        showPosTable.getItems().clear();
    }

    // -- Exit Button. This changes the Scene to return to the Main Window --
    public void posExitHandler() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            AppData.sceneChange(positionsAnchorPane.getScene(), "mainWindow.fxml");
        }
    }
}
