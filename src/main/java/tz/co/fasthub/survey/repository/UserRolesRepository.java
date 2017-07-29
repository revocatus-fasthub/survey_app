package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.User;

import java.util.List;

/**
 * Created by root on 7/28/17.
 */
public interface UserRolesRepository extends CrudRepository<User, Long>{
    List<String> findRoleByUsername(String username);
}
