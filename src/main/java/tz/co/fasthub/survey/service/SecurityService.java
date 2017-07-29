package tz.co.fasthub.survey.service;

/**
 * Created by root on 7/27/17.
 */
public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);

}
