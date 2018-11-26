实现第三方翻译 SDK 的调用。翻译平台支持：百度、google、bing \n
（1）只需在配置文件中配置两项，即可直接使用：
    1.翻译平台
    2.APP_ID 和 APP_KEY(一个或多个)
（2）根据配置文件，本程序会自动选择相应的翻译平台
（3）因为部分第三方翻译平台有限制，如果单个APP_ID调用过于频繁 将会导致没有数据返回。
    建议申请多个账号、程序内部逻辑会随机选择不同的APP_ID进行接口请求


''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
1、百度
SDK官网： http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
在平台申请APP_ID。每个账号每个月200万字符内免费。
	
2、bing
SDK官网：http://www.microsoft.com/en-us/translator/translatorapi.aspx
http://account.azure.com
 
3、google
SDK官网：https://cloud.google.com/translate
  a.注册：
	1)中国visa卡通过注册。注册时选择“个人”账号。需要上传身份证、卡号、账单等信息。注册时会扣除1美元做测试
	2)注册账号赠送300美金、期限为一年，超过会扣钱
	3)如果超出会有账单

  b.请求路径及参数
	url：
		https://translation.googleapis.com/language/translate/v2
	入参说明：  
		q：string  必需输入要翻译的文本。重复此参数以在多个文本输入上执行翻译操作。
		target：string  必需用于翻译输入文本的语言，设置为语言支持中列出的语言代码之一。
	        format：string  源文本的格式，采用HTML（默认）或纯文本格式。值html表示HTML，值text 表示纯文本。
	        source：string  源文本的语言，设置为语言支持中列出的语言代码之一 。如果未指定源语言，则API将尝试自动检测源语言，并在响应中将其返回。
	        model：  string  翻译模式。两种：base、nmt。 base使用基于短语的机器翻译（PBMT）模型，nmt使用神经机器翻译（NMT）模型。默认为nmt。如果模型是nmt，且NMT模型不支持对请求的语言进行翻译，则使用base模型翻译该请求。
	       key：       string  一个有效的API密钥来处理这个API的请求。如果您使用的是OAuth 2.0服务帐户凭据（推荐），请不要提供此参数。
	       
	 重要提示：
	 提供两种翻译模式 nmt模式(推荐)、base模式(不推荐)
	 目前nmt翻译模式只支持部分语种直接翻译成英文，不能直接翻译成其他语种。
	 若翻译请求使用nmt翻译模式，并且目标语种非英文语种，请勿添加source参数，谷歌尝试自动检测保证翻译结果正确。
	 若使用base模式，则可以添加source参数。
       
	返回参数说明：
		{
		  "data": {
		    "translations": [
		      {
		        "translatedText": "你好",
		        "detectedSourceLanguage": "en"
		      }
		    ]
		  }
		}

		translations[]:   array     包含所提供文本的翻译结果列表
		translatedText:   string    将文本翻译成目标语言,即翻译结果
		detectedSourceLanguage:  string  如果在初始请求中没有传递源语言，则自动检测到初始请求的源语言。如果源语言已通过，则不会出现语言的自动检测，并且此字段将被忽略
