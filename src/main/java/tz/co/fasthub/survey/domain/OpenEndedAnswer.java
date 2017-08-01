package tz.co.fasthub.survey.domain;

import javax.persistence.*;

/**
 * Created by root on 8/1/17.
 */
@Entity
public class OpenEndedAnswer {

    @Id
    @GeneratedValue
    private Long id;
    private String answer;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "qsnId")
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public OpenEndedAnswer() {
    }


    public OpenEndedAnswer(Customer customer, Question question) {
        this.question = question;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "OpenEndedAnswer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
