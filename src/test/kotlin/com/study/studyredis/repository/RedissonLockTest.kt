package com.study.studyredis.repository

import com.github.javafaker.Faker
import com.study.studyredis.domain.Student
import com.study.studyredis.repository.suite.RedisTestSuite
import kotlinx.coroutines.delay
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.redisson.api.RMap
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.testcontainers.shaded.org.bouncycastle.asn1.x500.style.RFC4519Style.name
import org.testcontainers.shaded.org.bouncycastle.asn1.x500.style.RFC4519Style.st
import java.lang.Thread.sleep
import java.util.UUID
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.log

@RedisTestSuite
internal class RedissonLockTest {

    @Autowired
    private lateinit var sut: StudentRedisDao

    @Autowired
    private lateinit var client: RedissonClient

    private lateinit var ids: MutableList<UUID>

    @BeforeEach
    fun setup() {
        ids = mutableListOf()
        val rmap = client.getMap<UUID, Student>("student:")
        repeat(3) {
            with(randomStudent()){
//                sut.save(this)
                rmap[this.id] = this
            }
        }
    }

    // a이름으로 lock 걸어보기
    @Test
    fun `같은 key로 접근시 lock을 걸어 순차적으로 처리합니다`() {

        val service: ExecutorService = Executors.newFixedThreadPool(2)

        for (i in 0 until 2) {
            service.execute {
                val lock = sut.getLock(ids.first())
                if (lock.tryLock(200, TimeUnit.MILLISECONDS)) {
                    try {
                        println("lock 획득을 성공하였습니다. / $i ${Thread.currentThread().name}")
                        println("무언가를 저장함")
                        sleep(200)
                    } finally {
                        lock.unlock()
                    }
                } else {
                    println("lock 획득을 실패하였습니다. / $i ${Thread.currentThread().name}")
                }
            }
        }

        sleep(1000)
    }

    @Test
    fun `스튜던트 저장된 map을 저장합니다`(){
        val map: RMap<UUID, Student> = client.getMap<UUID, Student>("student:")
        println(map.size)
        val student = map[ids.first()]
        println(student)
        map[ids.first()] = student?.apply {
            println(age)
            this.age++
            println(age)
        }
        println(student)
        val student2 = map[ids.first()]
        println(student2)
    }


    private fun randomStudent() = with(Faker()) {
        Student(
            id = UUID.randomUUID().also { ids.add(it) },
            name = name().name(),
            age = number().numberBetween(0, 100),
            gender = Student.Gender.values().random(),
            grade = number().numberBetween(0, 100)
        )

    }

}