package com.xj.test;

import com.xj.dynamicthreadpoolspringbootstarter.domain.model.entity.ThreadPoolConfigEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author : xiongjun
 * @date : 2024/5/23 17:03
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {


    @Autowired
    private RTopic dynamicThreadPoolTopic;

    @Test
    public void test_dynamicThreadPoolTopic() throws InterruptedException {
        ThreadPoolConfigEntity threadPoolConfigEntity = new ThreadPoolConfigEntity("dynamic-thread-pool-test", "threadPoolExecutor01");
        threadPoolConfigEntity.setCorePoolSize(100);
        threadPoolConfigEntity.setMaximumPoolSize(100);
        dynamicThreadPoolTopic.publish(threadPoolConfigEntity);

        new CountDownLatch(1).await();
    }



}
