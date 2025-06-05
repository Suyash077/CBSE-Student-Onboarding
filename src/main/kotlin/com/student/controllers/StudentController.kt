package com.student.controllers

import com.student.entities.Student
import com.student.services.StudentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/students")
@Validated
class StudentController(
    private val studentService: StudentService,
) {

    @PostMapping
    fun create(@Valid @RequestBody student: Student): Mono<ResponseEntity<Student>> {
        return studentService.create(student)
            .map { ResponseEntity(it, HttpStatus.CREATED) }
            .onErrorReturn(ResponseEntity.badRequest().build())
    }

    @GetMapping("/allstudent")
    fun getAll() : Flux<Student> = studentService.getAll()

    @GetMapping
    fun getAllActiveStudents(): Flux<Student> = studentService.getAllActiveStudents()

    @GetMapping("/{studentId}")
    fun getById(@PathVariable studentId : Int) : Mono<Student> = studentService.getById(studentId)

    @PostMapping("/update/{studentId}")
    fun update(@Valid @RequestBody student: Student, @PathVariable studentId: Int): Mono<ResponseEntity<Student>> {
        return studentService.update(student, studentId)
            .map { ResponseEntity.ok(it) }
            .onErrorReturn(ResponseEntity.badRequest().build())
    }

    @PostMapping("/delete/{studentId}")
    fun delete(@PathVariable studentId: Int) : Mono<Student> = studentService.delete(studentId)
}