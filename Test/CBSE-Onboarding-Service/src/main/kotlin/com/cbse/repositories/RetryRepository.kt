package com.cbse.repositories

import com.cbse.entities.RetryEvent
import com.cbse.entities.RetryStatus
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*

interface RetryRepository : ReactiveMongoRepository<RetryEvent, UUID> {
        fun findAllByStatus(status: RetryStatus): Flux<RetryEvent>

//    fun findAllByStatusAndNextRunTimeLessThanEqual(
//        status: RetryStatus,
//        nextRunTime : LocalDateTime
//    ): Flux<RetryEvent>
}