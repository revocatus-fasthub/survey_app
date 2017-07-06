package tz.co.fasthub.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.co.fasthub.survey.domain.Payload;

/**
 * Created by root on 7/4/17.
 */
public interface PayloadRepository extends JpaRepository<Payload, String>{
    Payload findById(Integer id);
}