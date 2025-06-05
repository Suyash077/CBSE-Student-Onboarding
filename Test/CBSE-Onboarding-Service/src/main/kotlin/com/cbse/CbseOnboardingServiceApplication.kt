package com.cbse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CbseOnboardingServiceApplication

fun main(args: Array<String>) {
	runApplication<CbseOnboardingServiceApplication>(*args)
}
