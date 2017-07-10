package tz.co.fasthub.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by root on 6/16/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {
    private Channel channel;
    private List<MessageHandler> messages;

    public Content() {
    }

    public Content(Channel channel, List<MessageHandler> messages) {
        this.channel = channel;
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Content{" +
                "channel=" + channel +
                ", messages=" + messages +
                '}';
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<MessageHandler> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageHandler> messages) {
        this.messages = messages;
    }
}
