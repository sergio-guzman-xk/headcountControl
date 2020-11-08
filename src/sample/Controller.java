package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

// -- This class is the controller for the primary window --
public class Controller {

    @FXML
    private TableView<SummarizeData> showTable;
    @FXML
    private TableColumn<SummarizeData, String> colEmp;
    @FXML
    private TableColumn<SummarizeData, String> colFirst;
    @FXML
    private TableColumn<SummarizeData, String> colLast;
    @FXML
    private TableColumn<SummarizeData, String> colCamp;
    @FXML
    private TableColumn<SummarizeData, String> colShift;
    @FXML
    private TableColumn<SummarizeData, String> colPos;
    @FXML
    private TableColumn<SummarizeData, String> colStartDate;

    @FXML
    private TextField employeeIdTxtField;
    @FXML
    private TextField nationalIdTxtField;
    @FXML
    private TextField firstNameTxtField;
    @FXML
    private TextField lastNameTxtField;
    @FXML
    private TextField employeeEmailTxtField;
    @FXML
    private TextField campaignNameTxtField;
    @FXML
    private TextField campaignIdTxtField;
    @FXML
    private TextField shiftIdTxtField;
    @FXML
    private TextField showEmpBatchUid;
    @FXML
    private TextField showEmpEmpId;
    @FXML
    private TextField showEmpFirstN;
    @FXML
    private TextField showEmpLastN;
    @FXML
    private TextField showEmpEmail;
    @FXML
    private TextField showEmpSalary;
    @FXML
    private TextField showEmpConDate;
    @FXML
    private TextField showEmpNatId;
    @FXML
    private TextField showCampBatchUid;
    @FXML
    private TextField showCampCampId;
    @FXML
    private TextField showCampCampN;
    @FXML
    private TextField showCampHeadC;
    @FXML
    private TextField showCampRev;
    @FXML
    private TextField showCampPriority;
    @FXML
    private TextField showCampConRen;
    @FXML
    private TextField showShiftBatchUid;
    @FXML
    private TextField showShiftDesc;
    @FXML
    private TextField showShiftSchedule;

    @FXML
    private VBox mainWindow;


    AppData newData = new AppData();

    // -- Search Button is pressed on the main window --
    public void performSearch() throws SQLException {
        showTable.getItems().clear();
        String employeeIdTextData = employeeIdTxtField.getText().trim();
        String nationalIdTextData = nationalIdTxtField.getText().trim();
        String firstNameTextData = firstNameTxtField.getText().trim();
        String lastNameTextData = lastNameTxtField.getText().trim();
        String employeeEmailTextData = employeeEmailTxtField.getText().trim();
        String campaignNameTextData = campaignNameTxtField.getText().trim();
        String campaignIdTextData = campaignIdTxtField.getText().trim();
        String shiftTextData = shiftIdTxtField.getText().trim();

        List<SummarizeData> sumData;
        // -- If the master fields are empty or not --
        if (employeeIdTextData.isEmpty() & nationalIdTextData.isEmpty() & firstNameTextData.isEmpty() & lastNameTextData.isEmpty()
        & employeeEmailTextData.isEmpty() & campaignNameTextData.isEmpty() & campaignIdTextData.isEmpty() & shiftTextData.isEmpty()) {
            sumData = newData.querySummarize();
        } else {
            sumData = newData.querySummarize(employeeIdTextData, nationalIdTextData, firstNameTextData, lastNameTextData,
                    employeeEmailTextData, campaignNameTextData, campaignIdTextData, shiftTextData);
        }
        // -- Checks if there are records returned by the search. If so, print them in the tableview --
        if (sumData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("No results");
            alert.showAndWait();
        } else {
            for (SummarizeData entry : sumData) {
                showTable.getItems().addAll(entry);
                colEmp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmployee_id()));
                colFirst.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirst_name()));
                colLast.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLast_name()));
                colCamp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCampaign_name()));
                colShift.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getShift()));
                colPos.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPosition()));
                colStartDate.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getStartDate())));
            }
        }
    }

    // -- When an item is selected in the tableview it shows the individual data in the other sections of the main interface --
    public void itemSelected() throws SQLException {
        // -- Gets the selected item and stores it in a SummarizeData object --
        SummarizeData selectedData = showTable.getSelectionModel().getSelectedItem();
        // -- Checks if anything is selected --
        if(selectedData != null) {
            /* -- Based on the data in the employees_campaigns table the app creates an Employee, Campaign, Shift objects
             * by using the query method on each table using the PK1 column on each table --*/
            Employee employee = newData.queryEmployee(selectedData.getEmployees_pk1());
            Campaign campaign = newData.queryCampaign(selectedData.getCampaigns_pk1());
            Shift shift = newData.queryShift(selectedData.getShifts_pk1());
            // -- Transforms the data gathered above and shows that data to the user --
            showEmpBatchUid.setText(employee.getBatch_uid());
            showEmpEmpId.setText(employee.getEmployee_id());
            showEmpFirstN.setText(employee.getFirst_name());
            showEmpLastN.setText(employee.getLast_name());
            showEmpEmail.setText(employee.getEmail());
            showEmpSalary.setText(String.format("%.0f",employee.getSalary()));
            showEmpConDate.setText(String.valueOf(employee.getContract_date()));
            showEmpNatId.setText(employee.getNational_id());
            showCampBatchUid.setText(campaign.getBatch_uid());
            showCampCampId.setText(campaign.getCampaign_id());
            showCampCampN.setText(campaign.getName());
            showCampConRen.setText(String.valueOf(campaign.getRenew_date()));
            showCampRev.setText(String.format("%.0f", campaign.getRevenue()));
            showCampHeadC.setText(String.format("%.0f", campaign.getHeadcount_req()));
            showCampPriority.setText(campaign.getPriority());
            showShiftBatchUid.setText(shift.getBatch_uid());
            showShiftDesc.setText(shift.getDescription());
            showShiftSchedule.setText(shift.getTime());
        }
    }

    // -- Delete/Update/create button is clicked. Calls a scene change --
    public void interactionRequest() throws IOException {
//        Scene currentScene = mainWindow.getScene();
//        Window currentWindow = currentScene.getWindow();
//        Stage currentStage = (Stage) currentWindow;
//        Parent root = FXMLLoader.load(getClass().getResource("interactionWindow.fxml"));
//        Stage newStage = new Stage();
//        Scene newScene = new Scene(root);
//        newStage.setScene(newScene);
//        currentStage.hide();
//        newStage.show();
        AppData.sceneChange(mainWindow.getScene(), "interactionWindow.fxml");
    }

    // -- Exit button --
    public void handleExit() {
        // add confirmation box!!!!!!!!!!!!!!!!!!!
        Platform.exit();
    }

    // -- Reset button --
    public void resetGrid() {
        showTable.getItems().clear();
        employeeIdTxtField.clear();
        nationalIdTxtField.clear();
        firstNameTxtField.clear();
        lastNameTxtField.clear();
        employeeEmailTxtField.clear();
        campaignNameTxtField.clear();
        campaignIdTxtField.clear();
        shiftIdTxtField.clear();
        showEmpBatchUid.clear();
        showEmpEmpId.clear();
        showEmpFirstN.clear();
        showEmpLastN.clear();
        showEmpEmail.clear();
        showEmpSalary.clear();
        showEmpConDate.clear();
        showEmpNatId.clear();
        showCampBatchUid.clear();
        showCampCampId.clear();
        showCampCampN.clear();
        showCampHeadC.clear();
        showCampRev.clear();
        showCampPriority.clear();
        showCampConRen.clear();
        showShiftBatchUid.clear();
        showShiftDesc.clear();
        showShiftSchedule.clear();
    }
}
