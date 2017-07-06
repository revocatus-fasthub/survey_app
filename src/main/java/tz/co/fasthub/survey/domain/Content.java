package tz.co.fasthub.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by root on 6/16/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {
    private Channel channel;
    private String question;

    public Content() {
    }

    public Content(Channel channel, String question) {
        this.channel = channel;
        this.question = question;
    }

    @Override
    public String toString() {
        return "Content{" +
                "channel=" + channel +
                ", question='" + question + '\'' +
                '}';
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
