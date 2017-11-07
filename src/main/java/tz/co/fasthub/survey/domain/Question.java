package tz.co.fasthub.survey.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by naaminicharles on 7/17/17.
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

    @NotNull
    private String type;


    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    private List<Answer> answer;

    @OneToMany(mappedBy = "question",cascade = CascadeType.DETACH)
    private List<OpenEndedAnswer> openEndedAnswers;

    @OneToMany(mappedBy = "question",cascade = CascadeType.DETACH)
    private List<CustomerTransaction> customerTransaction;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "userId")
    private User user;


    @NotNull
    private String status;

    @Column(name="is_Checked")
    private String isChecked;//checker
//
//    @Column(name="is_Created")
//    private String isCreated;//maker

    private String comment;

    public Question() {
    }


    public Question(String qsn, int sequence, Long version, String type, String status, String isChecked, String isCreated, String comment) {
        this.qsn = qsn;
        this.sequence = sequence;
        this.version = version;
        this.type = type;
        this.status = status;
        this.isChecked = isChecked;
        this.comment = comment;
//        this.isCreated = isCreated;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", qsn='" + qsn + '\'' +
                ", sequence=" + sequence +
                ", version=" + version +
                ", type=" + type +
                ", status=" + status +
                ", isChecked=" + isChecked +
                ", comment=" + comment +
//                ", isCreated=" + isCreated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return id.equals(question.id);
    }

/*
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
*/

    public boolean isEnabled(Long qsnId){
        if(qsnId!=null){
            return qsnId.equals(id);
        }
        return false;
    }


    public boolean isSelected(Long qsnId){
        if (qsnId != null) {
            return qsnId.equals(id);
        }
        return false;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    /*public String getIsCreated() {
        return isCreated;
    }

    public void setIsCreated(String isCreated) {
        this.isCreated = isCreated;
    }*/

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

