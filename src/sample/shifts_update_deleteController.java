package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class shifts_update_deleteController {

    @FXML
    private AnchorPane shiftsAnchorPane;

    @FXML
    private TableView<Shift> showShiftsTable;
    @FXML
    private TableColumn<Shift, String> colShiftBatchUid;
    @FXML
    private TableColumn<Shift, String> colShiftSchedule;
    @FXML
    private TableColumn<Shift, String> colShiftDescription;

    @FXML
    private TextField showShiftBatchUid;
    @FXML
    private TextField showShiftSchedule;
    @FXML
    private TextArea showShiftDescription;

    private String shiftBatchUidData;
    private String shiftScheduleData;
    private String shiftDescriptionData;

    AppData newData = new AppData();

    // -- Search Button --
    public void shiftPaneSearch() throws SQLException {
        showShiftsTable.getItems().clear();
        shiftBatchUidData = showShiftBatchUid.getText().trim();
        shiftScheduleData = showShiftSchedule.getText().trim();
        shiftDescriptionData = showShiftDescription.getText().trim();

        // -- Define a list of Employee objects --
        List<Shift> shift;
        // -- Checks if there are any search parameters --
        if (shiftBatchUidData.isEmpty() & shiftScheduleData.isEmpty() & shiftDescriptionData.isEmpty()) {
            shift = newData.queryShift();
        } else {
            shift = newData.queryShift(shiftBatchUidData, shiftScheduleData, shiftDescriptionData);
        }
        // -- Checks if there are any results being returned from the query --
        if (shift.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("No results");
            alert.showAndWait();
        } else {
            for (Shift entry : shift) {
                showShiftsTable.getItems().addAll(entry);
                colShiftBatchUid.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBatch_uid()));
                colShiftSchedule.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime()));
                colShiftDescription.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));

            }
        }
    }

    // -- Something is selected in the table view --
    public void shiftSelected() {
        Shift selectedShift = showShiftsTable.getSelectionModel().getSelectedItem();
        if (selectedShift != null) {
            showShiftBatchUid.setText(selectedShift.getBatch_uid());
            showShiftSchedule.setText(selectedShift.getTime());
            showShiftDescription.setText(selectedShift.getDescription());
            System.out.println(selectedShift.getPk1());
        }
    }

    // -- Create Button --
    public void shiftPaneCreate() {
        // -- Creates and empty employee object --
        Shift newShift = new Shift();
        shiftBatchUidData = showShiftBatchUid.getText().trim();
        shiftScheduleData = showShiftSchedule.getText().trim();
        shiftDescriptionData = showShiftDescription.getText().trim();

        // -- Checks if there are any empty fields --
        if(shiftBatchUidData.isEmpty() || shiftScheduleData.isEmpty() || shiftDescriptionData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("One or more fields are empty. The records was not created.");
            alert.showAndWait();
        } else {
            try {
                // -- Use set methods to set the the values of the employee object created before --
                newShift.setBatch_uid(shiftBatchUidData);
                newShift.setTime(shiftScheduleData);
                newShift.setDescription(shiftDescriptionData);
                // -- Run the insertEmployee() to create the user in the DB. Assign the returned value to success --
                int success = newData.insertShift(newShift);
                // -- If it returns 0 is because 0 rows where modified thus the above was not execute --
                // -- If the above was a success then refresh the tableview to show the new data --
                if(success != 0) {
                    shiftPaneSearch();
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
    public void shiftPaneUpdate() throws SQLException {
        Shift selectedShift = showShiftsTable.getSelectionModel().getSelectedItem();
        if(selectedShift != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("UPDATE RECORD");
            alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && (result.get() == ButtonType.OK)) {
                /* -- If the user hit ok in the confirmation box it calls the overload method updateEmployee()
                 * and stores the returned value -- */
                int success = newData.updateShift(selectedShift, showShiftBatchUid.getText().trim(), showShiftSchedule.getText().trim()
                        , showShiftDescription.getText().trim());
                // If the value is different from 0 it was success, then it will refresh the tableview to show the updated record --
                if(success != 0){
                    shiftPaneSearch();
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
    public void shiftPaneDelete() throws SQLException {
        Shift selectedShift= showShiftsTable.getSelectionModel().getSelectedItem();
        if(selectedShift != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && (result.get() == ButtonType.OK)) {
                // -- Calls deleteEmployee() and then resets all the fields in the UI and refreshes the tableview --
                newData.deleteShift(selectedShift);
                shiftPaneReset();
                shiftPaneSearch();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Nothing is selected");
            alert.showAndWait();
        }
    }

    // -- Reset Button --
    public void shiftPaneReset() {
        showShiftBatchUid.clear();
        showShiftSchedule.clear();
        showShiftDescription.clear();
        showShiftsTable.getItems().clear();
    }

    // -- Exit Button. This changes the Scene to return to the Main Window --
    public void shiftExitHandler() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            AppData.sceneChange(shiftsAnchorPane.getScene(), "mainWindow.fxml");
        }
    }
}
