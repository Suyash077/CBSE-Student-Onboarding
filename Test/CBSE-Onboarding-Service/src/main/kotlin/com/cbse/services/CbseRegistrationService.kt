package com.cbse.services

import com.student.dto.StudentAddedEvent
import com.cbse.exceptions.StudentAlreadyEnrolledException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CbseRegistrationService {

    fun registerStudent(student: StudentAddedEvent): Mono<String> {
        return when (student.rollNo) {
            "1" -> Mono.just("Success")
            "2" -> Mono.error(RuntimeException("Server Error"))
            else -> Mono.error(StudentAlreadyEnrolledException("Student Already Enrolled"))
        }
    }
}