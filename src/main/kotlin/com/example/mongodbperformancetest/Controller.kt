package com.example.mongodbperformancetest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File
import kotlin.random.Random

@RestController
class Controller @Autowired constructor(
    private val service: Service
) {
    @GetMapping("/insert")
    fun insert(@RequestParam size: Int, @RequestParam byteSize: Int) {
        val entity = mutableListOf<Entity>()

        for ((counter) in (0..size).withIndex()) {
            entity.add(
                Entity(
                    message = "_$counter",
                    byte = Random.Default.nextBytes(byteSize)
                )
            )
        }

        val sb = StringBuilder()
        sb.append("mongo template\n")
        sb.append("current rows: ${service.countAll()}\n")

        val startTime = System.currentTimeMillis()
        service.saveAll(entity)
        val endTime = System.currentTimeMillis()

        sb.append("time to save $size rows with byte size $byteSize: ${endTime - startTime} millis\n")
        sb.append("current rows after insert: ${service.countAll()}\n")

        File("write-${System.currentTimeMillis()}.txt").printWriter().use { out ->
            out.println(sb.toString())
        }
    }

    @GetMapping("/fetch")
    fun fetchAll() {
        val sb = StringBuilder()
        sb.append("mongo template\n")

        sb.append("current rows: ${service.countAll()}\n")

        val startTime = System.currentTimeMillis()
        service.findAll()
        val endTime = System.currentTimeMillis()

        sb.append("time to fetch all rows: ${endTime - startTime} millis")

        File("read-all-${System.currentTimeMillis()}.txt").printWriter().use { out ->
            out.println(sb.toString())
        }
    }

    @GetMapping("/count")
    fun countAll() {
        val sb = StringBuilder()
        sb.append("mongo template\n")

        sb.append("current rows: ${service.countAll()}\n")

        val startTime = System.currentTimeMillis()
        service.countAll()
        val endTime = System.currentTimeMillis()

        sb.append("time to count all rows: ${endTime - startTime} millis")

        File("count-all-${System.currentTimeMillis()}.txt").printWriter().use { out ->
            out.println(sb.toString())
        }
    }

    @GetMapping("/find")
    fun find() {
        val sb = StringBuilder()
        sb.append("mongo template\n")

        sb.append("current rows: ${service.countAll()}\n")

        val randomValues = List(2000) { Random.nextInt(0, 200000).toString() }

        val startTime = System.currentTimeMillis()

        runBlocking {
            processAllPages(randomValues)
        }

        val endTime = System.currentTimeMillis()

        sb.append("time to fetch by message: ${endTime - startTime} millis")

        File("fetch-${System.currentTimeMillis()}.txt").printWriter().use { out ->
            out.println(sb.toString())
        }
    }

    suspend fun processAllPages(randomValues: List<String>) = withContext(Dispatchers.IO) {
        // withContext waits for all children coroutines
        randomValues.forEach {
            launch {
                service.findByMessageLike(it)
            }
        }
    }

    @GetMapping("/delete")
    fun deleteAll() {
        val sb = StringBuilder()
        sb.append("mongo template\n")

        sb.append("current rows: ${service.countAll()}\n")

        val startTime = System.currentTimeMillis()
        service.deleteAll()
        val endTime = System.currentTimeMillis()

        sb.append("time to delete all: ${endTime - startTime} millis")

        File("delete-${System.currentTimeMillis()}.txt").printWriter().use { out ->
            out.println(sb.toString())
        }
    }
}