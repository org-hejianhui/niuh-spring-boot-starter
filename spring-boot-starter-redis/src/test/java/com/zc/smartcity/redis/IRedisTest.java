package com.zc.smartcity.redis;

import com.zc.smartcity.redis.service.IRedis;
import com.zc.smartcity.redis.spring.SpringJedisStandAloneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IRedisTest {

   @Autowired
   SpringJedisStandAloneService springJedisStandAloneService;

   @Autowired
   IRedis iRedisClusterHA;

   @Test
    public void testKey(){

       new Thread().start();
      for (int i=0;i<1000000000;i++) {
          //iRedis.set("trade", "hejianhui" + i, "20190409" + i);



          springJedisStandAloneService.hset(0,"test", "hejianhui","hejianhui" + i, "20190409" + i);
          System.out.println(springJedisStandAloneService.hget(0,"test","hejianhui","hejianhui" + i));
//         try {
//            Thread.sleep(2000);
//         } catch (InterruptedException e) {
//            e.printStackTrace();
//         }
      }

   }

   @Test
   public void testKey2(){
      System.out.println(iRedisClusterHA.get("trade","hejianhui"));
   }

}
