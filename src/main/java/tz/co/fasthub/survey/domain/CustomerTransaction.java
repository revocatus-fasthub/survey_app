package tz.co.fasthub.survey.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by naaminicharles on 7/25/17.
 */
@Entity
public class CustomerTransaction {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    private Date timestamp= new Date();

    private Boolean attended=false;

    @Column(length = 1024)
    private String answerDetails;


    public CustomerTransaction() {
    }


    public CustomerTransaction(Customer customer, Question question) {
        this.customer = customer;
        this.question = question;
    }

    public CustomerTransaction(Customer customer, Question question, Answer answer, Date timestamp, Boolean attended, String answerDetails) {
        this.customer = customer;
        this.question = question;
        this.answer = answer;
        this.timestamp = timestamp;
        this.attended = attended;
        this.answerDetails = answerDetails;
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
                ", answerDetails=" + answerDetails +
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

    public String getAnswerDetails() {

        return answerDetails;
    }

    public void setAnswerDetails(String answerDetails) {
        this.answerDetails = answerDetails;
    }
}
