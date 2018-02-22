package tz.co.fasthub.survey.domain;

import java.util.Date;

/**
 * Created by daniel on 2/22/18.
 */
public class TransactionTemp {
    private Long id;

    private String msisdn;

    private String  question;

    private String answer;

    private Date timestamp;

    private boolean attended;

    private String answerDetails;



    public TransactionTemp() {
    }

    public TransactionTemp(Long id, String msisdn, String question, String answer, Date timestamp, boolean attended, String answerDetails) {
        this.id = id;
        this.msisdn = msisdn;
        this.question = question;
        this.answer = answer;
        this.timestamp = timestamp;
        this.attended = attended;
        this.answerDetails = answerDetails;
    }


    @Override
    public String toString() {
        return "TransactionTemp{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", timestamp=" + timestamp +
                ", attended=" + attended +
                ", answerDetails='" + answerDetails + '\'' +
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public String getAnswerDetails() {
        return answerDetails;
    }

    public void setAnswerDetails(String answerDetails) {
        this.answerDetails = answerDetails;
    }
}
