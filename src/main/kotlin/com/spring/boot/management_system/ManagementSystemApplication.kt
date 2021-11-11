package com.spring.boot.management_system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ManagementSystemApplication

fun main(args: Array<String>) {
	runApplication<ManagementSystemApplication>(*args)
}
