package tz.co.fasthub.survey.domain;

import javax.persistence.*;

/**
 * Created by root on 7/25/17.
 */
@Entity
public class Customer {

    @GeneratedValue
    @Id
    private Long id;
    private String msisdn;
    private String email;

    @OneToOne(mappedBy = "customer")
    private CustomerTransaction customerTransaction;

    public Customer() {
    }

    public Customer(String msisdn, String email, CustomerTransaction customerTransaction) {
        this.msisdn = msisdn;
        this.email = email;
        this.customerTransaction = customerTransaction;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", email='" + email + '\'' +
                ", customerTransaction=" + customerTransaction +
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

    public CustomerTransaction getCustomerTransaction() {
        return customerTransaction;
    }

    public void setCustomerTransaction(CustomerTransaction customerTransaction) {
        this.customerTransaction = customerTransaction;
    }
}
