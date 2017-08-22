package tz.co.fasthub.survey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by naaminicharles on 8/21/17.
 */
@Entity
public class AuditLog {

    @Id
    @GeneratedValue
    private Long id;
    private String  username;
    @Column(name = "auditEventType")
    private String type;
    private String remote_ip_address;
    private String session_id;
    private String request_url;

    public AuditLog() {
    }

    public AuditLog(String username, String type, String remote_ip_address, String session_id, String request_url) {
        this.username = username;
        this.type = type;
        this.remote_ip_address = remote_ip_address;
        this.session_id = session_id;
        this.request_url = request_url;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemote_ip_address() {
        return remote_ip_address;
    }

    public void setRemote_ip_address(String remote_ip_address) {
        this.remote_ip_address = remote_ip_address;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getRequest_url() {
        return request_url;
    }

    public void setRequest_url(String request_url) {
        this.request_url = request_url;
    }
}
