package Payload;

public class tokenResponse {

	private String access_token;
	private String expires_in;
	//private String refresh_expires_in;
	
	

	public String getAccess_token() {
		return access_token;
	}

	public tokenResponse(String access_token, String expires_in) {
		super();
		this.access_token = access_token;
		this.expires_in = expires_in;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

//	public String getRefresh_expires_in() {
//		return refresh_expires_in;
//	}
//
//	public void setRefresh_expires_in(String refresh_expires_in) {
//		this.refresh_expires_in = refresh_expires_in;
//	}

}
