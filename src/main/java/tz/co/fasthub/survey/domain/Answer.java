package tz.co.fasthub.survey.domain;


import org.springframework.beans.factory.annotation.Autowired;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qsnId")
    private Question question;

    private static AnswerRepository answerRepository;
    private static AnswerService answerService;
    private static QuestionService questionService;
    public Answer() {
    }

    public Answer(String ans, int sequence, int position, Question question) {
        this.ans = ans;
        this.sequence = sequence;
        this.position=position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        System.out.println(position);
        if(this.getId()==0){
            Question qs = questionService.getQsnById(this.getId());
            if(qs != null){
                List<Answer> answers = answerService.getAnswerByQsnId(qs);
                if(!answers.isEmpty()){
                    this.position = answers.size()+1;
                }
            }
        }else{
            this.position = position;
        }

    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
