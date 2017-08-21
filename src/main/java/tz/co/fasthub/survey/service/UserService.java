package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.User;

/**
 * Created by naaminicharles on 7/27/17.
 */
public interface UserService {


    void save(User user);

    void update(User user);

    User findByUsername(String username);

    Iterable<User> listAllCustomers();

    User getUserById(Long id);

    void deleteUser(Long id);
}
