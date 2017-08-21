package tz.co.fasthub.survey.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by naaminicharles on 7/27/17.
 */
@Entity
public class User implements UserDetails{

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String cpassword;
    private String role;
    @Version
    private Long version;
    private boolean enabled;

    public User() {
        enabled = true;
    }

    public User(String username, String password, String cpassword,String role, Long version) {
        this();
        this.username = username;
        this.password = password;
        this.cpassword = cpassword;
        this.role = role;
        this.version = version;
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


    public boolean isSelected(Long userId){
        if (userId != null) {
            return userId.equals(id);
        }
        return false;
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role));
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
