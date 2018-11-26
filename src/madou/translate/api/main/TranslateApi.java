package madou.translate.api.main;

import org.apache.log4j.Logger;
import org.apache.log4j.LoggerFactory;
import org.apache.log4j.PropertyConfigurator;

import com.jfinal.kit.PropKit;


import madou.translate.api.impl.baidu.BaiduTranslateApi;
import madou.translate.api.impl.google.GoogleTranslateApi;
import madou.translate.api.util.ConfigInitHelper;
import madou.translate.api.util.Constants;
import madou.translate.api.vo.LanauageTypeEnum;
import madou.translate.api.vo.TranslatePlatformEnum;

/**
 * 对外的出口主要类
 * 
 * @author Yucong.Ma
 *
 */
public class TranslateApi {
	static Logger logger = LoggerFactory.getLogger(TranslateApi.class);

	public TranslateApi(){
		init();
	}
	
    /**
     * 初始化配置
     * 
     * @return
     * @author Yucong.Ma
     * @date 2017年11月3日 上午10:21:23
     */
    public boolean init(){
    	//初始化配置
    	return ConfigInitHelper.initPlatfomConfig();
    }
    
    /**
     * 翻译功能入口
     * 
     * @param type			翻译平台类型
     * @param queryStr		待翻译文字
     * @param fromLanguageType	源语言类型
     * @param toLanguage		目标语言类型
     * @return	翻译结果
     * @author Yucong.Ma
     * @date 2017年11月3日 上午11:01:27
     */
    public String getTranslateResult(TranslatePlatformEnum type,String queryStr, 
    		LanauageTypeEnum fromLanguageType, LanauageTypeEnum toLanguage) {
    	if(TranslatePlatformEnum.BAIDU.equals(type)){
    		return BaiduTranslateApi.getTranslateResultUsingDefaultKey(queryStr, fromLanguageType, toLanguage);
    	}else if(TranslatePlatformEnum.ALL.equals(type) || TranslatePlatformEnum.GOOGLE.equals(type)){
    		return GoogleTranslateApi.getTranslateResultUsingDefaultKey(queryStr, fromLanguageType, toLanguage);
    	}else{
    		return "待开发";
    	}
    }
    
    /**
     * 翻译功能入口
     * 
     * @param type			翻译平台类型
     * @param queryStr		待翻译文字
     * @param fromLanguageType	源语言类型
     * @param toLanguage		目标语言类型
     * @return	翻译结果
     * @author Yucong.Ma
     * @date 2017年11月3日 上午11:01:27
     */
    public String getTranslateResult(String queryStr, 
    		LanauageTypeEnum fromLanguageType, LanauageTypeEnum toLanguage) {
    	if(TranslatePlatformEnum.BAIDU.equals(Constants.API_PLATFORM)){
    		return BaiduTranslateApi.getTranslateResultUsingDefaultKey(queryStr, fromLanguageType, toLanguage);
    	}else if(TranslatePlatformEnum.ALL.equals(Constants.API_PLATFORM) ||
    			TranslatePlatformEnum.GOOGLE.equals(Constants.API_PLATFORM)){
    		return GoogleTranslateApi.getTranslateResultUsingDefaultKey(queryStr, fromLanguageType, toLanguage);
    	}else{
    		return "待开发";
    	}
    }
    
    public static void main(String[] argv) {
		logger.info("获取配置");
		PropKit.use("a_little_config.txt");

		logger.info("加载日志配置");
		PropertyConfigurator.configureAndWatch("log4j.properties");
		
    	String query = "源字符串";

    	TranslateApi api = new TranslateApi();
    	String res = api.getTranslateResult(TranslatePlatformEnum.GOOGLE, query, LanauageTypeEnum.auto,LanauageTypeEnum.zh);
    	res = api.getTranslateResult(query, LanauageTypeEnum.auto,LanauageTypeEnum.zh);
        
    	System.out.println("查询："+query);
		System.out.println("翻译结果：" + res);
    	
    }
}
