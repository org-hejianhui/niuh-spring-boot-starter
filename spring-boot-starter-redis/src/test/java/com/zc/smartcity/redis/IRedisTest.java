package com.zc.smartcity.redis;

import com.zc.smartcity.redis.service.IRedis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IRedisTest {

   @Autowired
   IRedis iRedis;

   @Autowired
   IRedis iRedisClusterHA;

   @Test
    public void testKey(){
      for (int i=0;i<100;i++) {
          iRedisClusterHA.set("trade", "hejianhui" + i, "20190409" + i);
         System.out.println(iRedis.get("trade","hejianhui"+i));
         try {
            Thread.sleep(2000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      try {
         Thread.sleep(Long.MAX_VALUE);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void testKey2(){
      System.out.println(iRedisClusterHA.get("trade","hejianhui"));
   }

}
