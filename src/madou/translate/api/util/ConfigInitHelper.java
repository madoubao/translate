package madou.translate.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.LoggerFactory;

import madou.translate.api.vo.ApiIdAndKeyVO;
import madou.translate.api.vo.TranslatePlatformEnum;

import com.jfinal.kit.PropKit;

/**
 * 初始化配置
 * 同一个key，调用不能太频繁，且每月可翻译字符数有限制，因此使用多个key。上层通过id array随机调用
 * 
 * api_platform=baidu
 * baidu_app_id_key=id1:key1,id2:key2
 * bing_app_id_key=id1:key1,id2:key2
 * google_app_id_key=id1:key1,id2:key2
 * 
 * @author Yucong.Ma
 * @date 2017年11月2日 下午3:52:16
 */
public class ConfigInitHelper {
	static Logger logger = LoggerFactory.getLogger(ConfigInitHelper.class);
	
	/**
	 * 初始化配置项 在配置文件中的名称
	 */
	static{
		Constants.PLATFOMR_CONFIG_NAME = new HashMap<TranslatePlatformEnum, String>();
		Constants.PLATFOMR_CONFIG_NAME.put(TranslatePlatformEnum.BAIDU,"baidu_app_id_key");
		Constants.PLATFOMR_CONFIG_NAME.put(TranslatePlatformEnum.BING,"bing_app_id_key");
		Constants.PLATFOMR_CONFIG_NAME.put(TranslatePlatformEnum.GOOGLE,"google_app_id_key");
		
		Constants.API_PLATFORM = TranslatePlatformEnum.valueOf(PropKit.get("api_platform","GOOGLE").toUpperCase());
		Constants.GOOGLE_METHOD = PropKit.get("google_method","nmt").trim();
		logger.info("初始化翻译平台 "+Constants.API_PLATFORM);
	}
	
	/**
	 * 初始化所有平台的配置
	 * 
	 * @author Yucong.Ma
	 * @date 2017年11月2日 下午3:52:16
	 */
	public static boolean initPlatfomConfig(){
		try{
			//配置为all，则全部初始化
			if(TranslatePlatformEnum.ALL.equals(Constants.API_PLATFORM)){
				for(TranslatePlatformEnum platform : TranslatePlatformEnum.values()){
					initSinglePlatformConfig(platform);
				}
				
			}else{//否则，单个平台初始化
				initSinglePlatformConfig(Constants.API_PLATFORM);
			}
			
			return true;
		}catch(Exception e){
			logger.info("初始化错误",e);
		}finally{
			
		}
		
		return false;
	}
	
	/**
	 * 初始化单个平台的配置
	 * 
	 * @author Yucong.Ma
	 * @date 2017年11月2日 下午3:52:16
	 */
	public static boolean initSinglePlatformConfig(TranslatePlatformEnum platform){
		try{
			if(null == platform || platform.equals(TranslatePlatformEnum.ALL)){
				return false;
			}
			logger.info("platform "+platform);
			
			String idKeyConfigStr = PropKit.get(Constants.PLATFOMR_CONFIG_NAME.get(platform));
			logger.info("初始化翻译配置 , "+platform+", config="+idKeyConfigStr);
			
			if(StringUtils.isNotBlank(idKeyConfigStr)){
				
				List<ApiIdAndKeyVO> keyList = new ArrayList<ApiIdAndKeyVO>();
				String[] idAndKeys = idKeyConfigStr.trim().split(",");
				for(String obj : idAndKeys){
					String[] idArray = obj.trim().split(":");
					if(null != idArray && idArray.length > 1)
					{
						keyList.add(new ApiIdAndKeyVO(idArray[0], idArray[1]));
					}else{
						logger.error("app_id app_key不合法,"+idKeyConfigStr);
					}
				}
				idAndKeys = null;
				
				//放入map
				if(null == Constants.PLATFORM_ID_KEY_MAP){
					Constants.PLATFORM_ID_KEY_MAP = new HashMap<TranslatePlatformEnum,List<ApiIdAndKeyVO>>();
				}
				Constants.PLATFORM_ID_KEY_MAP.put(platform, keyList);

				//标记已初始化
				if(null == Constants.PLATFORM_ALREAY_INIT){
					Constants.PLATFORM_ALREAY_INIT = new HashMap<TranslatePlatformEnum, Boolean>(); 
				}
				Constants.PLATFORM_ALREAY_INIT.put(platform, true);
				
				return true;
			}else{
				logger.error("initSinglePlatform "+platform+" 配置为空");
			}
			return false;
		}catch(Exception e){
			logger.info("initSinglePlatform，"+platform,e);
		}finally{
			
		}
		
		return false;
	}
	
	/**
	 * 随机获取某个翻译平台下的appId和secretKey
	 * 
	 * @param type
	 * @return
	 * @author Yucong.Ma
	 * @date 2017年11月2日 下午6:46:12
	 */
	public static ApiIdAndKeyVO getRandomIdAndKey(TranslatePlatformEnum type){
		
		if(null == type){
			logger.error("获取随机id和key  type为空");
			return null;
		}
		
		//获得平台下， id和key的map
		List<ApiIdAndKeyVO> voList = Constants.PLATFORM_ID_KEY_MAP.get(type);
		if(null == voList || voList.size()==0){
			logger.error("获取随机id和key。error：已有id和key为空");
			return null;
		}
		
		//随机得到一个id
	   	return voList.get(new Random().nextInt(voList.size()));
	}

	public static void main(String[] args) {
		for(int i=0;i<20;i++){
			System.out.println(new Random().nextInt(3));
		}
	}
}