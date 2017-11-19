package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.AuditLog;

/**
 * Created by naaminicharles on 8/21/17.
 */
public interface AuditLogService {
    AuditLog save(AuditLog auditLog);
    Iterable<AuditLog> listAllAuditEvents();
}
