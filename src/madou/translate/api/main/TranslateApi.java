package casia.translate.api.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jfinal.kit.PropKit;

import casia.ibasic.fusionsight.common.util.CommonFactory;
import casia.translate.api.impl.baidu.BaiduTranslateApi;
import casia.translate.api.impl.google.GoogleTranslateApi;
import casia.translate.api.util.ConfigInitHelper;
import casia.translate.api.util.Constants;
import casia.translate.api.vo.LanauageTypeEnum;
import casia.translate.api.vo.TranslatePlatformEnum;

/**
 * 对外的出口主要类
 * 
 * @author mayucong
 *
 */
public class TranslateApi {
	static Logger logger = CommonFactory.createLogger(TranslateApi.class);

	public TranslateApi(){
		init();
	}
	
    /**
     * 初始化配置
     * 
     * @return
     * @author mayucong
     * @date 2017年11月3日 上午10:21:23
     */
    public boolean init(){
    	//初始化配置
    	return ConfigInitHelper.initPlatfomConfig();
    }
    
    /**
	 * 翻译功能入口
	 * 
	 * @param queryStr
	 * @param fromLanguage
	 * @param toLanguage
	 * @return	
	 */
    /**
     * 翻译功能入口
     * 
     * @param type			翻译平台类型
     * @param queryStr		待翻译文字
     * @param fromLanguageType	源语言类型
     * @param toLanguage		目标语言类型
     * @return	翻译结果
     * @author mayucong
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
     * @author mayucong
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
		
    	String query = "[In this context]we reaffirm our commitment to the implementation of the UN Global Counter-Terrorism Strategy";

    	TranslateApi api = new TranslateApi();
    	String res = api.getTranslateResult(TranslatePlatformEnum.GOOGLE, query, LanauageTypeEnum.auto,LanauageTypeEnum.zh);
    	res = api.getTranslateResult(query, LanauageTypeEnum.auto,LanauageTypeEnum.zh);
        
    	System.out.println("查询："+query);
		System.out.println("翻译结果：" + res);
    	
        // [在这方面]我们重申致力于执行联合国全球反恐战略。	 bai
        // [在这方面]我们重申对执行“联合国全球反恐战略”的承诺 google
    }
}
