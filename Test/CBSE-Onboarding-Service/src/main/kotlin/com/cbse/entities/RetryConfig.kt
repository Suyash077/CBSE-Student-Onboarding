package com.cbse.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "RetryConfig")
data class RetryConfig(
    @Id
    val taskType : String,
    val maxRetryCount : Int = 5,
    val retryAfterInMins : Int = 10
)
