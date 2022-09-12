package com.study.studyredis.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
class RedisProperties(
    @Value("\${spring.redis.port}")
    val redisPort: Int,
    @Value("\${spring.redis.host}")
    val redisHost: String
)

@Configuration
@EnableRedisRepositories
class RedisConfig {
    @Bean
    fun redisConnectionFactory(
        redisProperties: RedisProperties
    ): LettuceConnectionFactory {
        return LettuceConnectionFactory(
            redisProperties.redisHost,
            redisProperties.redisPort
        )
    }

    @Bean
    fun redisTemplate(connectionFactory: LettuceConnectionFactory): RedisTemplate<*, *> {
        val template = RedisTemplate<ByteArray, ByteArray>()
        template.setConnectionFactory(connectionFactory)
        return template
    }
}