package com.study.studyredis.config

import com.study.studyredis.domain.Student
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Service


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
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = Jackson2JsonRedisSerializer(Student::class.java)
        return template
    }



}

@Configuration
class RedisPubSubConfig{
    @Bean
    fun messageListener(): MessageListenerAdapter {
        return MessageListenerAdapter(RedisMessageSubscriber())
    }

    @Bean
    fun redisMessageListenerContainer(connectionFactory: RedisConnectionFactory): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.addMessageListener(messageListener(), topic())
        return container
    }
    @Bean
    fun redisPublisher(redisTemplate: RedisTemplate<*,*>): MessagePublisher? {
        return RedisMessagePublisher(redisTemplate, topic())
    }

    @Bean
    fun topic(): ChannelTopic {
        return ChannelTopic("studentGrade")
    }
    interface MessagePublisher {
        fun publish(message: String?)
    }

    @Service
    class RedisMessagePublisher(
        private val redisTemplate: RedisTemplate<*, *>,
        private val topic: ChannelTopic
    ) : MessagePublisher {


        override fun publish(message: String?) {
            redisTemplate.convertAndSend(topic.topic, message!!)
        }
    }

    @Service
    class RedisMessageSubscriber : MessageListener {
        override fun onMessage(message: Message, pattern: ByteArray?) {
            messageList.add(message.toString())
            println("Message received: $message")
        }

        fun comsumeMessage() = messageList.first().apply {
            messageList.removeFirst()
        }
        companion object {
            val messageList: MutableList<String> = ArrayList()
        }
    }
}