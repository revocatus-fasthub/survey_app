package tz.co.fasthub.survey.domain;

/**
 * Created by root on 6/20/17.
 */
public class Survey {
    private int qsnId;
    private String qsn;

    public Survey() {
    }

    public Survey(int qsnId, String qsn) {
        this.qsnId = qsnId;
        this.qsn = qsn;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "qsnId=" + qsnId +
                ", qsn='" + qsn + '\'' +
                '}';
    }

    public int getQsnId() {
        return qsnId;
    }

    public void setQsnId(int qsnId) {
        this.qsnId = qsnId;
    }

    public String getQsn() {
        return qsn;
    }

    public void setQsn(String qsn) {
        this.qsn = qsn;
    }
}
