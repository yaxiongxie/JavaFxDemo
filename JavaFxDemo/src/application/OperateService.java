package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.springframework.data.redis.core.RedisTemplate;

public class OperateService {
	
	public static void saveMac(String mac,int serviceid) {
		RedisTemplate<Serializable, Serializable> redisTemplate=null;
		ConnectionConfiguration config = null;
		if(serviceid==1){
			redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
			config=new ConnectionConfiguration("172.17.5.24",5222);  
		}else{
			redisTemplate=application.RedisTemplate.getTestRedisTemplate();
			config=new ConnectionConfiguration("172.17.5.24",5222);  
		}
		String id = "DeviceModel"+mac;
		if(redisTemplate.opsForHash().get(id, "id")!=null){
			
		}else{
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
		
		try{
			Connection connection = new XMPPConnection(config);
			connection.connect();
			AccountManager amgr = new AccountManager(connection);
			amgr.createAccount(mac, mac);
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	public static  String getMainBind(String mac,int serviceid){
		RedisTemplate<Serializable, Serializable> redisTemplate=null;
		if(serviceid==1){
			redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
		}else{
			redisTemplate=application.RedisTemplate.getTestRedisTemplate();
		}
		Serializable mainUserid=redisTemplate.opsForValue().get("mainbind"+mac);
		if(mainUserid==null){
			return "无";
		}
		return mainUserid.toString();
	}
	
	public static Set<Serializable> getSubBind(String mac,int serviceid){
		RedisTemplate<Serializable, Serializable> redisTemplate=null;
		if(serviceid==1){
			redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
		}else{
			redisTemplate=application.RedisTemplate.getTestRedisTemplate();
		}
		Set<Serializable> set=redisTemplate.opsForSet().members("bind"+mac);
		return set;
	}
	
	public static List<String> getKitSubNodes(String mac,int serviceid){
		RedisTemplate<Serializable, Serializable> redisTemplate=null;
		if(serviceid==1){
			redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
		}else{
			redisTemplate=application.RedisTemplate.getTestRedisTemplate();
		}
		Object shorts=redisTemplate.opsForHash().get("zigbee_mac_shorts", mac);//获取所有短地址
		List<String> resultList=new ArrayList<String>();
		if(shorts==null){
			return resultList;
		}
		String[] ssArr=shorts.toString().split(",");
		for(String s:ssArr){
			if(s.equals("")){
				continue;
			}
			Object longIp=redisTemplate.opsForHash().get("zigbee_mac_short_long", mac+s);//TODO 2//获取长地址
			Object moduleName=redisTemplate.opsForHash().get("zigbee_long_modulename", longIp.toString());
			resultList.add(moduleName.toString()+","+s+","+longIp.toString());
		}
		return resultList;
	}
	
	public static List<String> getMcHistoryList(String mac,int serviceid){
		//00158d0000d52c13
		RedisTemplate<Serializable, Serializable> redisTemplate=null;
		if(serviceid==1){
			redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
		}else{
			redisTemplate=application.RedisTemplate.getTestRedisTemplate();
		}
		List<Serializable> list=redisTemplate.opsForList().range("McModel"+mac,0, 50);
		List<String> resultList=new ArrayList<String>();
	    for(Serializable serializable:list){
	    	String[] sArr=serializable.toString().split("%");
	    	String status=sArr[0];
	    	String time=sArr[1].replace(" ", "-").replace(":", "-");
	    	resultList.add(time+","+status);
	    }
	    return resultList;
	}
	
	public static List<String> getRtHistoryList(String mac,int serviceid){
		//00158d0000d52c13
		RedisTemplate<Serializable, Serializable> redisTemplate=null;
		if(serviceid==1){
			redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
		}else{
			redisTemplate=application.RedisTemplate.getTestRedisTemplate();
		}
		List<Serializable> list=redisTemplate.opsForList().range("RtNewModel"+mac,0, 50);
		List<String> resultList=new ArrayList<String>();
	    for(Serializable serializable:list){
	    	String[] sArr=serializable.toString().split("%");
	    	String status=sArr[0];
	    	String time=sArr[1].replace(" ", "-").replace(":", "-");
	    	resultList.add(time+","+status);
	    }
	    return resultList;
	}

}
