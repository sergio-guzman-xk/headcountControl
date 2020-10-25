package sample;

public class Positions {

    private int pk1;
    private String batch_uid;
    private String tittle;
    private String description;

    public Positions() {
    }

    public Positions(int pk1, String batch_uid, String tittle, String description) {
        this.pk1 = pk1;
        this.batch_uid = batch_uid;
        this.tittle = tittle;
        this.description = description;
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

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
