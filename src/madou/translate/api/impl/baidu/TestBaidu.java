package casia.translate.api.impl.baidu;

import casia.translate.api.vo.LanauageTypeEnum;

class TestBaidu {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
	// 20170627000060538\:90BN5Yv_n92G470UlGTh,20170703000062143\:Q55kMNNvmJf2lpxhKYQc,20170720000065470\:7VO5CgXc2FH1xyDLYorb,20170729000069226\:sY6_8ZwNAPOO4FjKNgkI
    private static final String APP_ID = "20170627000060538";
    private static final String SECURITY_KEY = "90BN5Yv_n92G470UlGTh";

    public static void main(String[] args) {
        String query = "العثور على قاذفات صواريخ ومدافع فى مخبأ أسلحة ضخم في بنجلادش";
        // [在这方面]我们重申致力于执行联合国全球反恐战略。
        String res = BaiduTranslateApi.getTranslateResult(APP_ID, SECURITY_KEY, query, LanauageTypeEnum.auto,LanauageTypeEnum.zh);
        System.out.println(query);
		System.out.println("res=" + res);
    }
}
