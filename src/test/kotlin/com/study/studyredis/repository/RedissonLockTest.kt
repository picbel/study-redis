package com.study.studyredis.repository

import com.github.javafaker.Faker
import com.study.studyredis.domain.Student
import com.study.studyredis.repository.suite.RedisTestSuite
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.testcontainers.shaded.org.bouncycastle.asn1.x500.style.RFC4519Style.name
import java.util.UUID
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.log

internal class RedissonLockTest : RedisTestSuite() {


    @Qualifier("studentRedisDaoImpl")
    @Autowired
    private lateinit var sut: StudentRedisDao

    private lateinit var sut2: StudentRedisCrudDao

    private lateinit var ids: MutableList<UUID>

    @BeforeEach
    fun setup() {
        ids = mutableListOf()

        repeat(3) {
            sut2.save(randomStudent())
        }
    }

    // a이름으로 lock 걸어보기
    @Test
    fun `같은 key로 접근시 lock을 걸어 순차적으로 처리합니다`() {

        val service: ExecutorService = Executors.newFixedThreadPool(2)

        for (i in 0 until 2) {
            service.execute {
                val lock = sut.getLock(ids.first())
                try {
                    if (lock.tryLock(200, TimeUnit.MILLISECONDS)) {
                        println("lock 획득을 성공하였습니다. / $i ${Thread.currentThread().name}")
                    } else {
                        println("lock 획득을 실패하였습니다. / $i ${Thread.currentThread().name}")
                    }
                }finally {
                    lock.unlock()
                }

            }
        }


    }



    // a와 b로 락 걸어보기

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