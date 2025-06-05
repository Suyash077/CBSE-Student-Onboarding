package com.cbse.services

import com.student.dto.StudentAddedEvent
import com.student.entities.RetryStatus
import com.cbse.exceptions.StudentAlreadyEnrolledException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CbseOnboardingService(
    private val cbseRegistrationService: CbseRegistrationService
) {
    fun onboardStudent(student: StudentAddedEvent): Mono<RetryStatus> {
        return cbseRegistrationService.registerStudent(student)
            .thenReturn(RetryStatus.CLOSED)
            .onErrorResume { e ->
                when (e) {
                    is StudentAlreadyEnrolledException -> Mono.just(RetryStatus.FAILED)
                    else -> Mono.just(RetryStatus.OPEN)
                }
            }
    }
}