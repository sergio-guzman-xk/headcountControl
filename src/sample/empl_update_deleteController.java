package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class empl_update_deleteController {

    @FXML
    private TableView<Employee> showEmpTable;
    @FXML
    private TableColumn<Employee, String> colEmpBatchUid;
    @FXML
    private TableColumn<Employee, String> colEmpEmpID;
    @FXML
    private TableColumn<Employee, String> colEmpFirstName;
    @FXML
    private TableColumn<Employee, String> colEmpLastName;
    @FXML
    private TableColumn<Employee, String> colEmpEmail;
    @FXML
    private TableColumn<Employee, String> colEmpSalary;
    @FXML
    private TableColumn<Employee, String> colEmpConDate;
    @FXML
    private TableColumn<Employee, String> colEmpNatID;

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

    AppData newData = new AppData();

    public void employeePaneSearch() throws SQLException {
        showEmpTable.getItems().clear();
        String employeeBatchUidData = showEmpBatchUid.getText().trim();
        String employeeEmpIdData = showEmpEmpId.getText().trim();
        String employeeFirstNameData = showEmpFirstN.getText().trim();
        String employeeLastNameData = showEmpLastN.getText().trim();
        String employeeEmailData = showEmpEmail.getText().trim();
        String employeeSalaryData = showEmpSalary.getText().trim();
        String employeeConDateData = showEmpConDate.getText().trim();
        String employeeNatIdData = showEmpNatId.getText().trim();

        List<Employee> employees;
        if (employeeBatchUidData.isEmpty() & employeeEmpIdData.isEmpty() & employeeFirstNameData.isEmpty() & employeeLastNameData.isEmpty()
                & employeeEmailData.isEmpty() & employeeSalaryData.isEmpty() & employeeConDateData.isEmpty() & employeeNatIdData.isEmpty()) {
            employees = newData.queryEmployee();
        } else {
            employees = newData.queryEmployee(employeeBatchUidData, employeeEmpIdData, employeeFirstNameData, employeeLastNameData,
                    employeeEmailData, employeeSalaryData, employeeConDateData, employeeNatIdData);
        }
        if (employees.isEmpty()) {
            System.out.println("No results");
        } else {
            for (Employee entry : employees) {
                showEmpTable.getItems().addAll(entry);
                colEmpBatchUid.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBatch_uid()));
                colEmpEmpID.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmployee_id()));
                colEmpFirstName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFirst_name()));
                colEmpLastName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLast_name()));
                colEmpEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
                colEmpSalary.setCellValueFactory(c -> new SimpleStringProperty(String.format("%.0f", c.getValue().getSalary())));
                colEmpConDate.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getContract_date())));
                colEmpNatID.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNational_id()));

            }
        }
    }

    public void employeeSelected() {

    }

}

