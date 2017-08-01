package tz.co.fasthub.survey.domain;

import javax.persistence.*;

/**
 * Created by root on 7/27/17.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String cpassword;
    private String role;
    @Version
    private Long version;


    public User() {
    }

    public User(String username, String password, String cpassword,String role, Long version) {
        this.username = username;
        this.password = password;
        this.cpassword = cpassword;
        this.role = role;
        this.version = version;
    }

    public User(User user) {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cpassword='" + cpassword + '\'' +
                ", role='" + role + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
