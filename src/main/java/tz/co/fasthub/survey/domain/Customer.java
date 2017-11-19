package tz.co.fasthub.survey.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by naaminicharles on 7/25/17.
 */
@Entity
public class Customer {

    @GeneratedValue
    @Id
    private Long id;
    @NotNull
    @Column(unique = true)
    private String msisdn;
    private String email;

    @OneToMany(mappedBy = "customer")
    @Cascade(CascadeType.ALL)
    private List<CustomerTransaction> customerTransaction;

    @OneToMany(mappedBy = "customer")
    @Cascade(CascadeType.ALL)
    private List<OpenEndedAnswer> openEndedAnswers;

    public Customer() {
    }

    public Customer(String msisdn) {
        this.msisdn = msisdn;
    }

    public Customer(String msisdn, String email) {
        this.msisdn = msisdn;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
