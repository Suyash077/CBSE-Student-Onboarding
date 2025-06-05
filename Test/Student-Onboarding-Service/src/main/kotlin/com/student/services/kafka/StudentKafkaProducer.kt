package com.student.services.kafka

import com.student.dto.StudentAddedEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class StudentKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, StudentAddedEvent>
) {
    fun publishStudentAddedEvent(event: StudentAddedEvent) {
        println("Sending student added event to CBSE")
        kafkaTemplate.send("student-added", event)
    }
}