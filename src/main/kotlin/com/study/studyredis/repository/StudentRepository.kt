package com.study.studyredis.repository

import com.study.studyredis.domain.Student
import org.springframework.context.annotation.Primary
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


interface StudentRedisDao : CrudRepository<Student, UUID>

