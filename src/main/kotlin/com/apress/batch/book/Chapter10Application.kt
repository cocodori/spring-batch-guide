package com.apress.batch.book

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class Chapter10Application

fun main(args: Array<String>) {
    runApplication<Chapter10Application>(*args)
}
