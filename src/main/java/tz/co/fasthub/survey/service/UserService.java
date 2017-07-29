package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.User;

/**
 * Created by root on 7/27/17.
 */
public interface UserService {


    void save(User user);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User validateUser(String username, String password);

    User encryptUserInput(String username, String password);
}
