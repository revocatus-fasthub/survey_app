package tz.co.fasthub.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by root on 2/21/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageHandler {
    private String reference_id;
    private String text;
    private String msisdn;
    private String source;

    private MessageHandler() {
    }

    public MessageHandler(String reference_id, String text, String msisdn, String source) {
        this.reference_id=reference_id;
        this.text = text;
        this.msisdn = msisdn;
        this.source = source;
    }

    @Override
    public String toString() {
        return "MessageHandler{" +
                "reference_id='" + reference_id + '\'' +
                "text='" + text + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
