package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.AuditLog;

/**
 * Created by naaminicharles on 8/21/17.
 */
public interface AuditLogRepository extends CrudRepository<AuditLog, Long>{
    }
