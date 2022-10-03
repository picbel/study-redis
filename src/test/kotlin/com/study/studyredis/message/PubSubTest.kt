package com.study.studyredis.message

import com.study.studyredis.StudyRedisApplication
import com.study.studyredis.config.RedisConfig
import com.study.studyredis.config.RedisMessagePublisher
import com.study.studyredis.config.RedisMessageSubscriber
import com.study.studyredis.config.RedisPubSubConfig.*
import com.study.studyredis.test.config.TestRedisConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import java.util.*

@SpringBootTest(classes = [TestRedisConfig::class])
@ComponentScan(basePackages = [
    "com.study.studyredis",
])
class PubSubTest {

    @Autowired
    private lateinit var pub: RedisMessagePublisher

    @Autowired
    private lateinit var sub: RedisMessageSubscriber

    @Test
    fun `pub_sub을 실행한다`() = runBlocking() {
        //given
        val message = "Message : ${UUID.randomUUID()}"
        launch {
            pub.publish(message)
        }
        delay(100)
        //when
        println(sub.comsumeMessage())
        //then

    }
}