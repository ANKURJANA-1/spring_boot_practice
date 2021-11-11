package com.spring.boot.management_system.controller

import com.spring.boot.management_system.model.Student
import com.spring.boot.management_system.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("")
class StudentController {

    @Autowired
    private lateinit var studentService: StudentService


    @GetMapping("student/all")
    fun getAllStudent(): List<Student> {
        return try {
            studentService.getAllStudents()
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    @PostMapping("student")
    fun addStudent(@RequestBody student: Student): Student {
        return try {
            studentService.addStudent(student)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }


    @GetMapping("student/{firstName}")
    fun findStudentByName(@PathVariable firstName: String): List<Student> {
        return try {
            studentService.findByName(firstName.capitalize())
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

}