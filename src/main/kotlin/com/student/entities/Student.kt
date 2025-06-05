package com.student.entities

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "StudentDetails")
data class Student(
    @Id
    val rollNo : String,

    @field: NotEmpty(message = "Student name is required")
    @field: Size(min = 3, message = "StudentName must be minimum of 3 characters")
    val name : String,

    @field:Min(value = 1, message = "Class level must be between 1 and 12")
    @field:Max(value = 12, message = "Class level must be between 1 and 12")
    val level : Int,

    val schoolName : String = "Delhi public school",

    var isActive: Boolean
)
