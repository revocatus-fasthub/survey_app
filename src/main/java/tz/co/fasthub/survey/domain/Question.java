package tz.co.fasthub.survey.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by root on 7/17/17.
 */
@Entity
public class Question {

    @GeneratedValue
    @Id
    private Long id;
    private String qsn;
    private int sequence;
    @Version
    private Long version;

    @OneToMany(mappedBy = "question")
    private List<Answer> answer;

    @OneToOne(mappedBy = "question")
    private CustomerTransaction customerTransaction;

    public Question() {
    }

    public Question(String qsn, int sequence, Long version, List<Answer> answer, CustomerTransaction customerTransaction) {
        this.qsn = qsn;
        this.sequence = sequence;
        this.version = version;
        this.answer = answer;
        this.customerTransaction = customerTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return id.equals(question.id);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", qsn='" + qsn + '\'' +
                ", sequence=" + sequence +
                ", version=" + version +
                ", answer=" + answer +
                ", customerTransaction=" + customerTransaction +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQsn() {
        return qsn;
    }

    public void setQsn(String qsn) {
        this.qsn = qsn;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public CustomerTransaction getCustomerTransaction() {
        return customerTransaction;
    }

    public void setCustomerTransaction(CustomerTransaction customerTransaction) {
        this.customerTransaction = customerTransaction;
    }
}

