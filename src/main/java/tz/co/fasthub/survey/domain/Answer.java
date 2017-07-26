package tz.co.fasthub.survey.domain;


import tz.co.fasthub.survey.repository.AnswerRepository;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.QuestionService;

import javax.persistence.*;
import java.util.List;

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
    private int position;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "qsnId")
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.DETACH)
    private List<CustomerTransaction> customerTransaction;

    private static AnswerRepository answerRepository;
    private static AnswerService answerService;
    private static QuestionService questionService;
    public Answer() {
    }

    public Answer(String ans, int sequence, int position, Question question) {
        this.ans = ans;
        this.sequence = sequence;
        this.position = position;
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", ans='" + ans + '\'' +
                ", sequence=" + sequence +
                ", position=" + position +
                ", question=" + question +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return id.equals(answer.id);
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
            this.position = position;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public static AnswerRepository getAnswerRepository() {
        return answerRepository;
    }

    public static void setAnswerRepository(AnswerRepository answerRepository) {
        Answer.answerRepository = answerRepository;
    }

    public static AnswerService getAnswerService() {
        return answerService;
    }

    public static void setAnswerService(AnswerService answerService) {
        Answer.answerService = answerService;
    }

    public static QuestionService getQuestionService() {
        return questionService;
    }

    public static void setQuestionService(QuestionService questionService) {
        Answer.questionService = questionService;
    }

}
