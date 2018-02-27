package tz.co.fasthub.survey.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by daniel on 2/22/18.
 */
public class TransactionTemp {
    private Long id;
    private String msisdn;
    private String  question;
    private String answer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "Africa/Dar_es_Salaam")
    private Date time_stamp;
    private boolean attended;


    public TransactionTemp(Long id, String msisdn, String question, String answer, Date time_stamp, boolean attended) {
        this.id = id;
        this.msisdn = msisdn;
        this.question = question;
        this.answer = answer;
        this.time_stamp = time_stamp;
        this.attended = attended;
    }

    public TransactionTemp() {
    }

    @Override
    public String toString() {
        return "TransactionTemp{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", time_stamp=" + time_stamp +
                ", attended=" + attended +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
