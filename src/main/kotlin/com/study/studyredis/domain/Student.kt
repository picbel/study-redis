package com.study.studyredis.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.UUID


@RedisHash("student")
data class Student(
    @Id
    val id: UUID,

    val name: String,

    var age: Int,

    val gender : Student.Gender,

    var grade: Int

) : java.io.Serializable{
    enum class Gender{
        MALE, FEMALE
    }
}