package com.student

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class StudentApplication

fun main(args: Array<String>) {
	runApplication<StudentApplication>(*args)
}
