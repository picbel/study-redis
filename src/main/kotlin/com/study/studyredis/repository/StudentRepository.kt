package com.study.studyredis.repository

import com.study.studyredis.domain.Student
import org.redisson.api.RedissonClient
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.locks.Lock


interface StudentRedisCrudDao : CrudRepository<Student, UUID>
interface StudentRedisDao {
    fun getLock(key: UUID): Lock
}

@Repository
class StudentRedisDaoImpl(
//    private val redissonClient: RedissonClient
) : StudentRedisDao {
    override fun getLock(key: UUID): Lock {
//        return redissonClient.getLock(key.toString())
        TODO()
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
