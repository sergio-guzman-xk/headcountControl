package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    AppData newData = new AppData();

    public void performSearch () throws SQLException {
        showTable.getItems().clear();
        String employeeIdTextData = employeeIdTxtField.getText();
        String nationalIdTextData = nationalIdTxtField.getText().trim();
        String firstNameTextData = firstNameTxtField.getText().trim();
        String lastNameTextData = lastNameTxtField.getText().trim();
        String employeeEmailTextData = employeeEmailTxtField.getText().trim();
        String campaignNameTextData = campaignNameTxtField.getText().trim();
        String campaignIdTextData = campaignIdTxtField.getText().trim();
        String shiftTextData = shiftIdTxtField.getText().trim();

        List<SummarizeData> sumData;
        if (employeeIdTextData.isEmpty() & nationalIdTextData.isEmpty() & firstNameTextData.isEmpty() & lastNameTextData.isEmpty()
        & employeeEmailTextData.isEmpty() & campaignNameTextData.isEmpty() & campaignIdTextData.isEmpty() & shiftTextData.isEmpty()) {
            sumData = newData.querySummarize();
        } else {
            sumData = newData.querySummarize(employeeIdTextData, nationalIdTextData, firstNameTextData, lastNameTextData,
                    employeeEmailTextData, campaignNameTextData, campaignIdTextData, shiftTextData);
        }
        if (sumData.isEmpty()) {
            System.out.println("No results");
        } else {
            for (SummarizeData entry : sumData) {
                showTable.getItems().addAll(entry);
                colEmp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmployee_id()));
                colFirst.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirst_name()));
                colLast.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLast_name()));
                colCamp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCampaign_name()));
                colShift.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getShift()));
                colPos.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPosition()));

            }
        }
    }

    public void itemSelected () throws SQLException {
        SummarizeData selectedData = showTable.getSelectionModel().getSelectedItem();
        if(selectedData != null) {
            System.out.println("Item was selected");
            Employee employee = newData.queryEmployee(selectedData.getEmployees_pk1());
            Campaign campaign = newData.queryCampaign(selectedData.getCampaigns_pk1());
            Shift shift = newData.queryShift(selectedData.getShifts_pk1());
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
}
