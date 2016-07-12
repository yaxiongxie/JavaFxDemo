package application;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisTemplate;

public class OperateService {
	
	public void saveMac(String mac,int serviceid) {
		RedisTemplate<Serializable, Serializable> redisTemplate=null;
		if(serviceid==1){
			redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
		}else{
			redisTemplate=application.RedisTemplate.getTestRedisTemplate();
		}
		String id = "DeviceModel"+mac;
		if(redisTemplate.opsForHash().get(id, "id")!=null){
			return ;
		}
		redisTemplate.opsForHash().put(id, "id", id);
		redisTemplate.opsForHash().put(id, "devicemac", mac);
		redisTemplate.opsForHash().put(id, "devicepwd", "nopassword");
		redisTemplate.opsForHash().put(id, "devicename", "");
		redisTemplate.opsForHash().put(id, "devicehardware", "");
		redisTemplate.opsForHash().put(id, "devicesoftware", "");
		redisTemplate.opsForHash().put(id, "tzone", "");
		redisTemplate.opsForHash().put(id, "devicestatus", "0");
		redisTemplate.opsForHash().put(id, "brmode", "0");
		redisTemplate.opsForHash().put(id, "light", "0");
		redisTemplate.opsForHash().put(id, "factoryFlag", "");
	}

}
