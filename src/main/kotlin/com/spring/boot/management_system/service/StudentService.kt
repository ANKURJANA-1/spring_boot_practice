package com.spring.boot.management_system.service

import com.spring.boot.management_system.model.Student
import com.spring.boot.management_system.repository.StudentRepository
import com.spring.boot.management_system.utils.StudentServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class StudentService : StudentServices, UserDetailsService {

    @Autowired
    private lateinit var studentRepository: StudentRepository


    //get all student list
    override fun getAllStudents(): List<Student> {
        return try {
            studentRepository.findAll()
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    // get entity list by firstname
    override fun findByName(name: String): List<Student> {
        if (studentRepository.findByName(name).isEmpty) {
            throw RuntimeException("Student $name doe's not exists")
        }
        return try {
            studentRepository.findByName(name).get()
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }


    // get entity by id
    override fun findById(id: String): Student {
        if (!studentRepository.findById(id.toLong()).isPresent) {
            throw RuntimeException("Student doe's not exists")
        }
        return try {
            studentRepository.findById(id.toLong()).get()
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    // get entity by email id
    override fun findByEmail(email: String): Student {
        if (!studentRepository.findByEmail(email).isPresent) {
            throw RuntimeException("Student doe's not exists")
        }
        return try {
            studentRepository.findByEmail(email).get()
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }


    // add entity
    override fun addStudent(student: Student): Student {
        if (student.email.isEmpty() ||
            student.firstName.isEmpty() ||
            student.email.isEmpty() ||
            student.mobile.isEmpty() ||
            student.stream.isEmpty()
        ) {
            throw RuntimeException("Please fill the mandatory field ")
        }

        return try {
            studentRepository.save(student)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    override fun loadUserByUsername(email: String?): UserDetails {
        if (email.isNullOrBlank()) {
            throw RuntimeException("Please provide username")
        }
        return try {
            val student = findByEmail(email)
            User(
                student.firstName,
                student.mobile,
                ArrayList<GrantedAuthority>()
            )
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

}