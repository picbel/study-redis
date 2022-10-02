package com.study.studyredis.repository

import com.study.studyredis.config.RedisConfig
import com.study.studyredis.config.RedisProperties
import com.study.studyredis.domain.Student
import com.study.studyredis.repository.suite.RedisTestSuite
import com.study.studyredis.test.config.TestRedisConfig
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import java.util.*



internal class StudentRepositoryImplTest : RedisTestSuite(){

    @Autowired
    private lateinit var sut: StudentRedisDao

    @Test
    fun `redis에 학생정보를 저장합니다`() {
        //given
        val studentId = UUID.randomUUID()
        val student = Student(
            id = studentId,
            name = "jone doe",
            age = 27,
            gender = Student.Gender.MALE,
            grade = 100
        )

        //when
        sut.save(student)

        //then
        val find = sut.findById(studentId).orElseThrow()
        assertThat(student,`is`(find))
    }

    @Test
    fun `redis에 학생정보를 수정합니다`() {
        //given
        val studentId = UUID.randomUUID()
        val student = Student(
            id = studentId,
            name = "jone doe",
            age = 27,
            gender = Student.Gender.MALE,
            grade = 100
        )

        //when
        sut.save(student)
        student.apply {
            age = 28
        }
        sut.save(student)

        //then
        val find = sut.findById(studentId).orElseThrow()
        assertThat(find.age,`is`(28))
    }



}