package com.example.mongodbperformancetest

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import kotlin.random.Random

@Document
class Entity {
    @Id
    var id: String? = null
    var message: String = "hello world"
    var byte: ByteArray = Random.Default.nextBytes(1024)
}