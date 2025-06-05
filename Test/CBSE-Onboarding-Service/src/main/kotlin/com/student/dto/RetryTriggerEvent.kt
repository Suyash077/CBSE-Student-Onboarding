package com.student.dto

import java.util.*

data class RetryTriggerEvent(
    val retryId: UUID,
    val taskType: String
)
