package tz.co.fasthub.survey.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by root on 7/17/17.
 */

@Entity
public class Answer implements Serializable{

    private static final long serialVersionUID = 1L;

    @GeneratedValue
    @Id
    private Long id;
    private String ans;
    private int sequence;
    private String position;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Question.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "qsnId")
    private Question question;

    public Answer() {
    }

    public Answer(String ans, int sequence, String position, Question question) {
        this.ans = ans;
        this.sequence = sequence;
        this.position = position;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Question getQuestion() {

        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
