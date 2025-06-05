package com.student.services

import com.student.entities.Student
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface StudentService {
    fun create(student: Student) : Mono<Student>

    fun getAll(): Flux<Student>

    fun getById(studentId: Int): Mono<Student>

    fun getAllActiveStudents(): Flux<Student>

    fun update(student: Student, studentId: Int) : Mono<Student>

    fun delete(studentId: Int) : Mono<Student>
}