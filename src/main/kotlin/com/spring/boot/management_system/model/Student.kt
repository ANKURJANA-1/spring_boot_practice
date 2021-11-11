package com.spring.boot.management_system.model

import org.hibernate.Hibernate
import javax.persistence.*


@Entity
@Table(name = "students", catalog = "management_system")
data class Student(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id", nullable = false, updatable = false)
    val id: Long,

    @Column(name = "first_name", nullable = false)
    val firstName: String = "",

    @Column(name = "last_name")
    val lastName: String = "",

    @Column(name = "student_email", nullable = false, unique = true)
    val email: String = "",

    @Column(name = "student_mobile", nullable = false, unique = true)
    val mobile: String = "",

    @Column(name = "student_stream", nullable = false)
    val stream: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Student

        return id == other.id
    }

    override fun hashCode(): Int = 0

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , firstName = $firstName , lastName = $lastName , email = $email , mobile = $mobile , stream = $stream )"
    }
}
