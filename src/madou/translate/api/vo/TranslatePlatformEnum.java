package madou.translate.api.vo;

/** 
 * 翻译平台枚举类
 */
public enum TranslatePlatformEnum{
	BAIDU/*百度*/,
	BING/*必应*/,
	GOOGLE/*Google*/,
	ALL/*全部*/;
	  
    public static TranslatePlatformEnum getApiType(String apiType) {  
        return valueOf(apiType.toUpperCase());  
    }
}
