package sample;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Campaign {

    private double pk1;
    private String batch_uid;
    private String name;
    private String campaign_id;
    private DateTimeFormatter contract_date;
    private DateTimeFormatter renew_date;
    private double headcount_req;
    private double revenue;
    private String priority;

    public Campaign() {
    }

    public Campaign(double pk1, String batch_uid, String name, String campaign_id, DateTimeFormatter contract_date, DateTimeFormatter renew_date, double headcount_req, double revenue, String priority) {
        this.pk1 = pk1;
        this.batch_uid = batch_uid;
        this.name = name;
        this.campaign_id = campaign_id;
        this.contract_date = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.renew_date = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.headcount_req = headcount_req;
        this.revenue = revenue;
        this.priority = priority;
    }

    public double getPk1() {
        return pk1;
    }

    public void setPk1(double pk1) {
        this.pk1 = pk1;
    }

    public String getBatch_uid() {
        return batch_uid;
    }

    public void setBatch_uid(String batch_uid) {
        this.batch_uid = batch_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public DateTimeFormatter getContract_date() {
        return contract_date;
    }

    public void setContract_date(DateTimeFormatter contract_date) {
        this.contract_date = contract_date;
    }

    public DateTimeFormatter getRenew_date() {
        return renew_date;
    }

    public void setRenew_date(DateTimeFormatter renew_date) {
        this.renew_date = renew_date;
    }

    public double getHeadcount_req() {
        return headcount_req;
    }

    public void setHeadcount_req(double headcount_req) {
        this.headcount_req = headcount_req;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
