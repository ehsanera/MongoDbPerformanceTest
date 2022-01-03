package com.example.mongodbperformancetest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller @Autowired constructor(
    private val service: Service
) {
    @GetMapping("/insert")
    fun insert() {
        service.saveAll(List(1000) { Entity() })
    }

    @GetMapping("/count")
    fun count(): Long {
        return service.getAll()
    }
}