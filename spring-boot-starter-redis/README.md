# spring-boot-starter-redis
基于Jedis的自定义redis-starter组件，使得项目使用Redis变得异常简单，支持对单机Redis和Redis Cluster，支持spring boot，和spirng mvc等spring相关项目 [https://github.com/org-hejianhui/spring-boot-starter-redis](https://github.com/org-hejianhui/spring-boot-starter-redis)

# 特性

1、	基于Jedis Cluster开发的客户端支持Redis Cluster集群

2、	对被调用方（客户端）侵入极少，上手极快

3、	支持动态增加节点，客户端自动感知（zk）

4、	支持客户端验证与拦截 (token)

5、	异步监控调用数据，支持异步上报

6、	方便管理有效的区分业务系统。例如：会员（memmber） 商品（goods）

7、	支持Falcon协议. 监控系统


# 快速开始

> spring boot项目接入


 1.添加redis starter组件依赖，~~目前还没上传到公共仓库，需要自己下源码build~~ 直接依赖下面即可使用
```
<dependency>
  <groupId>com.github.org-hejianhui</groupId>
  <artifactId>spring-boot-starter-redis</artifactId>
  <version>1.0-RELEASE</version>
</dependency>

```

2.application.yml配置redis

```java
spring:
  jedis:
    # 业务标记
    business: 1
    # 公共配置选项
    maxWaitMillis: 30000
    minIdle: 100
    #auth: redis1234
    timeout: 30000
    testOnBorrow: true
    maxTotal: 500
    #在单机环境下可以不配置，按照规范大家配置一下
    host: 192.168.1.250
    port: 6379
    stand:
      alone:
        enabled: false
    # 在集群环境下必须配置，按照规范大家配置一下
    nodes: 192.168.1.221:7000,192.168.1.221:7001,192.168.1.221:7002,192.168.1.221:7003,192.168.1.221:7004,192.168.1.221:7005
    cluster:
      enabled: false
    # 在集群环境下通过zk可动态扩容必须配置，按照规范大家配置一下
    haCluster:
      enabled: true
    zkHostPort: 192.168.1.221:2181
    # 监控配置，按照规范大家配置一下
    monitor:
      enabled: true
      url: http://127.0.0.1:8080/springboot/monitor
      protocol: protocol 
```

3.API 接口分为3种情况使用，第二种与第三种方式不可同时使用，二选其一，如：

**单机，Redis API**
```java
@Autowired
private IRedis iRedis;
```

**集群，Redis API**
```java
@Autowired
private IRedis iRedisCluster;
```

**集群可扩容，Redis API**
```java
@Autowired
private IRedis iRedisClusterHA;
```

单元测试
-------------------------

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


# 数据监控
<p align="center">
    <img src="https://github.com/org-hejianhui/spring-boot-starter-redis/blob/master/src/test/resources/monitor.png?raw=true" width="800" />
</p>


# 关于测试
工程test模块下，为Redis 客户端的测试模块。可以快速体验客户端API使用的效果。

# 使用登记
如果这个项目解决了你的实际问题，可在[https://github.com/org-hejianhui/spring-boot-starter-redis/issues/](https://github.com/org-hejianhui/spring-boot-starter-redis/issues/)登记下，如果节省了你的研发时间，也愿意支持下的话，可点击下方【捐助】请作者喝杯咖啡，也是非常感谢！

<p align="center">
    <img src="https://github.com/org-hejianhui/spring-boot-starter-redis/blob/master/src/test/resources/mycat.JPG?raw=true" width="200" />
</p>
