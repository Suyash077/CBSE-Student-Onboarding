package com.cbse.services.kafka

import  com.student.dto.RetryTriggerEvent
import com.cbse.entities.RetryStatus
import com.cbse.repositories.RetryConfigRepository
import com.cbse.repositories.RetryRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime

@Service
class RetryKafkaConsumer(
    private val retryRepository: RetryRepository,
    private val webClient: WebClient,
    private val retryConfigRepository: RetryConfigRepository
) {

    @KafkaListener(topics = ["retry-trigger"], groupId = "retry-handler-group")
    fun consumeRetryTrigger(event: RetryTriggerEvent){
        retryRepository.findById(event.retryId)
            .flatMap { retryEvent ->
                retryConfigRepository.findByTaskType(retryEvent.taskType)
                    .flatMap { config ->
                        val now= LocalDateTime.now()
                        if(retryEvent.version >= config.maxRetryCount){
                            val failed = retryEvent.copy(status = RetryStatus.FAILED, lastRunDate = now, nextRunTime = now)
                            retryRepository.save(failed)
                        }
                        else {
                            webClient.post()
                                .uri("/cbse/onboard")
                                .bodyValue(retryEvent.requestMetadata)
                                .retrieve()
                                .bodyToMono(RetryStatus::class.java)
                                .onErrorReturn(RetryStatus.OPEN)
                                .flatMap { newStatus ->
                                    val updated = retryEvent.copy(
                                        status = newStatus,
                                        version = retryEvent.version + 1,
                                        lastRunDate = now,
                                        nextRunTime = if (newStatus == RetryStatus.OPEN)
                                            now.plusMinutes(config.retryAfterInMins.toLong()) else now
                                    )
                                    println("Retry attempt for rollNo: ${retryEvent.studentRollNo}, new status: $newStatus")
                                    retryRepository.save(updated)
                                }
                        }
                    }

            }
            .doOnError { e -> println("Error during retry processing: ${e.message}") }
            .subscribe()
    }
}
