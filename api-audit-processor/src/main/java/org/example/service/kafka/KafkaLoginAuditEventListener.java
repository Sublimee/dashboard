package org.example.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaLoginAuditEventListener {

    @KafkaListener(topics = "login-audit", groupId = "api-audit-processor")
    void listener(String data) {
        log.info("Received message [{}]", data);
    }
}