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
            System.out.println("EmpID: " + employeeIdTextData);
            sumData = newData.querySummarize(employeeIdTextData, nationalIdTextData, firstNameTextData, lastNameTextData,
                    employeeEmailTextData, campaignNameTextData, campaignIdTextData, shiftTextData);
        }
        /*for (SummarizeData entry : sumData) {
            System.out.println("Employee ID: " + entry.getEmployee_id() + " Name: " + entry.getFirst_name() + " " + entry.getLast_name() + " Campaign Name: " +
                    entry.getCampaign_name() + " Schedule: " + entry.getShift() + " Position: " + entry.getPosition());
        }*/
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
}
