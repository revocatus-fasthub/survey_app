package tz.co.fasthub.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by root on 6/7/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {

    private int channel;
    private String password;

    public Channel(int channel, String password) {
        this.channel = channel;
        this.password = password;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
