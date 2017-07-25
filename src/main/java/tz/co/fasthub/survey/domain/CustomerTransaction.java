package tz.co.fasthub.survey.domain;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by root on 7/25/17.
 */
@Entity
public class CustomerTransaction {

    @GeneratedValue
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "msisdn")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question")
    private Question question;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer")
    private Answer answer;

    private Date timestamp;

    @JoinColumn(name = "attended")
    private Boolean attended;

    public CustomerTransaction() {
    }

    public CustomerTransaction(Customer customer, Question question, Answer answer, Date timestamp, Boolean attended) {
        this.customer = customer;
        this.question = question;
        this.answer = answer;
        this.timestamp = timestamp;
        this.attended = attended;
    }

    @Override
    public String toString() {
        return "CustomerTransaction{" +
                "id=" + id +
                ", customer=" + customer +
                ", question=" + question +
                ", answer=" + answer +
                ", timestamp=" + timestamp +
                ", attended=" + attended +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getAttended() {
        return attended;
    }

    public void setAttended(Boolean attended) {
        this.attended = attended;
    }
}
