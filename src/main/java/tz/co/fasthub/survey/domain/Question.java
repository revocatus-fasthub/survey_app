package tz.co.fasthub.survey.domain;

import javax.persistence.*;

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


    @OneToOne(mappedBy = "answerId")
    private Answer answer;

    public Question() {
    }

    public Question(String qsn, int sequence) {
        this.qsn = qsn;
        this.sequence = sequence;
    }


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", qsn='" + qsn + '\'' +
                ", sequence=" + sequence +
                ", version=" + version +
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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}

