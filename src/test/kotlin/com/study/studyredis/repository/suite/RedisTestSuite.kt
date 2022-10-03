package com.study.studyredis.repository.suite

import com.study.studyredis.config.RedisConfig
import com.study.studyredis.config.RedisProperties
import com.study.studyredis.config.RedisPubSubConfig
import com.study.studyredis.repository.StudentRedisCrudDao
import com.study.studyredis.repository.StudentRedisDao
import com.study.studyredis.repository.StudentRedisDaoImpl
import com.study.studyredis.test.config.TestRedisConfig
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration


@DataRedisTest
@EnableAutoConfiguration
@AutoConfigureDataRedis
@ComponentScan(basePackages = [
    "com.study.studyredis.repository.*",
])
@ContextConfiguration(
    classes = [
        RedisProperties::class,
        RedisConfig::class,
        TestRedisConfig::class,
        StudentRedisCrudDao::class,
        StudentRedisDao::class,
        StudentRedisDaoImpl::class
    ]
)
internal abstract class RedisTestSuite
