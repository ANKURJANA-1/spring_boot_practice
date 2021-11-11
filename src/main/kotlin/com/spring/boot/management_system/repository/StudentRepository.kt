package com.spring.boot.management_system.repository

import com.spring.boot.management_system.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface StudentRepository : JpaRepository<Student, Long> {


    @Query(
        value = "SELECT *FROM students WHERE first_name=:name",
        nativeQuery = true
    )
    fun findByName(name: String): Optional<List<Student>>


    @Query(
        value = "SELECT *FROM students WHERE student_email=:email",
        nativeQuery = true
    )
    fun findByEmail(email: String): Optional<Student>
}