package com.example.mongodbperformancetest

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EntityRepository : MongoRepository<Entity, String>