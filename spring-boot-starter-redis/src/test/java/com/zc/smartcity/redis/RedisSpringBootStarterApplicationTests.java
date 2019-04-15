package com.zc.smartcity.redis;

import com.zc.smartcity.redis.spring.SpringJedisClusterService;
import com.zc.smartcity.redis.configure.SpringJedisProperties;
import com.zc.smartcity.redis.spring.SpringJedisStandAloneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisSpringBootStarterApplicationTests {

    @Autowired
    private SpringJedisProperties springJedisProperties;

    /**
     * Redis集群服务
     */
    @Autowired
    SpringJedisClusterService clusterService;

    /**
     * Redis单机服务
     */
    @Autowired
    SpringJedisStandAloneService standAloneService;


    @Test
    public void contextLoads() {

        //单机redis
        new Thread(() -> System.out.println(standAloneService.set(0, "1","test1", "123"))).start();
        new Thread(() -> System.out.println(standAloneService.exists(0, "1","test2"))).start();
        //集群redis
        new Thread(() -> System.out.println(clusterService.exists("test"))).start();

    }

}

