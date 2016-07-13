package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
			config=new ConnectionConfiguration("172.17.12.116",5222);  
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
			resultList.add(replaceModuleName(moduleName.toString())+","+s+","+longIp.toString());
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
	
	public static List<String> findByMac(String mac,int serviceid) {
			RedisTemplate<Serializable, Serializable> redisTemplate=null;
			if(serviceid==1){
				redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
			}else{
				redisTemplate=application.RedisTemplate.getTestRedisTemplate();
			}
			String id = "DeviceModel"+mac;
			List<String> resultList=new ArrayList<>();
		String existingRecord=(String)redisTemplate.opsForHash().get(id, "devicemac");
		if(existingRecord==null){
			return resultList;
		}
		resultList.add("开关状态:"+getStatus(redisTemplate.opsForHash().get(id, "devicestatus")));
		resultList.add("小夜灯状态:"+getStatus(redisTemplate.opsForHash().get(id, "light")));
		resultList.add("充电保护状态:"+getStatus(redisTemplate.opsForHash().get(id, "brmode")));
		resultList.add("设备名称:"+redisTemplate.opsForHash().get(id, "devicename"));
		resultList.add("软件版本:"+redisTemplate.opsForHash().get(id, "devicesoftware"));
		resultList.add("硬件版本:"+redisTemplate.opsForHash().get(id, "devicehardware"));
		return resultList;
	}
	public static List<String> getModulesByMac(String mac,int serviceid){
		RedisTemplate<Serializable, Serializable> redisTemplate=null;
		if(serviceid==1){
			redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
		}else{
			redisTemplate=application.RedisTemplate.getTestRedisTemplate();
		}
		List<String> resultList=new ArrayList<>();
		String key = "DeviceModuleModel"+mac;
		Collection<Serializable> idx = redisTemplate.opsForSet().members(key);
		for (Serializable id: idx) {
			String moduleStatus=redisTemplate.opsForHash().get(id, "modulestatus").toString();
			if(moduleStatus.equals("1") || moduleStatus.equals("2")){
				resultList.add(replaceModuleName(redisTemplate.opsForHash().get(id, "modulename").toString()));
			}
		}
		return resultList;
	}
	
	public static String getBattery(String mac,int serviceid){
		RedisTemplate<Serializable, Serializable> redisTemplate=null;
		if(serviceid==1){
			redisTemplate=application.RedisTemplate.getFormalRedisTemplate();
		}else{
			redisTemplate=application.RedisTemplate.getTestRedisTemplate();
		}
		Object object=redisTemplate.opsForHash().get("zigbee_long_battery",mac);
		if(object==null){
			return "无";
		}
		return object.toString();
	}
	
	public static String getStatus(Object status){
		if(status.equals("0")){
			return "close";
		}else if(status.equals("1")){
			return "open";
		}
		return status.toString();
	}
	
	public static String replaceModuleName(String modulename){
		return modulename.replace("rt_module", "人体感应")
				.replace("mc_module", "门磁")
				.replace("tp_module", "温湿度")
				.replace("ir_module", "红外")
				.replace("rf_module", "射频")
				.replace("tp_zigbee", "温湿度")
				.replace("rt_zigbee", "人体感应")
				.replace("mc_zigbee", "门磁")
				.replace("smartlock_zigbee", "门锁");
	}

}
