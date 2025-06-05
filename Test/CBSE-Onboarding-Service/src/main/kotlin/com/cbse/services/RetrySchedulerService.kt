package com.cbse.services

import com.student.dto.RetryTriggerEvent
import com.student.entities.RetryStatus
import com.cbse.repositories.RetryRepository
import com.cbse.services.kafka.RetryKafkaProducer
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RetrySchedulerService(
    private val retryRepository: RetryRepository,
    private val retryKafkaProducer: RetryKafkaProducer
) {
    @Scheduled(fixedDelay = 60 * 1000)
    fun scheduleRetries() {
        val now = LocalDateTime.now()
        println("RetryScheduler triggered at: $now")
        retryRepository.findAllByStatus(RetryStatus.OPEN)// status == OPEN
            .map { retryEvent ->
                RetryTriggerEvent(retryId = retryEvent.retryId, taskType = retryEvent.taskType)
            }
            .doOnNext { retryKafkaProducer.publishRetryTriggerEvent(it) }
            .subscribe()
    }
}
