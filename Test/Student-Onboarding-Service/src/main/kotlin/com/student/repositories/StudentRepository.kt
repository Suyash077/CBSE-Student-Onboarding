package com.student.repositories

import com.student.entities.Student
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface StudentRepository : ReactiveMongoRepository<Student,Int> {
    fun findAllByIsActiveTrue(): Flux<Student>
}