package com.cbse.services.kafka

import com.student.dto.RetryTriggerEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class RetryKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, RetryTriggerEvent>
) {
    fun publishRetryTriggerEvent(event: RetryTriggerEvent) {
        kafkaTemplate.send("retry-trigger", event)
        println("Published RetryTriggerEvent for ${event.retryId}")
    }
}
