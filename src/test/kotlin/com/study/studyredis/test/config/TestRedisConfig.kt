package com.study.studyredis.test.config

import com.study.studyredis.config.RedisProperties
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@TestConfiguration
class TestRedisConfig(
    redisProperties: RedisProperties
) {
    private lateinit var redisServer: RedisServer

    init {
        redisServer = RedisServer(redisProperties.redisPort)
    }

    @PostConstruct
    fun postConstruct() {
        redisServer.start()
    }

    @PreDestroy
    fun preDestroy() {
        redisServer.stop()
    }
}