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

public class assign_update_deleteController {

    @FXML
    private AnchorPane assignAnchorPane;

    @FXML
    private TableView<SummarizeData> showAssignTable;
    @FXML
    private TableColumn<SummarizeData, String> colAssignBatchUid;
    @FXML
    private TableColumn<SummarizeData, String> colAssignEmpID;
    @FXML
    private TableColumn<SummarizeData, String> colAssignFirstName;
    @FXML
    private TableColumn<SummarizeData, String> colAssignLastName;
    @FXML
    private TableColumn<SummarizeData, String> colAssignCamp;
    @FXML
    private TableColumn<SummarizeData, String> colAssignShift;
    @FXML
    private TableColumn<SummarizeData, String> colAssignPos;
    @FXML
    private TableColumn<SummarizeData, String> colAssignStartDate;

    @FXML
    private TextField showAssignBatchUid;
    @FXML
    private TextField showAssignEmpBatchUid;
    @FXML
    private TextField showAssignCampBatchUid;
    @FXML
    private TextField showAssignShiftBatchUid;
    @FXML
    private TextField showAssignPosBatchUid;
    @FXML
    private TextField showAssignStartDate;

    private String assignBatchUidData;
    private String assignEmpBatchUidData;
    private String assignCampBatchUidData;
    private String assignShiftBatchUidData;
    private String assignPosBatchUidData;
    private String assignStartDateData;

    AppData newData = new AppData();

    // -- Search Button is pressed on the main window --
    public void assignPaneSearch() throws SQLException {
        showAssignTable.getItems().clear();
        assignBatchUidData = showAssignBatchUid.getText().trim();
        assignEmpBatchUidData = showAssignEmpBatchUid.getText().trim();
        assignCampBatchUidData = showAssignCampBatchUid.getText().trim();
        assignShiftBatchUidData = showAssignShiftBatchUid.getText().trim();
        assignPosBatchUidData = showAssignPosBatchUid.getText().trim();
        assignStartDateData = showAssignStartDate.getText().trim();

        List<SummarizeData> sumData;
        // -- If the master fields are empty or not --
        if (assignBatchUidData.isEmpty() & assignEmpBatchUidData.isEmpty() & assignCampBatchUidData.isEmpty() & assignShiftBatchUidData.isEmpty()
                & assignPosBatchUidData.isEmpty() & assignStartDateData.isEmpty()) {
            sumData = newData.querySummarize();
        } else {
            sumData = newData.querySummarize(assignBatchUidData, assignEmpBatchUidData, assignCampBatchUidData, assignShiftBatchUidData,
                    assignPosBatchUidData, assignStartDateData);
        }
        // -- Checks if there are records returned by the search. If so, print them in the tableview --
        if (sumData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("No results");
            alert.showAndWait();
        } else {
            for (SummarizeData entry : sumData) {
                showAssignTable.getItems().addAll(entry);
                colAssignBatchUid.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBatch_uid()));
                colAssignEmpID.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmployee_id()));
                colAssignFirstName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirst_name()));
                colAssignLastName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLast_name()));
                colAssignCamp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCampaign_name()));
                colAssignShift.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getShift()));
                colAssignPos.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPosition()));
                colAssignStartDate.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getStartDate())));
            }
        }
    }

    // -- When an item is selected in the tableview it shows the individual data in the other sections of the main interface --
    public void assignSelected() throws SQLException {
        // -- Gets the selected item and stores it in a SummarizeData object --
        SummarizeData selectedData = showAssignTable.getSelectionModel().getSelectedItem();
        // -- Checks if anything is selected --
        if(selectedData != null) {
            /* -- Based on the data in the employees_campaigns table the app creates an Employee, Campaign, Shift objects
             * by using the query method on each table using the PK1 column on each table --*/
            Employee employee = newData.queryEmployee(selectedData.getEmployees_pk1());
            Campaign campaign = newData.queryCampaign(selectedData.getCampaigns_pk1());
            Shift shift = newData.queryShift(selectedData.getShifts_pk1());
            Positions position = newData.queryPosition(selectedData.getCampaigns_pk1());
            // -- Transforms the data gathered above and shows that data to the user --
            showAssignBatchUid.setText(selectedData.getBatch_uid());
            showAssignEmpBatchUid.setText(employee.getBatch_uid());
            showAssignCampBatchUid.setText(campaign.getBatch_uid());
            showAssignShiftBatchUid.setText(shift.getBatch_uid());
            showAssignPosBatchUid.setText(position.getBatch_uid());
            showAssignStartDate.setText(String.valueOf(selectedData.getStartDate()));
        }
    }

    // -- Create Button --
    public void assignPaneCreate() {
        // -- Creates and empty employee object --
        SummarizeData newAssign = new SummarizeData();
        assignBatchUidData = showAssignBatchUid.getText().trim();
        assignEmpBatchUidData = showAssignEmpBatchUid.getText().trim();
        assignCampBatchUidData = showAssignCampBatchUid.getText().trim();
        assignShiftBatchUidData = showAssignShiftBatchUid.getText().trim();
        assignPosBatchUidData = showAssignPosBatchUid.getText().trim();
        assignStartDateData = showAssignStartDate.getText().trim();

        // -- Checks if there are any empty fields --
        if(assignBatchUidData.isEmpty() || assignEmpBatchUidData.isEmpty() || assignCampBatchUidData.isEmpty() ||
                assignShiftBatchUidData.isEmpty() || assignPosBatchUidData.isEmpty() || assignStartDateData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("One or more fields are empty. The records was not created.");
            alert.showAndWait();
        } else {
            try {
                Date assignStartDateData = Date.valueOf(showAssignStartDate.getText().trim());
                try {
                    // -- Use set methods to set the the values of the employee object created before --
                    Employee employee = newData.queryEmployee(assignEmpBatchUidData);
                    Campaign campaign = newData.queryCampaign(assignCampBatchUidData);
                    Shift shift = newData.queryShift(assignShiftBatchUidData);
                    Positions position = newData.queryPosition(assignPosBatchUidData);

                    newAssign.setBatch_uid(assignBatchUidData);
                    newAssign.setEmployees_pk1(employee.getPk1());
                    newAssign.setCampaigns_pk1(campaign.getPk1());
                    newAssign.setShifts_pk1(shift.getPk1());
                    newAssign.setPositions_pk1(position.getPk1());
                    newAssign.setStartDate(assignStartDateData);
                    // -- Run the insertEmployee() to create the user in the DB. Assign the returned value to success --
                    int success = newData.insertSummarize(newAssign);
                    // -- If it returns 0 is because 0 rows where modified thus the above was not execute --
                    // -- If the above was a success then refresh the tableview to show the new data --
                    if(success != 0) {
                        assignPaneSearch();
                    }
                } catch (Exception e) {
                    System.out.println("Empty or invalid value" + e.getCause());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(e.getMessage());
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR: Date format must be yyyy-mm-dd. " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    // -- Update Button. Needs a selected item from the table view --
    public void assignPaneUpdate() {
        SummarizeData selectedAssign = showAssignTable.getSelectionModel().getSelectedItem();
        assignBatchUidData = showAssignBatchUid.getText().trim();
        assignEmpBatchUidData = showAssignEmpBatchUid.getText().trim();
        assignCampBatchUidData = showAssignCampBatchUid.getText().trim();
        assignShiftBatchUidData = showAssignShiftBatchUid.getText().trim();
        assignPosBatchUidData = showAssignPosBatchUid.getText().trim();
        if(selectedAssign != null) {
            try {
                // -- checks if the data is a value date format yyyy-mm-dd --
                Date newEmpConDate = Date.valueOf(showAssignStartDate.getText());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("UPDATE RECORD");
                alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && (result.get() == ButtonType.OK)) {
                    /* -- If the user hit ok in the confirmation box it calls the overload method updateEmployee()
                     * and stores the returned value -- */
                    Employee employee = newData.queryEmployee(assignEmpBatchUidData);
                    Campaign campaign = newData.queryCampaign(assignCampBatchUidData);
                    Shift shift = newData.queryShift(assignShiftBatchUidData);
                    Positions position = newData.queryPosition(assignPosBatchUidData);
                    int success = newData.updateSummarize(selectedAssign, assignBatchUidData, employee.getPk1(), campaign.getPk1(),
                            shift.getPk1(), newEmpConDate, position.getPk1());
                    // If the value is different from 0 it was success, then it will refresh the tableview to show the updated record --
                    if(success != 0){
                        assignPaneSearch();
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR: Date format must be yyyy-mm-dd. " + e.getMessage());
                alert.showAndWait();
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
    public void assignPaneDelete() throws SQLException {
        SummarizeData selectedAssign = showAssignTable.getSelectionModel().getSelectedItem();
        if(selectedAssign != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && (result.get() == ButtonType.OK)) {
                // -- Calls deleteEmployee() and then resets all the fields in the UI and refreshes the tableview --
                newData.deleteSummarize(selectedAssign);
                assignPaneReset();
                assignPaneSearch();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Nothing is selected");
            alert.showAndWait();
        }
    }

    // -- Reset Button --
    public void assignPaneReset() {
        showAssignBatchUid.clear();
        showAssignEmpBatchUid.clear();
        showAssignCampBatchUid.clear();
        showAssignShiftBatchUid.clear();
        showAssignPosBatchUid.clear();
        showAssignStartDate.clear();
        showAssignTable.getItems().clear();
    }

    // -- Exit Button. This changes the Scene to return to the Main Window --
    public void assignExitHandler() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            AppData.sceneChange(assignAnchorPane.getScene(), "mainWindow.fxml");
        }
    }
}
