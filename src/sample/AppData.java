package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppData {

    private static final String dbUrl = "jdbc:postgresql://ruby.db.elephantsql.com:5432/kmkwzkbq";
    private static final String user = "kmkwzkbq";
    private static final String password = "2W1bAos2DLv_DnDTQwdVj6HVc4PvAKfQ";

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

    private static final String TABLE_CAMPAIGNS = "campaigns";
    private static final String COLUMN_CAMPAIGNS_PK1 = "pk1";
    private static final String COLUMN_CAMPAIGNS_BATCH_UDI = "batch_udi";
    private static final String COLUMN_CAMPAIGNS_CAMP_NAME = "camp_name";
    private static final String COLUMN_CAMPAIGNS_CAMPAIGN_ID = "campaign_id";
    private static final String COLUMN_CAMPAIGNS_CONTRACT_DATE = "contract_date";
    private static final String COLUMN_CAMPAIGNS_RENEW_DATE = "renew_date";
    private static final String COLUMN_CAMPAIGNS_HEADCOUNT_REQ = "headcount_req";
    private static final String COLUMN_CAMPAIGNS_REVENUE = "revenue";
    private static final String COLUMN_CAMPAIGNS_PRIORITY = "priority";

    private static final String TABLE_POSITIONS = "positions";
    private static final String COLUMN_POSITIONS_PK1 = "pk1";
    private static final String COLUMN_POSITIONS_BATCH_UDI = "batch_uid";
    private static final String COLUMN_POSITIONS_TITLE = "title";
    private static final String COLUMN_POSITIONS_DESCRIPTION = "description";

    private static final String TABLE_SHIFTS = "shifts";
    private static final String COLUMN_SHIFTS_PK1 = "pk1";
    private static final String COLUMN_SHIFTS_BATCH_UID = "batch_udi";
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

    private static final String QUERY_SUMMARIZE_DATA_RETRIEVE = "SELECT " + TABLE_EMPLOYEES_CAMPAIGNS  + "."
            + COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1 + ", " + TABLE_EMPLOYEES_CAMPAIGNS  + "." + COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1
            + ", " + TABLE_EMPLOYEES_CAMPAIGNS  + "." + COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1 + ", "
            + TABLE_EMPLOYEES_CAMPAIGNS  + "." + COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1 + ", " +TABLE_EMPLOYEES + "." +
    COLUMN_EMPLOYEES_EMPLOYEE_ID + ", " + TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_FIRST_NAME +
            ", " + TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_LAST_NAME + ", " + TABLE_CAMPAIGNS + "." +
    COLUMN_CAMPAIGNS_CAMP_NAME + ", " + TABLE_SHIFTS + "." + COLUMN_SHIFTS_SCHEDULE + ", " +
    TABLE_POSITIONS + "." + COLUMN_POSITIONS_TITLE +
            " FROM " + TABLE_EMPLOYEES_CAMPAIGNS +
            " JOIN " + TABLE_EMPLOYEES + " ON " + TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1
            + " = " + TABLE_EMPLOYEES + "." + COLUMN_EMPLOYEES_PK1 +
            " JOIN " + TABLE_CAMPAIGNS + " ON " + TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1
            + " = " + TABLE_CAMPAIGNS + "." + COLUMN_CAMPAIGNS_PK1 +
            " JOIN " + TABLE_SHIFTS + " ON " + TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1
            + " = " + TABLE_SHIFTS + "." + COLUMN_SHIFTS_PK1 +
            " JOIN " + TABLE_POSITIONS + " ON " + TABLE_EMPLOYEES_CAMPAIGNS + "." + COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1
            + " = " + TABLE_POSITIONS + "." + COLUMN_POSITIONS_PK1;

    StringBuilder sb = new StringBuilder();
    private Connection conn;

    public Connection connect() throws SQLException {
        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("ERROR Unable to connect to PostgreSQL server: "+ e.getMessage());
        }

        return conn;
    }

    public List<SummarizeData> querySummarize () throws  SQLException{

        StringBuilder sb = new StringBuilder(QUERY_SUMMARIZE_DATA_RETRIEVE);
        //System.out.println(sb.toString());

        try (Connection db = connect();
            Statement statement1 = db.createStatement();
            ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<SummarizeData> summarizeData = new ArrayList<>();
            while (results1.next()) {
                SummarizeData sumData = new SummarizeData();
                sumData.setEmployees_pk1(results1.getDouble(COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1));
                sumData.setCampaigns_pk1(results1.getDouble(COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1));
                sumData.setShifts_pk1(results1.getDouble(COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1));
                sumData.setPositions_pk1(results1.getDouble(COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1));
                sumData.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                sumData.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                sumData.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                sumData.setCampaign_name(results1.getString(COLUMN_CAMPAIGNS_CAMP_NAME));
                sumData.setShift(results1.getString(COLUMN_SHIFTS_SCHEDULE));
                sumData.setPosition(results1.getString(COLUMN_POSITIONS_TITLE));

                summarizeData.add(sumData);
            }
            closeConnection(db);
            return summarizeData;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    public List<SummarizeData> querySummarize (String empCheck, String natCheck, String firstCheck, String lastCheck, String empEmailCheck,
                                               String campNameCheck, String campIdCheck,
                                               String shiftCheck) throws  SQLException{

        StringBuilder sb = new StringBuilder(QUERY_SUMMARIZE_DATA_RETRIEVE);
        boolean preCondition = false;
        sb.append(" WHERE ");
        if (!empCheck.isEmpty()) {
            if(preCondition) {
                sb.append(" AND ");
            }
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

        System.out.println(sb.toString());
        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery(sb.toString())){

            List<SummarizeData> summarizeData = new ArrayList<>();
            while (results1.next()) {
                SummarizeData sumData = new SummarizeData();
                sumData.setEmployees_pk1(results1.getDouble(COLUMN_EMPLOYEES_CAMPAIGNS_EMPLOYEES_PK1));
                sumData.setCampaigns_pk1(results1.getDouble(COLUMN_EMPLOYEES_CAMPAIGNS_CAMPAIGNS_PK1));
                sumData.setShifts_pk1(results1.getDouble(COLUMN_EMPLOYEES_CAMPAIGNS_SHIFTS_PK1));
                sumData.setPositions_pk1(results1.getDouble(COLUMN_EMPLOYEES_CAMPAIGNS_POSITIONS_PK1));
                sumData.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                sumData.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                sumData.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                sumData.setCampaign_name(results1.getString(COLUMN_CAMPAIGNS_CAMP_NAME));
                sumData.setShift(results1.getString(COLUMN_SHIFTS_SCHEDULE));
                sumData.setPosition(results1.getString(COLUMN_POSITIONS_TITLE));

                summarizeData.add(sumData);
            }
            closeConnection(db);
            return summarizeData;
        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    public List<Employee> queryEmployee () throws SQLException {

        try (Connection db = connect();
             Statement statement1 = db.createStatement();
             ResultSet results1 = statement1.executeQuery("SELECT * FROM " + TABLE_EMPLOYEES)){

            List<Employee> employeesList = new ArrayList<>();
            while (results1.next()) {
                Employee employee = new Employee();
                employee.setPk1(results1.getDouble(COLUMN_EMPLOYEES_PK1));
                employee.setBatch_uid(results1.getString(COLUMN_EMPLOYEES_BATCH_UID));
                employee.setEmployee_id(results1.getString(COLUMN_EMPLOYEES_EMPLOYEE_ID));
                employee.setFirst_name(results1.getString(COLUMN_EMPLOYEES_FIRST_NAME));
                employee.setLast_name(results1.getString(COLUMN_EMPLOYEES_LAST_NAME));
                employee.setEmail(results1.getString(COLUMN_EMPLOYEES_EMAIL));
                employee.setSalary(results1.getDouble(COLUMN_EMPLOYEES_SALARY));
                employee.setContract_date(results1.getDate(COLUMN_EMPLOYEES_CONTRACT_DATE));
                employee.setNational_id(results1.getString(COLUMN_EMPLOYEES_NATIONAL_ID));

                employeesList.add(employee);
            }
            closeConnection(db);
            return employeesList;

        } catch (SQLException e) {
            System.out.println("ERROR while trying to query: " + e.getMessage());
            return null;
        }
    }

    public void insertStatement (String statement) throws SQLException {
        try{
            Connection db = connect();
            Statement statement1 = db.createStatement();
            statement1.executeUpdate(statement);
            closeConnection(db);
            System.out.println("Item added successfully");
        } catch (SQLException e) {
            System.out.println("ERROR while trying to insert: " + e.getMessage());
        }
    }

    public static void closeConnection (Connection conn) throws SQLException {
        conn.close();
    }

}
