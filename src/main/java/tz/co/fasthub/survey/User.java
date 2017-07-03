package tz.co.fasthub.survey;

/**
 * Created by root on 6/30/17.
 */
public class User {

    private Long id;
    private String accessToken;

    public User() {
    }

    public User(Long id, String accessToken) {
        this.id = id;
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

