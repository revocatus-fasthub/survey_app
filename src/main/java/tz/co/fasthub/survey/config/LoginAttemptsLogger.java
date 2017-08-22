package tz.co.fasthub.survey.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import tz.co.fasthub.survey.domain.AuditLog;
import tz.co.fasthub.survey.service.AuditLogService;

/**
 * Created by naaminicharles on 8/21/17.
 */
@Component
public class LoginAttemptsLogger {

    private final Logger log = LoggerFactory.getLogger(LoginAttemptsLogger.class);

    private AuditLogService auditLogService;

    public LoginAttemptsLogger(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @EventListener
    public void auditEventHappened(AuditApplicationEvent auditApplicationEvent) {

        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();

        log.info("Principal " + auditEvent.getPrincipal() + " - " + auditEvent.getType());

        WebAuthenticationDetails details = (WebAuthenticationDetails) auditEvent.getData().get("details");

        log.info("  Remote IP address: " + details.getRemoteAddress());
        log.info("  Session Id: " + details.getSessionId());
        log.info("  Request URL: " + auditEvent.getData().get("requestUrl"));


        AuditLog auditLog = new AuditLog();
            auditLog.setUsername(auditEvent.getPrincipal());
            auditLog.setType(auditEvent.getType());
            auditLog.setRemote_ip_address(details.getRemoteAddress());
            auditLog.setSession_id(details.getSessionId());
            auditLog.setRequest_url(String.valueOf(auditEvent.getData().get("requestUrl")));
        auditLogService.save(auditLog);
    }
}
