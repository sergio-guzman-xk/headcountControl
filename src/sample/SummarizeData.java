package sample;

public class SummarizeData {

    private String employee_id;
    private String first_name;
    private String last_name;
    private String campaign_name;
    private String shift;
    private String position;
    private int employees_pk1;
    private int campaigns_pk1;
    private int shifts_pk1;
    private int positions_pk1;

    public SummarizeData() {
    }

    public SummarizeData(String employee_id, String first_name, String last_name, String campaign_name, String shift,
                         String position, int employees_pk1, int campaigns_pk1, int shifts_pk1, int positions_pk1) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.campaign_name = campaign_name;
        this.shift = shift;
        this.position = position;
        this.employees_pk1 = employees_pk1;
        this.campaigns_pk1 = campaigns_pk1;
        this.shifts_pk1 = shifts_pk1;
        this.positions_pk1 = positions_pk1;
    }

    public int getEmployees_pk1() {
        return employees_pk1;
    }

    public void setEmployees_pk1(int employees_pk1) {
        this.employees_pk1 = employees_pk1;
    }

    public int getCampaigns_pk1() {
        return campaigns_pk1;
    }

    public void setCampaigns_pk1(int campaigns_pk1) {
        this.campaigns_pk1 = campaigns_pk1;
    }

    public int getShifts_pk1() {
        return shifts_pk1;
    }

    public void setShifts_pk1(int shifts_pk1) {
        this.shifts_pk1 = shifts_pk1;
    }

    public int getPositions_pk1() {
        return positions_pk1;
    }

    public void setPositions_pk1(int positions_pk1) {
        this.positions_pk1 = positions_pk1;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
