package com.study.studyredis.repository

import com.study.studyredis.domain.Student
import org.redisson.api.RedissonClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.locks.Lock


@Repository
interface StudentRedisCrudDao : CrudRepository<Student, UUID>

interface StudentRedisDao : StudentRedisCrudDao {
    fun getLock(key: UUID): Lock
}

@Repository @Primary
class StudentRedisDaoImpl(
    @Qualifier("studentRedisCrudDao")
    private val crudDao: StudentRedisCrudDao,
    private val redissonClient: RedissonClient
) : StudentRedisDao, StudentRedisCrudDao by crudDao {
    override fun getLock(key: UUID): Lock {
        return redissonClient.getLock(key.toString())
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
