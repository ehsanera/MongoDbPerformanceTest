package com.example.mongodbperformancetest

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Entity(
    @Id
    var id: String? = null,
    val message: String,
    val byte: ByteArray,
)