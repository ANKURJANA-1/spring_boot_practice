package com.spring.boot.management_system.utils

import com.spring.boot.management_system.model.Student

interface StudentServices {

    fun getAllStudents(): List<Student>

    fun findByName(name: String): List<Student>

    fun findById(id: String): Student

    fun findByEmail(email: String): Student

    fun addStudent(student: Student): Student

}