package com.example.mongodbperformancetest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongodbPerformanceTestApplication

fun main(args: Array<String>) {
    runApplication<MongodbPerformanceTestApplication>(*args)
}
