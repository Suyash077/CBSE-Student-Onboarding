package com.student.services.impl

import com.student.dto.StudentAddedEvent
import com.student.entities.Student
import com.student.repositories.StudentRepository
import com.student.services.kafka.StudentKafkaProducer
import com.student.services.StudentService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class StudentServiceImpl(
    private val studentRepository: StudentRepository,
    private val studentKafkaProducer: StudentKafkaProducer
) : StudentService{

    override fun create(student: Student): Mono<Student> {
        return studentRepository.save(student)
            .doOnSuccess {
                val event = StudentAddedEvent(rollNo = it.rollNo, name = it.name, level = it.level, schoolName = it.schoolName)
                studentKafkaProducer.publishStudentAddedEvent(event)
            }
    }

    override fun getAll(): Flux<Student> {
        return studentRepository.findAll();
    }

    override fun getById(studentId: Int): Mono<Student> {
        return studentRepository.findById(studentId)
    }

    override fun getAllActiveStudents(): Flux<Student> {
        return studentRepository.findAllByIsActiveTrue()
    }

    override fun update(student: Student, studentId: Int): Mono<Student> {
        return studentRepository.findById(studentId)
            .flatMap {
                val updatedStudent =
                    it.copy(name = student.name, level = student.level, schoolName = student.schoolName, isActive = student.isActive)
                studentRepository.save(updatedStudent)
            }
    }

    override fun delete(studentId: Int): Mono<Student> {
        return studentRepository.findById(studentId).flatMap { student ->
            if (student.isActive) {
                student.isActive = false
                studentRepository.save(student)
            } else {
                Mono.empty()
            }
        }
    }
}