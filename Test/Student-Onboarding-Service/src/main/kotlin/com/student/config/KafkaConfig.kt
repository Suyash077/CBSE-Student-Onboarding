package com.student.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig {

    @Bean
    fun studentAddedTopic(): NewTopic {
        return NewTopic("student-added", 1, 1.toShort())
    }

}