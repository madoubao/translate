package casia.translate.api.impl.google;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import casia.ibasic.fusionsight.common.util.CommonFactory;
import casia.translate.api.util.ConfigInitHelper;
import casia.translate.api.util.Constants;
import casia.translate.api.vo.ApiIdAndKeyVO;
import casia.translate.api.vo.LanauageTypeEnum;
import casia.translate.api.vo.TranslatePlatformEnum;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *谷歌翻译入口
 */
public class GoogleTranslateApi{
	static final Logger logger = CommonFactory.createLogger(GoogleTranslateApi.class);

	/**
	 * 翻译入口，使用配置文件中的appId和key
	 * 
	 * @param key
	 * @param queryStr
	 * @param fromLanguage
	 * @param toLanguage
	 * @param model  翻译模式
	 * @return	翻译结果
	 */
    public static String getTranslateResultUsingDefaultKey(String queryStr,LanauageTypeEnum fromLanguage, 
    		LanauageTypeEnum toLanguage) {
    	if(null == Constants.PLATFORM_ALREAY_INIT || (!Constants.PLATFORM_ALREAY_INIT.get(TranslatePlatformEnum.GOOGLE))){
    		ConfigInitHelper.initSinglePlatformConfig(TranslatePlatformEnum.GOOGLE);
    	}
    	
    	ApiIdAndKeyVO idAndKey = ConfigInitHelper.getRandomIdAndKey(TranslatePlatformEnum.GOOGLE);
    	return getTranslateResult(idAndKey.getAppId(),idAndKey.getSecretKey(),queryStr,fromLanguage,toLanguage);
    }
	
	/**
	 * 翻译入口，使用特定的appId和key
	 * @param key
	 * @param queryStr
	 * @param fromLanguage
	 * @param toLanguage
	 * @param model  翻译模式
	 * @return	翻译结果
	 */
    public static String getTranslateResult(String app_id, String security_key, String queryStr, 
    		LanauageTypeEnum fromLanguage, LanauageTypeEnum toLanguage) {
    	
    	NetRequest api = new NetRequest(app_id, security_key);
    	String res = api.getTransResult(queryStr, fromLanguage.name(), toLanguage.name());
    	try{
	        if(StringUtils.isBlank(res)){
	    		logger.error("google getTranslateResult no result,"+res);
	        	return null;
	        }
	        	
	        JSONObject rjo = JSONObject.parseObject(res);
	        if(null == rjo){
	        	return null;
	        }
	
	        JSONObject error = rjo.getJSONObject("error");
	        //有错误
	        if(null != error ){
	        	Integer code = error.getInteger("code");
	        	String message = error.getString("message");
	        	logger.error("google getTranslateResult error,errorCode="+code+
	        			" error_msg="+message);
	        	return null;
	        }
	        
	        JSONObject data1 = rjo.getJSONObject("data");
	    	JSONArray data = data1.getJSONArray("translations");
			if (data != null && data.size() > 0) {
				return data.getJSONObject(0).getString("translatedText");
			}
    	}catch(Exception e){
    		logger.error("google getTranslateResult exception,"+e);
    	}

		return null;
    }

}
