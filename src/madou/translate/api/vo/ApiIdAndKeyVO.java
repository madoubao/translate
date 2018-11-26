package madou.translate.api.vo;

import java.io.Serializable;

public class ApiIdAndKeyVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8250880025312922789L;
	
	private String appId;
	private String secretKey;
	
	public ApiIdAndKeyVO(String appId,String secretKey){
		this.appId = appId;
		this.secretKey = secretKey;
	}
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IdKeyVO [appId=");
		builder.append(appId);
		builder.append(", secretKey=");
		builder.append(secretKey);
		builder.append("]");
		return builder.toString();
	}
	
}
