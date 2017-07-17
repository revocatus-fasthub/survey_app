package tz.co.fasthub.survey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by root on 7/13/17.
 */
@Entity
public class Contact {
    @GeneratedValue
    @Id
    private Long DbId;
    private String href;
    private String first_name;
    private String last_name;
    @Column(name = "contactId")
    private String id;
    private String email;
    private String phoneNumber;

    public Contact() {
    }

    public Contact(String href, String first_name, String last_name, String id, String email,String phoneNumber) {
        this.href = href;
        this.first_name = first_name;
        this.last_name = last_name;
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "DbId=" + DbId +
                ", href='" + href + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public Long getDbId() {
        return DbId;
    }

    public void setDbId(Long dbId) {
        DbId = dbId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {//255689328984@fasthub.com
        return getEmail().substring(0,11);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = getEmail().substring(0,11);
    }
}

