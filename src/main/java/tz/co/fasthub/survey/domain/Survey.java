package tz.co.fasthub.survey.domain;

/**
 * Created by root on 6/20/17.
 */

public class Survey {
    private int id;
    private String qsn;

    public Survey() {
    }

    public Survey(int id, String qsn) {
        this.id = id;
        this.qsn = qsn;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", qsn='" + qsn + '\'' +
                '}';
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getqsn() {
        return qsn;
    }

    public void setqsn(String qsn) {
        this.qsn = qsn;
    }
}
