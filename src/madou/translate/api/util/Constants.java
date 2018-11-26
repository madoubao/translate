package madou.translate.api.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.LoggerFactory;
import madou.translate.api.vo.ApiIdAndKeyVO;
import madou.translate.api.vo.TranslatePlatformEnum;

/**
 * 常量类
 */
public class Constants {
	static final Logger logger = LoggerFactory.getLogger(Constants.class);
	
	//平台选择：baidu bing google
	public static TranslatePlatformEnum API_PLATFORM;
	
	//每个平台对应的  appid和key
	public static Map<TranslatePlatformEnum,List<ApiIdAndKeyVO>> PLATFORM_ID_KEY_MAP;
	
	//记录每个平台是否已经初始化
	public static Map<TranslatePlatformEnum,Boolean> PLATFORM_ALREAY_INIT;
	
	//配置文件中 每个平台config项的名称
	public static Map<TranslatePlatformEnum,String> PLATFOMR_CONFIG_NAME;
	
	//谷歌翻译方式
	public static String GOOGLE_METHOD;
}
