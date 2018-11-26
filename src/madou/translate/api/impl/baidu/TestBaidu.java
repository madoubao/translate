package madou.translate.api.impl.baidu;

import madou.translate.api.vo.LanauageTypeEnum;

class TestBaidu {

    private static final String APP_ID = "id";
    private static final String SECURITY_KEY = "key";

    public static void main(String[] args) {
        String query = "源字符串";
        String res = BaiduTranslateApi.getTranslateResult(APP_ID, SECURITY_KEY, query, LanauageTypeEnum.auto,LanauageTypeEnum.zh);
        System.out.println(query);
		System.out.println("res=" + res);
    }
}
