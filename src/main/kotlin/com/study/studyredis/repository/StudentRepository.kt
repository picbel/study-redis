package com.study.studyredis.repository

import com.study.studyredis.domain.Student
import org.redisson.api.RedissonClient
import org.springframework.context.annotation.Primary
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.locks.Lock
import kotlin.concurrent.withLock


interface StudentRedisDao : CrudRepository<Student, UUID> {
    fun getLock(key : UUID) : Lock
}

@Primary
@Repository
class StudentRedisDaoImpl(
    private val dao: StudentRedisDao,
    private val redissonClient: RedissonClient
) : StudentRedisDao by dao {
    override fun getLock(key: UUID): Lock {
        return redissonClient.getLock("A")
    }
}
//
//interface StudentRepository
//
//@Repository
//class StudentRepositoryImpl(
//    val redisDao: StudentRedisDao
//) {
//
//}
