package casia.translate.api.impl.baidu;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import casia.translate.api.util.MD5;

/**
 * 百度翻译网络请求
 * 
 * @author mayucong
 */
public class NetRequest {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public NetRequest(String appid, String securityKey){
    	if(StringUtils.isBlank(appid) || StringUtils.isBlank(securityKey)){
    		throw  new IllegalArgumentException("app_id or security_key is null.");
    	}
    	
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", appid);
        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}
