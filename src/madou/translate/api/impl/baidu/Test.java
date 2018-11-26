package madou.translate.api.impl.baidu;

import madou.translate.api.vo.LanauageTypeEnum;

class Test {

    public static void main(String[] args) {
		String query = "源字符串";

		TranslateApi api = new TranslateApi();
		String result = api.getTranslateResult(query, LanauageTypeEnum.auto,LanauageTypeEnum.zh);
        
		System.out.println("查询："+query);
		System.out.println("翻译结果：" + result);
		
		String result = api.getTranslateResult(TranslatePlatformEnum.GOOGLE, query, LanauageTypeEnum.auto,LanauageTypeEnum.zh);
		System.out.println("翻译结果：" + result);
    }
}
