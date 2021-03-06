package tz.co.fasthub.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.co.fasthub.survey.domain.User;

/**
 * Created by naaminicharles on 7/27/17.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User findByEmail(String email);
}