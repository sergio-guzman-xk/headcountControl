package sample;

public class Shift {

    private int pk1;
    private String batch_uid;
    private String description;
    private String time;

    public Shift() {
    }

    public Shift(int pk1, String batch_uid, String description, String time) {
        this.pk1 = pk1;
        this.batch_uid = batch_uid;
        this.description = description;
        this.time = time;
    }

    public int getPk1() {
        return pk1;
    }

    public void setPk1(int pk1) {
        this.pk1 = pk1;
    }

    public String getBatch_uid() {
        return batch_uid;
    }

    public void setBatch_uid(String batch_uid) {
        this.batch_uid = batch_uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
