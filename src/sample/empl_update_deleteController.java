package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// -- This is the controller for the employees window UI --
public class empl_update_deleteController {

    @FXML
    private AnchorPane employeeChangePane;

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

    private String employeeBatchUidData;
    private String employeeEmpIdData;
    private String employeeFirstNameData;
    private String employeeLastNameData;
    private String employeeEmailData;
    private String employeeSalaryData;
    private String employeeConDateData;
    private String employeeNatIdData;

    AppData newData = new AppData();

    // -- Search Button --
    public void employeePaneSearch() throws SQLException {
        showEmpTable.getItems().clear();
        employeeBatchUidData = showEmpBatchUid.getText().trim();
        employeeEmpIdData = showEmpEmpId.getText().trim();
        employeeFirstNameData = showEmpFirstN.getText().trim();
        employeeLastNameData = showEmpLastN.getText().trim();
        employeeEmailData = showEmpEmail.getText().trim();
        employeeSalaryData = showEmpSalary.getText().trim();
        employeeConDateData = showEmpConDate.getText().trim();
        employeeNatIdData = showEmpNatId.getText().trim();

        // -- Define a list of Employee objects --
        List<Employee> employees;
        // -- Checks if there are any search parameters --
        if (employeeBatchUidData.isEmpty() & employeeEmpIdData.isEmpty() & employeeFirstNameData.isEmpty() & employeeLastNameData.isEmpty()
                & employeeEmailData.isEmpty() & employeeSalaryData.isEmpty() & employeeConDateData.isEmpty() & employeeNatIdData.isEmpty()) {
            employees = newData.queryEmployee();
        } else {
            employees = newData.queryEmployee(employeeBatchUidData, employeeEmpIdData, employeeFirstNameData, employeeLastNameData,
                    employeeEmailData, employeeSalaryData, employeeConDateData, employeeNatIdData);
        }
        // -- Checks if there are any results being returned from the query --
        if (employees.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("No results");
            alert.showAndWait();
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

    // -- Something is selected in the table view --
    public void employeeSelected() {
        Employee selectedEmployee = showEmpTable.getSelectionModel().getSelectedItem();
        if(selectedEmployee != null) {
            showEmpBatchUid.setText(selectedEmployee.getBatch_uid());
            showEmpEmpId.setText(selectedEmployee.getEmployee_id());
            showEmpFirstN.setText(selectedEmployee.getFirst_name());
            showEmpLastN.setText(selectedEmployee.getLast_name());
            showEmpEmail.setText(selectedEmployee.getEmail());
            showEmpSalary.setText(String.format("%.0f",selectedEmployee.getSalary()));
            showEmpConDate.setText(String.valueOf(selectedEmployee.getContract_date()));
            showEmpNatId.setText(selectedEmployee.getNational_id());
            System.out.println(selectedEmployee.getPk1());
        }
    }

    // -- Create Button --
    public void employeePaneCreate() {
        // -- Creates and empty employee object --
        Employee newEmployee = new Employee();
        employeeBatchUidData = showEmpBatchUid.getText().trim();
        employeeEmpIdData = showEmpEmpId.getText().trim();
        employeeFirstNameData = showEmpFirstN.getText().trim();
        employeeLastNameData = showEmpLastN.getText().trim();
        employeeEmailData = showEmpEmail.getText().trim();
        employeeSalaryData = showEmpSalary.getText().trim();
        employeeConDateData = showEmpConDate.getText().trim();
        employeeNatIdData = showEmpNatId.getText().trim();

        // -- Checks if there are any empty fields --
        if(employeeBatchUidData.isEmpty() || employeeEmpIdData.isEmpty() || employeeFirstNameData.isEmpty() ||
                employeeLastNameData.isEmpty() || employeeEmailData.isEmpty() || employeeSalaryData.isEmpty() ||
                employeeConDateData.isEmpty() || employeeNatIdData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("One or more fields are empty. The records was not created.");
            alert.showAndWait();
        } else {
            try {
                // -- Use set methods to set the the values of the employee object created before --
                newEmployee.setBatch_uid(employeeBatchUidData);
                newEmployee.setEmployee_id(employeeEmpIdData);
                newEmployee.setFirst_name(employeeFirstNameData);
                newEmployee.setLast_name(employeeLastNameData);
                newEmployee.setEmail(employeeEmailData);
                newEmployee.setSalary(Double.parseDouble(employeeSalaryData));
                newEmployee.setContract_date(Date.valueOf(employeeConDateData));
                newEmployee.setNational_id(employeeNatIdData);
                // -- Run the insertEmployee() to create the user in the DB. Assign the returned value to success --
                int success = newData.insertEmployee(newEmployee);
                // -- If it returns 0 is because 0 rows where modified thus the above was not execute --
                // -- If the above was a success then refresh the tableview to show the new data --
                if(success != 0) {
                    employeePaneSearch();
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
    public void employeePaneUpdate() {
        Employee selectedEmployee = showEmpTable.getSelectionModel().getSelectedItem();
        if(selectedEmployee != null) {
            try {
                // -- checks if the Salary is a valid double --
                Double newEmpSalary = Double.parseDouble(showEmpSalary.getText());
                try {
                    // -- checks if the data is a value date format yyyy-mm-dd --
                    Date newEmpConDate = Date.valueOf(showEmpConDate.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("UPDATE RECORD");
                    alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && (result.get() == ButtonType.OK)) {
                        /* -- If the user hit ok in the confirmation box it calls the overload method updateEmployee()
                        * and stores the returned value -- */
                        int success = newData.updateEmployee(selectedEmployee, showEmpBatchUid.getText().trim(), showEmpEmpId.getText().trim(),
                                showEmpFirstN.getText().trim(), showEmpLastN.getText().trim(), showEmpEmail.getText().trim(),
                                newEmpSalary, newEmpConDate, showEmpNatId.getText().trim());
                        // If the value is different from 0 it was success, then it will refresh the tableview to show the updated record --
                        if(success != 0){
                            employeePaneSearch();
                        }
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("ERROR: Date format must be yyyy-mm-dd. " + e.getMessage());
                    alert.showAndWait();
                }
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(e.getMessage());
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
    public void employeePaneDelete() throws SQLException {
        Employee selectedEmployee = showEmpTable.getSelectionModel().getSelectedItem();
        if(selectedEmployee != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && (result.get() == ButtonType.OK)) {
                // -- Calls deleteEmployee() and then resets all the fields in the UI and refreshes the tableview --
                newData.deleteEmployee(selectedEmployee);
                employeePaneReset();
                employeePaneSearch();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DELETE RECORD");
            alert.setHeaderText("Nothing is selected");
            alert.showAndWait();
        }
    }

    // -- Reset Button --
    public void employeePaneReset() {
        showEmpBatchUid.clear();
        showEmpEmpId.clear();
        showEmpFirstN.clear();
        showEmpLastN.clear();
        showEmpEmail.clear();
        showEmpSalary.clear();
        showEmpConDate.clear();
        showEmpNatId.clear();
        showEmpTable.getItems().clear();
    }

    // -- Exit Button. This changes the Scene to return to the Main Window --
    public void empExitHandler() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("Are you sure? Press OK to confirm or CANCEL to abort.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            AppData.sceneChange(employeeChangePane.getScene(), "mainWindow.fxml");
        }
    }
}

