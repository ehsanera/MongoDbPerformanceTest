package com.example.mongodbperformancetest

import com.mongodb.client.MongoClients
import com.mongodb.client.result.DeleteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service


@Service
class Service @Autowired constructor(
//    private val entityRepository: EntityRepository
) {
    val entityRepository: MongoOperations =
        MongoTemplate(
            SimpleMongoClientDatabaseFactory(
                MongoClients.create("mongodb://admin_user:admin_pass@31.7.64.35:27017,31.7.64.38:27017,31.7.64.40:27017/application_database?replicaSet=rs1&authSource=admin"),
                "performance"
            )
        )

    fun saveAll(listEntity: List<Entity>): MutableCollection<Entity> {
        return entityRepository.insertAll(listEntity)
    }

    fun countAll(): Long {
        return entityRepository.count(Query(), Entity::class.java)
    }

    fun findByMessageLike(message: String): MutableList<Entity> {

        return entityRepository.find(
            Query().addCriteria(
                Criteria.where("message").regex(message)
            ).limit(1),
            Entity::class.java
        )
    }

    fun findAll(): MutableList<Entity> {
        val query = Query()
        query.addCriteria(!Criteria.where("message").isNull)
        return entityRepository.find(query, Entity::class.java)
    }


    fun deleteAll(): DeleteResult {
        return entityRepository.remove(Query(), "entity")
    }
}