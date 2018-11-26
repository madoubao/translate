package casia.translate.api.impl.baidu;
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
 *
 * 	正常：{"from":"en","to":"zh","trans_result":[{"src":"[In this context]we reaffirm our commitment to the implementation of the UN Global Counter-Terrorism Strategy","dst":"[\u5728\u8fd9\u65b9\u9762]\u6211\u4eec\u91cd\u7533\u81f4\u529b\u4e8e\u6267\u884c\u8054\u5408\u56fd\u5168\u7403\u53cd\u6050\u6218\u7565\u3002"}]}
 *
 *  异常：{"error_code":"52003","error_msg":"UNAUTHORIZED USER"}
 *  错误码	含义				解决方法
	52000	成功	
	52001	请求超时			重试
	52002	系统错误			重试
	52003	未授权用户			检查您的 appid 是否正确
	54000	必填参数为空		检查是否少传参数
	54001	签名错误			请检查您的签名生成方法
	54003	访问频率受限		请降低您的调用频率
	54004	账户余额不足		请前往管理控制平台为账户充值
	54005	长query请求频繁	请降低长query的发送频率，3s后再试
	58000	客户端IP非法		检查个人资料里填写的IP地址 是否正确 ，可前往管理控制平台修改 IP限制，IP可留空
	58001	译文语言方向不支持	检查译文语言是否在语言列表里
 * 
 * @author mayucong
 *
 */
public class BaiduTranslateApi{
	static final Logger logger = CommonFactory.createLogger(BaiduTranslateApi.class);

	/**
	 * 翻译入口，使用配置文件中的appId和key
	 * 
	 * @param app_id
	 * @param security_key
	 * @param queryStr
	 * @param fromLanguage
	 * @param toLanguage
	 * @return	翻译结果
	 */
    public static String getTranslateResultUsingDefaultKey(String queryStr,LanauageTypeEnum fromLanguage, 
    		LanauageTypeEnum toLanguage) {
    	if(null == Constants.PLATFORM_ALREAY_INIT || (!Constants.PLATFORM_ALREAY_INIT.get(TranslatePlatformEnum.BAIDU))){
    		ConfigInitHelper.initSinglePlatformConfig(TranslatePlatformEnum.BAIDU);
    	}
    	
    	ApiIdAndKeyVO idAndKey = ConfigInitHelper.getRandomIdAndKey(TranslatePlatformEnum.BAIDU);
//    	logger.info("获取随机id和key "+idAndKey.toString());
    	return getTranslateResult(idAndKey.getAppId(),idAndKey.getSecretKey(),queryStr,fromLanguage,toLanguage);
    }
	
	/**
	 * 翻译入口，使用特定的appId和key
	 * 
	 * @param app_id
	 * @param security_key
	 * @param queryStr
	 * @param fromLanguage
	 * @param toLanguage
	 * @return	翻译结果
	 */
    public static String getTranslateResult(String app_id, String security_key, String queryStr, 
    		LanauageTypeEnum fromLanguage, LanauageTypeEnum toLanguage) {
    	
    	NetRequest api = new NetRequest(app_id, security_key);
    	String res = api.getTransResult(queryStr, fromLanguage.name(), toLanguage.name());
    	try{
	        if(StringUtils.isBlank(res)){
	    		logger.error("baidu getTranslateResult no result,"+res);
	        	return null;
	        }
	        	
	        JSONObject rjo = JSONObject.parseObject(res);
	        if(null == rjo){
	        	return null;
	        }
	
	        Integer errorCode = rjo.getInteger("error_code");
	        //有错误
	        if(null != errorCode && errorCode >= 52001){
	        	logger.error("baidu getTranslateResult error,errorCode="+errorCode+
	        			" error_msg="+rjo.getString("error_msg"));
	        	return null;
	        }
	        
	    	JSONArray data = rjo.getJSONArray("trans_result");
			if (data != null && data.size() > 0) {
				return data.getJSONObject(0).getString("dst");
			}
    	}catch(Exception e){
    		logger.error("baidu getTranslateResult exception,"+e);
    	}

		return null;
    }

}
