package sample;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Employee {

    private int pk1;
    private String batch_uid;
    private String employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private double salary;
    private Date contract_date;
    private String national_id;

    public Employee() {
    }

    public Employee(int pk1, String batch_uid, String employee_id, String first_name, String last_name, String email, double salary, Date contract_date, String national_id) {
        this.pk1 = pk1;
        this.batch_uid = batch_uid;
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.salary = salary;
        this.contract_date = contract_date;
        this.national_id = national_id;
    }

    public void setPk1(int pk1) {
        this.pk1 = pk1;
    }

    public void setBatch_uid(String batch_uid) {
        this.batch_uid = batch_uid;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setContract_date(Date contract_date) {
        this.contract_date = contract_date;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public int getPk1() {
        return pk1;
    }

    public String getBatch_uid() {
        return batch_uid;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public double getSalary() {
        return salary;
    }

    public Date getContract_date() {
        return contract_date;
    }

    public String getNational_id() {
        return national_id;
    }
}
