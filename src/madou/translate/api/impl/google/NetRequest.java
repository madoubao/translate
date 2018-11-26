package madou.translate.api.impl.google;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import madou.translate.api.util.Constants;
import madou.translate.api.util.MD5;

/**
 * GOOGLE翻译网络请求
 * 
 * @author Yucong.Ma
 */
public class NetRequest {
    private static final String TRANS_API_HOST = "https://translation.googleapis.com/language/translate/v2";

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

    /*
     * q：string       必需输入要翻译的文本。重复此参数以在多个文本输入上执行翻译操作。
	   target：string  必需用于翻译输入文本的语言，设置为语言支持中列出的语言代码之一。
       format：string  源文本的格式，采用HTML（默认）或纯文本格式。值html表示HTML，值text 表示纯文本。
       source：string  源文本的语言，设置为语言支持中列出的语言代码之一 。如果未指定源语言，则API将尝试自动检测源语言，并在响应中将其返回。
       model：string   翻译模式。既 base可以使用基于短语的机器翻译（PBMT）模型，也nmt可以使用神经机器翻译（NMT）模型。如果省略，则nmt 使用。如果模型是nmt，NMT模型不支持请求的语言翻译对，则使用该base模型翻译该请求。
       key：string     一个有效的API密钥来处理这个API的请求。如果您使用的是OAuth 2.0服务帐户凭据（推荐），请不要提供此参数。
      */
    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("target", to);
        if(StringUtils.isNoneBlank(securityKey)){
        	params.put("key", securityKey);
        }
        String model = Constants.GOOGLE_METHOD;
        if(StringUtils.isNoneBlank(model)){
        	if(model.trim().equals("base")){
        		 params.put("model", "base");
        	}else if(model.trim().equals("nmt")){
        		 params.put("model", "nmt");
        	}
        }
        if(StringUtils.isNoneBlank(from)){
        	if(!from.trim().equals("auto")){
        		params.put("source", from);
        	}
        }
        return params;
    }

}
