package com.cbse.services.kafka

import com.student.dto.StudentAddedEvent
import com.cbse.entities.RetryEvent
import com.cbse.entities.RetryStatus
import com.cbse.repositories.RetryConfigRepository
import com.cbse.repositories.RetryRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime
import java.util.*

@Service
class CbseKafkaConsumer(
    private val webClient: WebClient,
    private val retryRepository: RetryRepository,
    private val retryConfigRepository: RetryConfigRepository
) {
    @KafkaListener(topics = ["student-added"], groupId = "cbse-registration-group")
    fun consumeStudentAddedEvent(student : StudentAddedEvent){
        println("Received StudentAddedEvent: $student")

        webClient.post()
            .uri("/cbse/onboard")
            .bodyValue(student)
            .retrieve()
            .bodyToMono(RetryStatus :: class.java)
            .onErrorReturn(RetryStatus.OPEN) // retry on API or network failure
            .flatMap { status ->
                retryConfigRepository.findByTaskType("CBSE_ONBOARDING")
                    .flatMap { config ->
                        println("Received RetryStatus: $status for rollNo: ${student.rollNo}")
                        val now = LocalDateTime.now()
                        val retryEvent = RetryEvent(
                            retryId = UUID.randomUUID(),
                            studentRollNo = student.rollNo,
                            taskType = "CBSE_ONBOARDING",
                            requestMetadata = student,
                            responseMetadata = mapOf("status" to status),
                            createdDate = now,
                            lastRunDate = now,
                            nextRunTime = if (status == RetryStatus.OPEN) now.plusMinutes(config.retryAfterInMins.toLong()) else now,
                            version = 1,
                            status = status
                        )
                        retryRepository.save(retryEvent)
                    }
            }
            .doOnError { error ->  println("Error during onboarding: ${error.message}") }
            .subscribe()
    }
}
