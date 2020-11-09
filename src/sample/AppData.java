package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// -- This class holds all the logic of the application --
public class AppData {
    // -- Connection to the DB is defined --
    private static final String dbUrl = "jdbc:postgresql://ruby.db.elephantsql.com:5432/kmkwzkbq";
    private static final String user = "kmkwzkbq";
    private static final String password = "2W1bAos2DLv_DnDTQwdVj6HVc4PvAKfQ";
    // -- All tables are defined as variables --
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String COLUMN_EMPLOYEES_PK1 = "pk1";
    private static final String COLUMN_EMPLOYEES_BATCH_UID = "batch_uid";
    private static final String COLUMN_EMPLOYEES_EMPLOYEE_ID = "employee_id";
    private static final String COLUMN_EMPLOYEES_FIRST_NAME = "first_name";
    private static final String COLUMN_EMPLOYEES_LAST_NAME = "last_name";
    private static final String COLUMN_EMPLOYEES_EMAIL = "email";
    private static final String COLUMN_EMPLOYEES_SALARY = "salary";
    private static final String COLUMN_EMPLOYEES_CONTRACT_DATE = "contract_date";
    private static final String COLUMN_EMPLOYEES_NATIONAL_ID = "national_id";
    // -- All columns are defined as variables --
    private static final String TABLE_CAMPAIGNS = "campaigns";
    private static final String COLUMN_CAMPAIGNS_PK1 = "pk1";
    private static final String COLUMN_CAMPAIGNS_BATCH_UID = "batch_uid";
    private static final String COLUMN_CAMPAIGNS_CAMP_NAME = "camp_name";
    private static final String COLUMN_CAMPAIGNS_CAMPAIGN_ID = "campaign_id";
    private static final String COLUMN_CAMPAIGNS_CONTRACT_DATE = "contract_date";
    private static final String COLUMN_CAMPAIGNS_RENEW_DATE = "renew_date";
    private static final String COLUMN_CAMPAIGNS_HEADCOUNT_REQ = "headcount_req";
    private static final String COLUMN_CAMPAIGNS_REVENUE = "revenue";
    private static final String COLUMN_CAMPAIGNS_PRIORITY = "priority";

    private static final String TABLE_POSITIONS = "positions";
    private static final String COLUMN_POSITIONS_PK1 = "pk1";
    private static final String COLUMN_POSITIONS_BATCH_UID = "batch_uid";
    private static final String COLUMN_POSITIONS_TITLE = "title";
    private static final String COLUMN_POSITIONS_DESCRIPTION = "description";

    private static final String TABLE_SHIFTS = "shifts";
    private static final String COLUMN_SHIFTS_PK1 = "pk1";
    private static final String COLUMN_SHIFTS_BATCH_UID = "batch_uid";
    private static final String COLUMN_SHIFTS_DESCRIPTION = "description";
    private static final String COLUMN_SHIFTS_SCHEDULE = "schedule";

    private static final String TABLE_EMPLOYEES_CAMPAIGNS = "employees_campaigns";
    private static final String COLUMN_EMPLOYEES_CAMPAIGNS_PK1 = "pk1";
    private static final String COLUMN_EMPLOYEES_CAMPAIGNS_BATCH_UID = "batch_uid";
    private static final String COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1 = "employees_pk1";
    private static final String COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1 = "campaigns_pk1";
    private static final String COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1 = "shifts_pk1";
    private static final String COLUMN_EMPLOYEES_CAMPAIGNS_START_DATE = "start_date";
    private static final String COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1 = "positions_pk1";
    // -- All sql statements are defined as variables --

    private static final String QUERY_SUMMARIZE_DATA_RETRIEVE = "SELECT " + TABLE_EMPLOYEES_CAMPAIGNS + "." +
            COLUMN_EMPLOYEES_CAMPAIGNS_PK1 + ", " + TABLE_EMPLOYEES_CAMPAIGNS + "." +
            COLUMN_EMPLOYEES_CAMPAIGNS_BATCH_UID + ", "+ TABLE_EMPLOYEES_CAMPAIGNS  + "."
            + COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1 + ", " + TABLE_EMPLOYEES_CAMPAIGNS  + "." + COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1
            + ", " + TABLE_EMPLOYEES_CAMPAIGNS  + "." + COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1 + ", "
            + TABLE_EMPLOYEES_CAMPAIGNS  + "." + COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1 + ", " +TABLE_EMPLOYEES + "." +
    COLUMN_EMPLOYEES_EMPLOYEE_ID + ", " + TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_FIRST_NAME +
            ", " + TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_LAST_NAME + ", " + TABLE_CAMPAIGNS + "." +
    COLUMN_CAMPAIGNS_CAMP_NAME + ", " + TABLE_SHIFTS + "." + COLUMN_SHIFTS_SCHEDULE + ", " +
    TABLE_POSITIONS + "." + COLUMN_POSITIONS_TITLE + ", " + TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_START_DATE +
            " FROM " + TABLE_EMPLOYEES_CAMPAIGNS +
            " JOIN " + TABLE_EMPLOYEES + " ON " + TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1
            + " = " + TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_PK1 +
            " JOIN " + TABLE_CAMPAIGNS + " ON " + TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1
            + " = " + TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_PK1 +
            " JOIN " + TABLE_SHIFTS + " ON " + TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1
            + " = " + TABLE_SHIFTS + "." + COLUMN_SHIFTS_PK1 +
            " JOIN " + TABLE_POSITIONS + " ON " + TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1
            + " = " + TABLE_POSITIONS + "." + COLUMN_POSITIONS_PK1;
    private static final String QUERY_EMPLOYEES = "SELECT * FROM " + TABLE_EMPLOYEES;
    private static final String QUERY_CAMPAIGNS = "SELECT * FROM " + TABLE_CAMPAIGNS;
    private static final String QUERY_SHIFTS = "SELECT * FROM " + TABLE_SHIFTS;
    private static final String QUERY_POSITIONS = "SELECT * FROM " + TABLE_POSITIONS;

    private static final String INSERT_EMPLOYEES = "INSERT INTO " + TABLE_EMPLOYEES + "(" + COLUMN_EMPLOYEES_BATCH_UID +
            ", " + COLUMN_EMPLOYEES_EMPLOYEE_ID + ", " + COLUMN_EMPLOYEES_FIRST_NAME + ", " + COLUMN_EMPLOYEES_LAST_NAME +
            ", " + COLUMN_EMPLOYEES_EMAIL + ", " + COLUMN_EMPLOYEES_SALARY + ", " + COLUMN_EMPLOYEES_CONTRACT_DATE + ", "
            + COLUMN_EMPLOYEES_NATIONAL_ID + ") VALUES (";
    private static final String INSERT_CAMPAIGNS = "INSERT INTO " + TABLE_CAMPAIGNS + "(" + COLUMN_CAMPAIGNS_BATCH_UID +
            ", " + COLUMN_CAMPAIGNS_CAMP_NAME + ", " + COLUMN_CAMPAIGNS_CAMPAIGN_ID + ", " + COLUMN_CAMPAIGNS_CONTRACT_DATE +
            ", " + COLUMN_CAMPAIGNS_RENEW_DATE + ", " + COLUMN_CAMPAIGNS_HEADCOUNT_REQ + ", " + COLUMN_CAMPAIGNS_REVENUE + ", "
            + COLUMN_CAMPAIGNS_PRIORITY + ") VALUES (";
    private static final String INSERT_SHIFTS = "INSERT INTO " + TABLE_SHIFTS + "(" + COLUMN_SHIFTS_BATCH_UID +
            ", " + COLUMN_SHIFTS_DESCRIPTION + ", " + COLUMN_SHIFTS_SCHEDULE + ") VALUES (";
    private static final String INSERT_POSITIONS = "INSERT INTO " + TABLE_POSITIONS + "(" + COLUMN_POSITIONS_BATCH_UID +
            ", " + COLUMN_POSITIONS_DESCRIPTION + ", " + COLUMN_POSITIONS_TITLE + ") VALUES (";
    private static final String INSERT_SUMMARIZE = "INSERT INTO " + TABLE_EMPLOYEES_CAMPAIGNS + "(" + COLUMN_EMPLOYEES_CAMPAIGNS_BATCH_UID +
            ", " + COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1 + ", " + COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1 + ", " +
            COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1 + ", " + COLUMN_EMPLOYEES_CAMPAIGNS_START_DATE + ", " + COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1 +
            ") VALUES (";

    private static final String UPDATE_EMPLOYEES = "UPDATE " + TABLE_EMPLOYEES + " SET ";
    private static final String UPDATE_CAMPAIGNS = "UPDATE " + TABLE_CAMPAIGNS + " SET ";
    private static final String UPDATE_SHIFTS= "UPDATE " + TABLE_SHIFTS + " SET ";
    private static final String UPDATE_POSITIONS= "UPDATE " + TABLE_POSITIONS + " SET ";
    private static final String UPDATE_SUMMARIZE= "UPDATE " + TABLE_EMPLOYEES_CAMPAIGNS + " SET ";

    private static final String DELETE_EMPLOYEES = "DELETE FROM " + TABLE_EMPLOYEES + " WHERE " + COLUMN_EMPLOYEES_PK1 +
            " = '";
    private static final String DELETE_CAMPAIGNS = "DELETE FROM " + TABLE_CAMPAIGNS + " WHERE " + COLUMN_CAMPAIGNS_PK1 +
            " = '";
    private static final String DELETE_SHIFTS = "DELETE FROM " + TABLE_SHIFTS + " WHERE " + COLUMN_SHIFTS_PK1 +
            " = '";
    private static final String DELETE_POSITIONS = "DELETE FROM " + TABLE_POSITIONS + " WHERE " + COLUMN_POSITIONS_PK1 +
            " = '";
    private static final String DELETE_SUMMARIZE = "DELETE FROM " + TABLE_EMPLOYEES_CAMPAIGNS + " WHERE " + COLUMN_EMPLOYEES_CAMPAIGNS_PK1 +
            " = '";

    StringBuilder sb = new StringBuilder();
    private PreparedStatement currentStatement;
    private Connection conn;
    // -- Connection to the DB is defined --
    public Connection connect() throws SQLException {
        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("ERROR Unable to connect to PostgreSQL server: "+ e.getMessage());
        }

        return conn;
    }

    // -- Query for the SummarizeData object use in the main UI --
    // -- This is used when the master doesn't have any data --
    public List<SummarizeData> querySummarize() throws  SQLException{

        sb = new StringBuilder(QUERY_SUMMARIZE_DATA_RETRIEVE);
        System.out.printf(sb.toString());
        try (Connection db = connect();
            Statement statement1 = db.createStatement();
            ResultSet results1 = statement1.executeQuery(sb.toString())){
            // -- List of SummarizeData objects for showing in the main UI --
            List<SummarizeData> summarizeData = new ArrayList<>();
            while (results1.next()) {
                SummarizeData sumData = new SummarizeData();
                sumData.setEmployees_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1));
                sumData.setCampaigns_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1));
                sumData.setShifts_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1));
                sumData.setPositions_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1));
                sumData.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                sumData.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                sumData.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                sumData.setCampaign_name(results1.getString(COLUMN_CAMPAIGNS_CAMP_NAME));
                sumData.setShift(results1.getString(COLUMN_SHIFTS_SCHEDULE));
                sumData.setPosition(results1.getString(COLUMN_POSITIONS_TITLE));
                sumData.setPk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_PK1));
                sumData.setBatch_uid(results1.getString(COLUMN_EMPLOYEES_CAMPAIGNS_BATCH_UID));
                sumData.setStartDate(results1.getDate(COLUMN_EMPLOYEES_CAMPAIGNS_START_DATE));

                summarizeData.add(sumData);
            }
            closeConnection(db);
            return summarizeData;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }
    // -- Method overload for the Assignment window interface --
    public List<SummarizeData> querySummarize(String batchUidCheck, String assignEmpCheck, String assignCampCheck,
                                              String assignShiftCheck, String assignPositionCheck,
                                              String assignStartDateCheck) throws  SQLException{

        sb = new StringBuilder(QUERY_SUMMARIZE_DATA_RETRIEVE);
        // -- preCondition checks if there is a need of an  "AND" in the "WHERE" clause --
        boolean preCondition = false;
        sb.append(" WHERE ");
        if (!batchUidCheck.isEmpty()) {
            sb.append(TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(batchUidCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!assignEmpCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(assignEmpCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!assignCampCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(assignCampCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!assignShiftCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_SHIFTS + "." + COLUMN_SHIFTS_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(assignShiftCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!assignPositionCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_POSITIONS + "." + COLUMN_POSITIONS_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(assignPositionCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!assignStartDateCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_START_DATE);
            sb.append(" = ");
            sb.append("'");
            sb.append(assignStartDateCheck);
            sb.append("'");
        }

        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<SummarizeData> summarizeData = new ArrayList<>();
            while (results1.next()) {
                SummarizeData sumData = new SummarizeData();
                sumData.setEmployees_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1));
                sumData.setCampaigns_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1));
                sumData.setShifts_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1));
                sumData.setPositions_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1));
                sumData.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                sumData.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                sumData.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                sumData.setCampaign_name(results1.getString(COLUMN_CAMPAIGNS_CAMP_NAME));
                sumData.setShift(results1.getString(COLUMN_SHIFTS_SCHEDULE));
                sumData.setPosition(results1.getString(COLUMN_POSITIONS_TITLE));
                sumData.setPk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_PK1));
                sumData.setBatch_uid(results1.getString(COLUMN_EMPLOYEES_CAMPAIGNS_BATCH_UID));
                sumData.setStartDate(results1.getDate(COLUMN_EMPLOYEES_CAMPAIGNS_START_DATE));

                summarizeData.add(sumData);
            }
            closeConnection(db);
            return summarizeData;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method overload. This one is used when the master has any data for an specified search --
    public List<SummarizeData> querySummarize(String empCheck, String natCheck, String firstCheck, String lastCheck, String empEmailCheck,
                                               String campNameCheck, String campIdCheck,
                                               String shiftCheck) throws  SQLException{

        sb = new StringBuilder(QUERY_SUMMARIZE_DATA_RETRIEVE);
        // -- preCondition checks if there is a need of an  "AND" in the "WHERE" clause --
        boolean preCondition = false;
        sb.append(" WHERE ");
        if (!empCheck.isEmpty()) {
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_EMPLOYEE_ID);
            sb.append(" = ");
            sb.append("'");
            sb.append(empCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!natCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_NATIONAL_ID);
            sb.append(" = ");
            sb.append("'");
            sb.append(natCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!firstCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_FIRST_NAME);
            sb.append(" = ");
            sb.append("'");
            sb.append(firstCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!lastCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_LAST_NAME);
            sb.append(" = ");
            sb.append("'");
            sb.append(lastCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!empEmailCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_EMAIL);
            sb.append(" = ");
            sb.append("'");
            sb.append(empEmailCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!campNameCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_CAMP_NAME);
            sb.append(" = ");
            sb.append("'");
            sb.append(campNameCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!campIdCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_CAMPAIGN_ID);
            sb.append(" = ");
            sb.append("'");
            sb.append(campIdCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!shiftCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_SHIFTS + "." + COLUMN_SHIFTS_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(shiftCheck);
            sb.append("'");
        }

        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<SummarizeData> summarizeData = new ArrayList<>();
            while (results1.next()) {
                SummarizeData sumData = new SummarizeData();
                sumData.setEmployees_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1));
                sumData.setCampaigns_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1));
                sumData.setShifts_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1));
                sumData.setPositions_pk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1));
                sumData.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                sumData.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                sumData.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                sumData.setCampaign_name(results1.getString(COLUMN_CAMPAIGNS_CAMP_NAME));
                sumData.setShift(results1.getString(COLUMN_SHIFTS_SCHEDULE));
                sumData.setPosition(results1.getString(COLUMN_POSITIONS_TITLE));
                sumData.setPk1(results1.getInt(COLUMN_EMPLOYEES_CAMPAIGNS_PK1));
                sumData.setBatch_uid(results1.getString(COLUMN_EMPLOYEES_CAMPAIGNS_BATCH_UID));
                sumData.setStartDate(results1.getDate(COLUMN_EMPLOYEES_CAMPAIGNS_START_DATE));

                summarizeData.add(sumData);
            }
            closeConnection(db);
            return summarizeData;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method to insert campaigns in the campaigns table. Returns the number of rows modified --
    public int insertSummarize(SummarizeData newAssign) {
        sb = new StringBuilder(INSERT_SUMMARIZE);
        sb.append("'");
        sb.append(newAssign.getBatch_uid());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newAssign.getEmployees_pk1());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newAssign.getCampaigns_pk1());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newAssign.getShifts_pk1());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newAssign.getStartDate());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newAssign.getPositions_pk1());
        sb.append("'");
        sb.append(")");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int results = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item added successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record created");
            alert.showAndWait();
            return results;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    // -- Method to update the employees table --
    public int updateSummarize(SummarizeData assignment, String newBatchUid, int newEmpPk1, int newCampPk1,
                                int newShiftPk1, Date newStartDate, int newPosPk1) {
        sb = new StringBuilder(UPDATE_SUMMARIZE);
        sb.append(COLUMN_EMPLOYEES_CAMPAIGNS_BATCH_UID);
        sb.append(" = ");
        sb.append("'");
        sb.append(newBatchUid);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(newEmpPk1);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(newCampPk1);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(newShiftPk1);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_CAMPAIGNS_START_DATE);
        sb.append(" = ");
        sb.append("'");
        sb.append(newStartDate);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append((newPosPk1));
        sb.append("'");
        sb.append(" WHERE ");
        sb.append(COLUMN_EMPLOYEES_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(assignment.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int result = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item updated successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record Updated");
            alert.showAndWait();
            return result;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    // -- Method to deleted from the employees table --
    public void deleteSummarize(SummarizeData assign) {
        sb = new StringBuilder(DELETE_SUMMARIZE);
        sb.append(assign.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item deleted successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record deleted");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    // -- Method to insert employees in the employees table. Returns the number of rows modified --
    public int insertEmployee(Employee newEmployee) {
        sb = new StringBuilder(INSERT_EMPLOYEES);
        sb.append("'");
        sb.append(newEmployee.getBatch_uid());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newEmployee.getEmployee_id());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newEmployee.getFirst_name());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newEmployee.getLast_name());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newEmployee.getEmail());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(String.format("%.0f", newEmployee.getSalary()));
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newEmployee.getContract_date());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newEmployee.getNational_id());
        sb.append("'");
        sb.append(")");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int results = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item added successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record created");
            alert.showAndWait();
            return results;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    // -- Method to update the employees table --
    public int updateEmployee(Employee employee, String newBatchUid, String newEmpId, String newFirstName, String newLastName,
                               String newEmail, Double newSalary, Date newCondate, String newNatId) {
        sb = new StringBuilder(UPDATE_EMPLOYEES);
        sb.append(COLUMN_EMPLOYEES_BATCH_UID);
        sb.append(" = ");
        sb.append("'");
        sb.append(newBatchUid);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_EMPLOYEE_ID);
        sb.append(" = ");
        sb.append("'");
        sb.append(newEmpId);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_FIRST_NAME);
        sb.append(" = ");
        sb.append("'");
        sb.append(newFirstName);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_LAST_NAME);
        sb.append(" = ");
        sb.append("'");
        sb.append(newLastName);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_EMAIL);
        sb.append(" = ");
        sb.append("'");
        sb.append(newEmail);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_SALARY);
        sb.append(" = ");
        sb.append("'");
        sb.append(String.format("%.0f", newSalary));
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_CONTRACT_DATE);
        sb.append(" = ");
        sb.append("'");
        sb.append(newCondate);
        sb.append("', ");
        sb.append(COLUMN_EMPLOYEES_NATIONAL_ID);
        sb.append(" = ");
        sb.append("'");
        sb.append(newNatId);
        sb.append("'");
        sb.append(" WHERE ");
        sb.append(COLUMN_EMPLOYEES_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(employee.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int result = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item updated successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record Updated");
            alert.showAndWait();
            return result;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    // -- Method to deleted from the employees table --
    public void deleteEmployee(Employee employee) {
        sb = new StringBuilder(DELETE_EMPLOYEES);
        sb.append(employee.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item deleted successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record deleted");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    // -- Method to search for all the employees --
    public List<Employee> queryEmployee() throws SQLException {

        sb = new StringBuilder(QUERY_EMPLOYEES);
        try (Connection db = connect();
            Statement statement1 = db.createStatement();
            ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<Employee> employeeList = new ArrayList<>();
            while (results1.next()) {
                Employee employee = new Employee();
                employee.setPk1(results1.getInt(COLUMN_EMPLOYEES_PK1));
                employee.setBatch_uid(results1.getString(COLUMN_EMPLOYEES_BATCH_UID));
                employee.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                employee.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                employee.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                employee.setEmail(results1.getString(COLUMN_EMPLOYEES_EMAIL));
                employee.setSalary(results1.getDouble(COLUMN_EMPLOYEES_SALARY));
                employee.setContract_date(results1.getDate(COLUMN_EMPLOYEES_CONTRACT_DATE));
                employee.setNational_id(results1.getString(COLUMN_EMPLOYEES_NATIONAL_ID));

                employeeList.add(employee);
            }
            closeConnection(db);
            return employeeList;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method overload. Method to query the employees table using the PK1 --
    public Employee queryEmployee(int pk1) throws SQLException {

        sb = new StringBuilder(QUERY_EMPLOYEES);
        sb.append(" WHERE ");
        sb.append(COLUMN_EMPLOYEES_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(pk1);
        sb.append("'");
        try (Connection db = connect();
            Statement statement1 = db.createStatement();
            ResultSet results1 = statement1.executeQuery(sb.toString())){
            Employee employee = new Employee();
            while (results1.next()) {
                employee.setPk1(results1.getInt(COLUMN_EMPLOYEES_PK1));
                employee.setBatch_uid(results1.getString(COLUMN_EMPLOYEES_BATCH_UID));
                employee.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                employee.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                employee.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                employee.setEmail(results1.getString(COLUMN_EMPLOYEES_EMAIL));
                employee.setSalary(results1.getDouble(COLUMN_EMPLOYEES_SALARY));
                employee.setContract_date(results1.getDate(COLUMN_EMPLOYEES_CONTRACT_DATE));
                employee.setNational_id(results1.getString(COLUMN_EMPLOYEES_NATIONAL_ID));
            }
            closeConnection(db);
            return employee;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
            }
        }

    public Employee queryEmployee(String batchUid) throws SQLException {

        sb = new StringBuilder(QUERY_EMPLOYEES);
        sb.append(" WHERE ");
        sb.append(COLUMN_EMPLOYEES_BATCH_UID);
        sb.append(" = ");
        sb.append("'");
        sb.append(batchUid);
        sb.append("'");
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){
            Employee employee = new Employee();
            while (results1.next()) {
                employee.setPk1(results1.getInt(COLUMN_EMPLOYEES_PK1));
                employee.setBatch_uid(results1.getString(COLUMN_EMPLOYEES_BATCH_UID));
                employee.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                employee.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                employee.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                employee.setEmail(results1.getString(COLUMN_EMPLOYEES_EMAIL));
                employee.setSalary(results1.getDouble(COLUMN_EMPLOYEES_SALARY));
                employee.setContract_date(results1.getDate(COLUMN_EMPLOYEES_CONTRACT_DATE));
                employee.setNational_id(results1.getString(COLUMN_EMPLOYEES_NATIONAL_ID));
            }
            closeConnection(db);
            return employee;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method overload. Method to query the employees table using a 'WHERE' clause for all possible columns --
    public List<Employee> queryEmployee(String batchUidCheck, String empIdCheck, String firstCheck, String lastCheck, String emailCheck,
                                              String salaryCheck, String conDateCheck,
                                              String natIdCheck) throws  SQLException{

        sb = new StringBuilder(QUERY_EMPLOYEES);
        boolean preCondition = false;
        sb.append(" WHERE ");
        if (!batchUidCheck.isEmpty()) {
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(batchUidCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!empIdCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_EMPLOYEE_ID);
            sb.append(" = ");
            sb.append("'");
            sb.append(empIdCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!firstCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_FIRST_NAME);
            sb.append(" = ");
            sb.append("'");
            sb.append(firstCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!lastCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_LAST_NAME);
            sb.append(" = ");
            sb.append("'");
            sb.append(lastCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!emailCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_EMAIL);
            sb.append(" = ");
            sb.append("'");
            sb.append(emailCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!salaryCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_SALARY);
            sb.append(" = ");
            sb.append("'");
            sb.append(salaryCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!conDateCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_CONTRACT_DATE);
            sb.append(" = ");
            sb.append("'");
            sb.append(conDateCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!natIdCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_NATIONAL_ID);
            sb.append(" = ");
            sb.append("'");
            sb.append(natIdCheck);
            sb.append("'");
        }

        System.out.println(sb.toString());
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<Employee> employeeList = new ArrayList<>();
            while (results1.next()) {
                Employee employee = new Employee();
                employee.setPk1(results1.getInt(COLUMN_EMPLOYEES_PK1));
                employee.setBatch_uid(results1.getString(COLUMN_EMPLOYEES_BATCH_UID));
                employee.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                employee.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                employee.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                employee.setEmail(results1.getString(COLUMN_EMPLOYEES_EMAIL));
                employee.setSalary(results1.getDouble(COLUMN_EMPLOYEES_SALARY));
                employee.setContract_date(results1.getDate(COLUMN_EMPLOYEES_CONTRACT_DATE));
                employee.setNational_id(results1.getString(COLUMN_EMPLOYEES_NATIONAL_ID));

                employeeList.add(employee);
            }
            closeConnection(db);
            return employeeList;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Queries the campaigns table with not parameters --
    public List<Campaign> queryCampaign() {
        sb = new StringBuilder(QUERY_CAMPAIGNS);
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<Campaign> campaignList = new ArrayList<>();
            while (results1.next()) {
                Campaign campaign = new Campaign();
                campaign.setPk1(results1.getInt(COLUMN_CAMPAIGNS_PK1));
                campaign.setBatch_uid(results1.getString(COLUMN_CAMPAIGNS_BATCH_UID));
                campaign.setName(results1.getString(COLUMN_CAMPAIGNS_CAMP_NAME));
                campaign.setCampaign_id(results1.getString(COLUMN_CAMPAIGNS_CAMPAIGN_ID));
                campaign.setContract_date(results1.getDate(COLUMN_CAMPAIGNS_CONTRACT_DATE));
                campaign.setRenew_date(results1.getDate(COLUMN_CAMPAIGNS_RENEW_DATE));
                campaign.setHeadcount_req(results1.getDouble(COLUMN_CAMPAIGNS_HEADCOUNT_REQ));
                campaign.setRevenue(results1.getDouble(COLUMN_CAMPAIGNS_REVENUE));
                campaign.setPriority(results1.getString(COLUMN_CAMPAIGNS_PRIORITY));

                campaignList.add(campaign);
            }
            closeConnection(db);
            return campaignList;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Query the campaigns table using the PK1 --
    public Campaign queryCampaign(int pk1) {
        sb = new StringBuilder(QUERY_CAMPAIGNS);
        sb.append(" WHERE ");
        sb.append(COLUMN_CAMPAIGNS_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(pk1);
        sb.append("'");
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){
            Campaign campaign = new Campaign();
            while (results1.next()) {
                campaign.setPk1(results1.getInt(COLUMN_CAMPAIGNS_PK1));
                campaign.setBatch_uid(results1.getString(COLUMN_CAMPAIGNS_BATCH_UID));
                campaign.setName(results1.getString(COLUMN_CAMPAIGNS_CAMP_NAME));
                campaign.setCampaign_id(results1.getString(COLUMN_CAMPAIGNS_CAMPAIGN_ID));
                campaign.setContract_date(results1.getDate(COLUMN_CAMPAIGNS_CONTRACT_DATE));
                campaign.setRenew_date(results1.getDate(COLUMN_CAMPAIGNS_RENEW_DATE));
                campaign.setHeadcount_req(results1.getDouble(COLUMN_CAMPAIGNS_HEADCOUNT_REQ));
                campaign.setRevenue(results1.getDouble(COLUMN_CAMPAIGNS_REVENUE));
                campaign.setPriority(results1.getString(COLUMN_CAMPAIGNS_PRIORITY));
            }
            closeConnection(db);
            return campaign;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    public Campaign queryCampaign(String batchUid) {
        sb = new StringBuilder(QUERY_CAMPAIGNS);
        sb.append(" WHERE ");
        sb.append(COLUMN_CAMPAIGNS_BATCH_UID);
        sb.append(" = ");
        sb.append("'");
        sb.append(batchUid);
        sb.append("'");
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){
            Campaign campaign = new Campaign();
            while (results1.next()) {
                campaign.setPk1(results1.getInt(COLUMN_CAMPAIGNS_PK1));
                campaign.setBatch_uid(results1.getString(COLUMN_CAMPAIGNS_BATCH_UID));
                campaign.setName(results1.getString(COLUMN_CAMPAIGNS_CAMP_NAME));
                campaign.setCampaign_id(results1.getString(COLUMN_CAMPAIGNS_CAMPAIGN_ID));
                campaign.setContract_date(results1.getDate(COLUMN_CAMPAIGNS_CONTRACT_DATE));
                campaign.setRenew_date(results1.getDate(COLUMN_CAMPAIGNS_RENEW_DATE));
                campaign.setHeadcount_req(results1.getDouble(COLUMN_CAMPAIGNS_HEADCOUNT_REQ));
                campaign.setRevenue(results1.getDouble(COLUMN_CAMPAIGNS_REVENUE));
                campaign.setPriority(results1.getString(COLUMN_CAMPAIGNS_PRIORITY));
            }
            closeConnection(db);
            return campaign;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method overload to use parameters in the campaign query --
    public List<Campaign> queryCampaign(String batchUidCheck, String nameCheck, String campIdCheck, String conDateCheck,
                                        String renDateCheck, String headcountCheck, String revenueCheck,
                                        String priorityCheck) throws  SQLException{

        sb = new StringBuilder(QUERY_CAMPAIGNS);
        boolean preCondition = false;
        sb.append(" WHERE ");
        if (!batchUidCheck.isEmpty()) {
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(batchUidCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!nameCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_CAMP_NAME);
            sb.append(" = ");
            sb.append("'");
            sb.append(nameCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!campIdCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_CAMPAIGN_ID);
            sb.append(" = ");
            sb.append("'");
            sb.append(campIdCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!conDateCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_CONTRACT_DATE);
            sb.append(" = ");
            sb.append("'");
            sb.append(conDateCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!renDateCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_RENEW_DATE);
            sb.append(" = ");
            sb.append("'");
            sb.append(renDateCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!headcountCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_HEADCOUNT_REQ);
            sb.append(" = ");
            sb.append("'");
            sb.append(headcountCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!revenueCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_REVENUE);
            sb.append(" = ");
            sb.append("'");
            sb.append(revenueCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!priorityCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_PRIORITY);
            sb.append(" = ");
            sb.append("'");
            sb.append(priorityCheck);
            sb.append("'");
        }

        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<Campaign> campaignList = new ArrayList<>();
            while (results1.next()) {
                Campaign campaign = new Campaign();
                campaign.setPk1(results1.getInt(COLUMN_CAMPAIGNS_PK1));
                campaign.setBatch_uid(results1.getString(COLUMN_CAMPAIGNS_BATCH_UID));
                campaign.setName(results1.getString(COLUMN_CAMPAIGNS_CAMP_NAME));
                campaign.setCampaign_id(results1.getString(COLUMN_CAMPAIGNS_CAMPAIGN_ID));
                campaign.setContract_date(results1.getDate(COLUMN_CAMPAIGNS_CONTRACT_DATE));
                campaign.setRenew_date(results1.getDate(COLUMN_CAMPAIGNS_RENEW_DATE));
                campaign.setHeadcount_req(results1.getDouble(COLUMN_CAMPAIGNS_HEADCOUNT_REQ));
                campaign.setRevenue(results1.getDouble(COLUMN_CAMPAIGNS_REVENUE));
                campaign.setPriority(results1.getString(COLUMN_CAMPAIGNS_PRIORITY));

                campaignList.add(campaign);
            }
            closeConnection(db);
            return campaignList;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method to insert campaigns in the campaigns table. Returns the number of rows modified --
    public int insertCampaign(Campaign newCampaign) {
        sb = new StringBuilder(INSERT_CAMPAIGNS);
        sb.append("'");
        sb.append(newCampaign.getBatch_uid());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newCampaign.getName());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newCampaign.getCampaign_id());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newCampaign.getContract_date());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newCampaign.getRenew_date());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(String.format("%.0f", newCampaign.getHeadcount_req()));
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(String.format("%.0f",newCampaign.getRevenue()));
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newCampaign.getPriority());
        sb.append("'");
        sb.append(")");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int results = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item added successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record created");
            alert.showAndWait();
            return results;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    // -- Method to update the campaigns table --
    public int updateCampaign(Campaign campaign, String newBatchUid, String newName, String newCampId, Date newConDate,
                              Date newRenDate, Double newHeadReq, Double newRevenue, String newPriority) {
        sb = new StringBuilder(UPDATE_CAMPAIGNS);
        sb.append(COLUMN_CAMPAIGNS_BATCH_UID);
        sb.append(" = ");
        sb.append("'");
        sb.append(newBatchUid);
        sb.append("', ");
        sb.append(COLUMN_CAMPAIGNS_CAMP_NAME);
        sb.append(" = ");
        sb.append("'");
        sb.append(newName);
        sb.append("', ");
        sb.append(COLUMN_CAMPAIGNS_CAMPAIGN_ID);
        sb.append(" = ");
        sb.append("'");
        sb.append(newCampId);
        sb.append("', ");
        sb.append(COLUMN_CAMPAIGNS_CONTRACT_DATE);
        sb.append(" = ");
        sb.append("'");
        sb.append(newConDate);
        sb.append("', ");
        sb.append(COLUMN_CAMPAIGNS_RENEW_DATE);
        sb.append(" = ");
        sb.append("'");
        sb.append(newRenDate);
        sb.append("', ");
        sb.append(COLUMN_CAMPAIGNS_HEADCOUNT_REQ);
        sb.append(" = ");
        sb.append("'");
        sb.append(String.format("%.0f", newHeadReq));
        sb.append("', ");
        sb.append(COLUMN_CAMPAIGNS_REVENUE);
        sb.append(" = ");
        sb.append("'");
        sb.append(String.format("%.0f", newRevenue));
        sb.append("', ");
        sb.append(COLUMN_CAMPAIGNS_PRIORITY);
        sb.append(" = ");
        sb.append("'");
        sb.append(newPriority);
        sb.append("'");
        sb.append(" WHERE ");
        sb.append(COLUMN_CAMPAIGNS_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(campaign.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int result = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item updated successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record Updated");
            alert.showAndWait();
            return result;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    // -- Method to deleted from the campaigns table --
    public void deleteCampaign(Campaign campaign) {
        sb = new StringBuilder(DELETE_CAMPAIGNS);
        sb.append(campaign.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item deleted successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record deleted");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    // -- Method to search for all the shifts --
    public List<Shift> queryShift() throws SQLException {

        sb = new StringBuilder(QUERY_SHIFTS);
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<Shift> shiftList = new ArrayList<>();
            while (results1.next()) {
                Shift shift = new Shift();
                shift.setPk1(results1.getInt(COLUMN_SHIFTS_PK1));
                shift.setBatch_uid(results1.getString(COLUMN_SHIFTS_BATCH_UID));
                shift.setDescription(results1.getString(COLUMN_SHIFTS_DESCRIPTION));
                shift.setTime(results1.getString(COLUMN_SHIFTS_SCHEDULE));

                shiftList.add(shift);
            }
            closeConnection(db);
            return shiftList;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method to query the shifts table using the PK1 --
    public Shift queryShift(int pk1) {
        sb = new StringBuilder(QUERY_SHIFTS);
        sb.append(" WHERE ");
        sb.append(COLUMN_SHIFTS_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(pk1);
        sb.append("'");
        try (Connection db = connect();
            Statement statement1 = db.createStatement();
            ResultSet results1 = statement1.executeQuery(sb.toString())){
            Shift shift = new Shift();
            while (results1.next()) {
                shift.setPk1(results1.getInt(COLUMN_SHIFTS_PK1));
                shift.setBatch_uid(results1.getString(COLUMN_SHIFTS_BATCH_UID));
                shift.setDescription(results1.getString(COLUMN_SHIFTS_DESCRIPTION));
                shift.setTime(results1.getString(COLUMN_SHIFTS_SCHEDULE));
            }
            closeConnection(db);
            return shift;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    public Shift queryShift(String batchUid) {
        sb = new StringBuilder(QUERY_SHIFTS);
        sb.append(" WHERE ");
        sb.append(COLUMN_SHIFTS_BATCH_UID);
        sb.append(" = ");
        sb.append("'");
        sb.append(batchUid);
        sb.append("'");
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){
            Shift shift = new Shift();
            while (results1.next()) {
                shift.setPk1(results1.getInt(COLUMN_SHIFTS_PK1));
                shift.setBatch_uid(results1.getString(COLUMN_SHIFTS_BATCH_UID));
                shift.setDescription(results1.getString(COLUMN_SHIFTS_DESCRIPTION));
                shift.setTime(results1.getString(COLUMN_SHIFTS_SCHEDULE));
            }
            closeConnection(db);
            return shift;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method overload. Method to query the shifts table using a 'WHERE' clause for all possible columns --
    public List<Shift> queryShift(String batchUidCheck, String timeCheck, String descriptionCheck) throws  SQLException{

        sb = new StringBuilder(QUERY_SHIFTS);
        boolean preCondition = false;
        sb.append(" WHERE ");
        if (!batchUidCheck.isEmpty()) {
            sb.append(TABLE_SHIFTS + "." + COLUMN_SHIFTS_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(batchUidCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!descriptionCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_SHIFTS + "." + COLUMN_SHIFTS_DESCRIPTION);
            sb.append(" = ");
            sb.append("'");
            sb.append(descriptionCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!timeCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_SHIFTS + "." + COLUMN_SHIFTS_SCHEDULE);
            sb.append(" = ");
            sb.append("'");
            sb.append(timeCheck);
            sb.append("'");
        }

        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<Shift> shiftList = new ArrayList<>();
            while (results1.next()) {
                Shift shift = new Shift();
                shift.setPk1(results1.getInt(COLUMN_SHIFTS_PK1));
                shift.setBatch_uid(results1.getString(COLUMN_SHIFTS_BATCH_UID));
                shift.setDescription(results1.getString(COLUMN_SHIFTS_DESCRIPTION));
                shift.setTime(results1.getString(COLUMN_SHIFTS_SCHEDULE));

                shiftList.add(shift);
            }
            closeConnection(db);
            return shiftList;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method to insert campaigns in the campaigns table. Returns the number of rows modified --
    public int insertShift(Shift newShift) {
        sb = new StringBuilder(INSERT_SHIFTS);
        sb.append("'");
        sb.append(newShift.getBatch_uid());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newShift.getDescription());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newShift.getTime());
        sb.append("'");
        sb.append(")");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int results = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item added successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record created");
            alert.showAndWait();
            return results;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    public int updateShift(Shift shift, String newBatchUid, String newSchedule, String newDescription) {
        sb = new StringBuilder(UPDATE_SHIFTS);
        sb.append(COLUMN_SHIFTS_BATCH_UID);
        sb.append(" = ");
        sb.append("'");
        sb.append(newBatchUid);
        sb.append("', ");
        sb.append(COLUMN_SHIFTS_SCHEDULE);
        sb.append(" = ");
        sb.append("'");
        sb.append(newSchedule);
        sb.append("', ");
        sb.append(COLUMN_SHIFTS_DESCRIPTION);
        sb.append(" = ");
        sb.append("'");
        sb.append(newDescription);
        sb.append("'");
        sb.append(" WHERE ");
        sb.append(COLUMN_SHIFTS_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(shift.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int result = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item updated successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record Updated");
            alert.showAndWait();
            return result;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    // -- Method to deleted from the shifts table --
    public void deleteShift(Shift shift) {
        sb = new StringBuilder(DELETE_SHIFTS);
        sb.append(shift.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item deleted successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record deleted");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    // -- Method to search for all the positions --
    public List<Positions> queryPosition() throws SQLException {

        sb = new StringBuilder(QUERY_POSITIONS);
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<Positions> positionsList = new ArrayList<>();
            while (results1.next()) {
                Positions position = new Positions();
                position.setPk1(results1.getInt(COLUMN_POSITIONS_PK1));
                position.setBatch_uid(results1.getString(COLUMN_POSITIONS_BATCH_UID));
                position.setDescription(results1.getString(COLUMN_POSITIONS_DESCRIPTION));
                position.setTittle(results1.getString(COLUMN_POSITIONS_TITLE));

                positionsList.add(position);
            }
            closeConnection(db);
            return positionsList;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method to query the positions table using the PK1 --
    public Positions queryPosition(int pk1) {
        sb = new StringBuilder(QUERY_POSITIONS);
        sb.append(" WHERE ");
        sb.append(COLUMN_POSITIONS_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(pk1);
        sb.append("'");
        try (Connection db = connect();
            Statement statement1 = db.createStatement();
            ResultSet results1 = statement1.executeQuery(sb.toString())){
            Positions position = new Positions();
            while (results1.next()) {
                position.setPk1(results1.getInt(COLUMN_POSITIONS_PK1));
                position.setBatch_uid(results1.getString(COLUMN_POSITIONS_BATCH_UID));
                position.setDescription(results1.getString(COLUMN_POSITIONS_DESCRIPTION));
                position.setTittle(results1.getString(COLUMN_POSITIONS_TITLE));
            }
            closeConnection(db);
            return position;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    public Positions queryPosition(String batchUid) {
        sb = new StringBuilder(QUERY_POSITIONS);
        sb.append(" WHERE ");
        sb.append(COLUMN_POSITIONS_BATCH_UID);
        sb.append(" = ");
        sb.append("'");
        sb.append(batchUid);
        sb.append("'");
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){
            Positions position = new Positions();
            while (results1.next()) {
                position.setPk1(results1.getInt(COLUMN_POSITIONS_PK1));
                position.setBatch_uid(results1.getString(COLUMN_POSITIONS_BATCH_UID));
                position.setDescription(results1.getString(COLUMN_POSITIONS_DESCRIPTION));
                position.setTittle(results1.getString(COLUMN_POSITIONS_TITLE));
            }
            closeConnection(db);
            return position;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method overload. Method to query the positions table using a 'WHERE' clause for all possible columns --
    public List<Positions> queryPosition(String batchUidCheck, String titleCheck, String descriptionCheck) throws  SQLException{

        sb = new StringBuilder(QUERY_POSITIONS);
        boolean preCondition = false;
        sb.append(" WHERE ");
        if (!batchUidCheck.isEmpty()) {
            sb.append(TABLE_POSITIONS + "." + COLUMN_POSITIONS_BATCH_UID);
            sb.append(" = ");
            sb.append("'");
            sb.append(batchUidCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!descriptionCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_POSITIONS + "." + COLUMN_POSITIONS_DESCRIPTION);
            sb.append(" = ");
            sb.append("'");
            sb.append(descriptionCheck);
            sb.append("'");
            preCondition = true;
        }

        if (!titleCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
            sb.append(TABLE_POSITIONS + "." + COLUMN_POSITIONS_TITLE);
            sb.append(" = ");
            sb.append("'");
            sb.append(titleCheck);
            sb.append("'");
        }

        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<Positions> positionsList = new ArrayList<>();
            while (results1.next()) {
                Positions position = new Positions();
                position.setPk1(results1.getInt(COLUMN_POSITIONS_PK1));
                position.setBatch_uid(results1.getString(COLUMN_POSITIONS_BATCH_UID));
                position.setDescription(results1.getString(COLUMN_POSITIONS_DESCRIPTION));
                position.setTittle(results1.getString(COLUMN_POSITIONS_TITLE));

                positionsList.add(position);
            }
            closeConnection(db);
            return positionsList;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    // -- Method to insert positions in the positions table. Returns the number of rows modified --
    public int insertPosition(Positions newPosition) {
        sb = new StringBuilder(INSERT_POSITIONS);
        sb.append("'");
        sb.append(newPosition.getBatch_uid());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newPosition.getDescription());
        sb.append("'");
        sb.append(", ");
        sb.append("'");
        sb.append(newPosition.getTittle());
        sb.append("'");
        sb.append(")");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int results = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item added successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record created");
            alert.showAndWait();
            return results;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    public int updatePosition(Positions position, String newBatchUid, String newTitle, String newDescription) {
        sb = new StringBuilder(UPDATE_POSITIONS);
        sb.append(COLUMN_POSITIONS_BATCH_UID);
        sb.append(" = ");
        sb.append("'");
        sb.append(newBatchUid);
        sb.append("', ");
        sb.append(COLUMN_POSITIONS_TITLE);
        sb.append(" = ");
        sb.append("'");
        sb.append(newTitle);
        sb.append("', ");
        sb.append(COLUMN_POSITIONS_DESCRIPTION);
        sb.append(" = ");
        sb.append("'");
        sb.append(newDescription);
        sb.append("'");
        sb.append(" WHERE ");
        sb.append(COLUMN_POSITIONS_PK1);
        sb.append(" = ");
        sb.append("'");
        sb.append(position.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            int result = statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item updated successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record Updated");
            alert.showAndWait();
            return result;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
            return 0;
        }
    }

    // -- Method to deleted from the positions table --
    public void deletePosition(Positions position) {
        sb = new StringBuilder(DELETE_POSITIONS);
        sb.append(position.getPk1());
        sb.append("'");
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            statement1.executeUpdate(sb.toString());
            closeConnection(db);
            System.out.println("Item deleted successfully");
            closeConnection(db);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Record deleted");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }


    // -- Method to close the connection to the DB --
    public static void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    // -- Method to change scenes. Receives the current scene and the location of the new FXML --
    public static void sceneChange(Scene currentScene, String newFXML) throws IOException {
        Window currentWindow = currentScene.getWindow();
        Stage currentStage = (Stage) currentWindow;
        currentStage.hide();
        Parent root = FXMLLoader.load(Controller.class.getResource(newFXML));
        Stage newStage = new Stage();
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.show();

    }

}
