package com.example.mongodbperformancetest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Service @Autowired constructor(
    private val entityRepository: EntityRepository
) {
    fun saveAll(listEntity: List<Entity>) {
        entityRepository.saveAll(listEntity)
    }

    fun getAll(): Long {
        return entityRepository.count()
    }
}