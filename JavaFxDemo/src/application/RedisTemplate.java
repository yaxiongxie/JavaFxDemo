package application;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedisTemplate {
	
	public static void main(String[] args) {
		org.springframework.data.redis.core.RedisTemplate<Serializable, Serializable> redisTemplate=getTestRedisTemplate();
		System.out.println(redisTemplate.opsForHash().get("devicename","28-d9-8a-90-6e-23_80c31e896810431663"));
//		redisTemplate.opsForHash().put("test", "xx", "yy");
		
	}
	
	
	public static org.springframework.data.redis.core.RedisTemplate<Serializable, Serializable> getTestRedisTemplate(){
		ApplicationContext app=new ClassPathXmlApplicationContext("applicationContext.xml");
		org.springframework.data.redis.core.RedisTemplate<Serializable, Serializable> redisTemplate=(org.springframework.data.redis.core.RedisTemplate<Serializable, Serializable>)app.getBean("redisTemplate");
		return redisTemplate;
	}
	
	public static org.springframework.data.redis.core.RedisTemplate<Serializable, Serializable> getFormalRedisTemplate(){
		ApplicationContext app=new ClassPathXmlApplicationContext("applicationContext2.xml");
		org.springframework.data.redis.core.RedisTemplate<Serializable, Serializable> redisTemplate=(org.springframework.data.redis.core.RedisTemplate<Serializable, Serializable>)app.getBean("redisTemplate");
		return redisTemplate;
	}

}
