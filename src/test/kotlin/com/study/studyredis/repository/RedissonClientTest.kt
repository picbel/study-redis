package com.study.studyredis.repository

import com.study.studyredis.repository.suite.RedisTestSuite
import org.springframework.beans.factory.annotation.Autowired

internal class RedissonClientTest : RedisTestSuite() {
    @Autowired
    private lateinit var sut: StudentRedisDao

    // a이름으로 lock 걸어보기

    // a와 b로 락 걸어보기


}