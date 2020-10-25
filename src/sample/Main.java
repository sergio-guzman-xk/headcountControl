package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        /*AppData.insertStatement("INSERT INTO public.employees (" +
                "batch_uid, employee_id, first_name, last_name, email, salary, contract_date, national_id) " +
                "VALUES ('100000', '100000', 'Sergio', 'Guzman', 'sergio.guzman@empresa.com', 1500000, '22-Nov-1991', '1018449109')");
        */
        /*AppData.insertStatement("INSERT INTO public.employees (" +
                "batch_uid, employee_id, first_name, last_name, email, salary, contract_date, national_id) " +
                "VALUES ('100001', '100001', 'Joan', 'Rodriguez', 'joan.rodriguez@empresa.com', 15000000, '20-Aug-2018', '1018549220')");

        AppData newData = new AppData();
        /*List<Employee> employeeList = newData.queryEmployee();

        for(Employee employee: employeeList) {
            System.out.println("Pk1: " + employee.getPk1() + " Batch UID: " + employee.getBatch_uid() + " Employee ID: " +
                    employee.getEmployee_id() + " Name: " + employee.getFirst_name() + " " + employee.getLast_name() + " Salary: " +
                    employee.getSalary() + " National ID: " +employee.getNational_id());
        }

        List<SummarizeData> sumData = newData.querySummarize();
        for (SummarizeData entry : sumData) {
            System.out.println("Employee ID: " + entry.getEmployee_id() + " Name: " + entry.getFirst_name() + " " + entry.getLast_name() + " Campaign Name: " +
                    entry.getCampaign_name() + " Schedule: " + entry.getShift() + " Position: " + entry.getPosition());
        }

         */


        launch(args);
    }
}
