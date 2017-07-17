package tz.co.fasthub.survey.domain;

import java.util.ArrayList;

/**
 * Created by root on 7/14/17.
 */
public class ContactHandler {

    private ArrayList<Contact> data;

    public ContactHandler() {
    }

    public ContactHandler(ArrayList<Contact> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ContactHandler{" +
                "data=" + data +
                '}';
    }

    public ArrayList<Contact> getData() {
        return data;
    }

    public void setData(ArrayList<Contact> data) {
        this.data = data;
    }
}
