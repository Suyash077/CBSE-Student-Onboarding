package com.cbse.controllers

import com.cbse.services.CbseOnboardingService
import com.student.dto.StudentAddedEvent
import com.cbse.entities.RetryStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/cbse")
@Validated
class CbseController(
    private val cbseOnboardingService: CbseOnboardingService
) {
    @PostMapping("/onboard")
    fun onboard(@RequestBody student: StudentAddedEvent): Mono<RetryStatus> {
        return cbseOnboardingService.onboardStudent(student)
    }
}