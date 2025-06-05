package com.cbse.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "RetryService")
data class RetryEvent(
    @Id
    val retryId: UUID,
    val studentRollNo: String,
    val taskType: String,
    val requestMetadata: Any,
    val responseMetadata: Any,
    val createdDate: LocalDateTime,
    val lastRunDate: LocalDateTime,
    val nextRunTime: LocalDateTime,
    val version: Int,
    val status: RetryStatus
)

enum class RetryStatus { OPEN, CLOSED, FAILED }
