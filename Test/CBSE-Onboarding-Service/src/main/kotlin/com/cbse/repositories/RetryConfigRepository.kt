package com.cbse.repositories

import com.cbse.entities.RetryConfig
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface RetryConfigRepository : ReactiveMongoRepository<RetryConfig, String> {
    fun findByTaskType(taskType: String): Mono<RetryConfig>
}