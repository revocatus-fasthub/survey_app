package tz.co.fasthub.survey.domain;

import javax.persistence.*;

/**
 * Created by root on 7/17/17.
 */

@Entity
public class Answer {

    @GeneratedValue
    @Id
    private Long id;
    private String ans;
    private int sequence;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qsnId")
    private Question question;

    public Answer() {
    }

    public Answer(String ans, int sequence, Question question) {
        this.ans = ans;
        this.sequence = sequence;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
