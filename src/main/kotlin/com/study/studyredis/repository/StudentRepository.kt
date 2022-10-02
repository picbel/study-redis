package com.study.studyredis.repository

import com.study.studyredis.domain.Student
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


interface StudentRedisDao : CrudRepository<Student, UUID>

interface StudentRepository

@Repository
class StudentRepositoryImpl(
    val redisDao : StudentRedisDao
){

}
