package com.example.system.componet;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//redis工具
@Component
public class RedisOperator {
   
   @Resource(name = "myRedisTemplate")//根据名称注入属性
   private RedisTemplate redisTemplate;

   //key - value 命令
   //实现命令：TTL key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)。
   public long ttl(String key) {
      return redisTemplate.getExpire(key);
   }

   //实现命令：expire 设置过期时间，单位秒
   public void expire(String key, long timeout) {
      redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
   }
   
   //实现命令：INCR key，增加key一次
   public long incr(String key, long delta) {
      return redisTemplate.opsForValue().increment(key, delta);
   }

   //实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key
   public Set<String> keys(String pattern) {
      return redisTemplate.keys(pattern);
   }

   //实现命令：DEL key，删除一个key
   public void del(String key) {
      redisTemplate.delete(key);
   }

   // String（字符串）
   //实现命令：SET key value，设置一个key-value（将字符串值 value关联到 key）
   public void set(String key, Object value) {
      redisTemplate.opsForValue().set(key, value);
   }

   //实现命令：SET key value EX seconds，设置key-value和超时时间（秒）
   public void set(String key, String value, long timeout) {
      redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
   }

   //实现命令：GET key，返回 key所关联的字符串值。
   public String get(String key) {
      return String.valueOf(redisTemplate.opsForValue().get(key));
   }

   // Hash（哈希表）
   // 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
   public void hset(String key, String field, Object value) {
      redisTemplate.opsForHash().put(key, field, value);
   }

   //实现命令：HGET key field，返回哈希表 key中给定域 field的值
   public String hget(String key, String field) {
      return (String) redisTemplate.opsForHash().get(key, field);
   }

   //实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
   public void hdel(String key, Object... fields) {
      redisTemplate.opsForHash().delete(key, fields);
   }

   //实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
   public Map<Object, Object> hgetall(String key) {
      return redisTemplate.opsForHash().entries(key);
   }

   // List（列表）
   //实现命令：LPUSH key value，将一个值 value插入到列表 key的表头
   public long lpush(String key, String value) {
      return redisTemplate.opsForList().leftPush(key, value);
   }

   //实现命令：LPOP key，移除并返回列表 key的头元素。
   public String lpop(String key) {
      return String.valueOf(redisTemplate.opsForList().leftPop(key));
   }

   //实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)。
   public long rpush(String key, Object value) {
      return redisTemplate.opsForList().rightPush(key, value);
   }

   //查询列表中的元素
   public List<Integer> range(String key, int start, int end) {
      return redisTemplate.opsForList().range(key, start, end);
   }

   //实现命令：判断key是否还存在
   public boolean exists(String key) {
      return redisTemplate.hasKey(key);
   }

   public Long index(String key, Long i) {
      return Long.valueOf((String) redisTemplate.opsForList().index(key , i));
   }


}