package tz.co.fasthub.survey.domain;

/**
 * Created by root on 6/20/17.
 */

public class Survey {
    private int id;
    private String access_token;

    public Survey() {
    }

    public Survey(int id, String access_token) {
        this.id = id;
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", access_token='" + access_token + '\'' +
                '}';
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
