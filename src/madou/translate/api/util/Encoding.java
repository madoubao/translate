package madou.translate.api.util;;

/**
 */
public class Encoding {
	public static int GB2312 = 0;

	public static int GBK = 1;

	public static int GB18030 = 2;

	public static int HZ = 3;

	public static int BIG5 = 4;

	public static int CNS11643 = 5;

	public static int UTF8 = 6;

	public static int UTF8T = 7;

	public static int UTF8S = 8;

	public static int UNICODE = 9;

	public static int UNICODET = 10;

	public static int UNICODES = 11;

	public static int ISO2022CN = 12;

	public static int ISO2022CN_CNS = 13;

	public static int ISO2022CN_GB = 14;

	public static int EUC_KR = 15;

	public static int CP949 = 16;

	public static int ISO2022KR = 17;

	public static int JOHAB = 18;

	public static int SJIS = 19;

	public static int EUC_JP = 20;

	public static int ISO2022JP = 21;

	public static int ASCII = 22;

	public static int OTHER = 23;

	public static int TOTALTYPES = 24;

	public final static int SIMP = 0;

	public final static int TRAD = 1;

	// Names of the encodings as understood by Java
	public static String[] javaname = { "GB2312", "GBK", "GB18030", "ASCII",
			"BIG5", "EUC-TW", "UTF8", "UTF8", "UTF8", "Unicode", "Unicode",
			"Unicode", "ISO2022CN", "ISO2022CN_CNS", "ISO2022CN_GB", "EUC_KR",
			"MS949", "ISO2022KR", "Johab", "SJIS", "EUC_JP", "ISO2022JP",
			"ASCII", "ISO8859_1" };

	// Names of the encodings for human viewing
	public static String[] nicename = { "GB-2312", "GBK", "GB18030", "HZ",
			"Big5", "CNS11643", "UTF-8", "UTF-8 (Trad)", "UTF-8 (Simp)",
			"Unicode", "Unicode (Trad)", "Unicode (Simp)", "ISO2022 CN",
			"ISO2022CN-CNS", "ISO2022CN-GB", "EUC-KR", "CP949", "ISO 2022 KR",
			"Johab", "Shift-JIS", "EUC-JP", "ISO 2022 JP", "ASCII", "OTHER" };

	// Names of charsets as used in charset parameter of HTML Meta tag
	public static String[] htmlname = { "GB2312", "GBK", "GB18030",
			"HZ-GB-2312", "BIG5", "EUC-TW", "UTF-8", "UTF-8", "UTF-8",
			"UTF-16", "UTF-16", "UTF-16", "ISO-2022-CN", "ISO-2022-CN-EXT",
			"ISO-2022-CN-EXT", "EUC-KR", "x-windows-949", "ISO-2022-KR",
			"x-Johab", "Shift_JIS", "EUC-JP", "ISO-2022-JP", "ASCII",
			"ISO8859-1" };

	/**
	 * 获取字符串的编码
	 * 
	 * @param codeString
	 * @return type code
	 * @author ahui wang
	 */
	public static int getCodeType(String codeString) {
		String str = codeString.toLowerCase();
		for (int i = 0; i < TOTALTYPES; i++) {
			if (str.compareTo(javaname[i].toLowerCase()) == 0
					|| str.compareTo(htmlname[i].toLowerCase()) == 0
					|| str.compareTo(nicename[i].toLowerCase()) == 0) {
				return i;
			}
		}
		return OTHER;
	}

	public static void main(String args[]) {

	}
}
