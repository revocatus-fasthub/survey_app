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
    private String position;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qsnId")
    private Question answerId;

    public Answer() {
    }

    public Answer(String ans, int sequence, String position, Question answerId) {
        this.ans = ans;
        this.sequence = sequence;
        this.position = position;
        this.answerId = answerId;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Question getQuestion() {
        return answerId;
    }

    public void setQuestion(Question question) {
        this.answerId = question;
    }
}
