package Payload;

public class ErrorResponse {

	private String description;
	private String code;

	// private String refresh_expires_in;
	public ErrorResponse(String description, String code) {
		super();
		this.description = description;
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public String getRefresh_expires_in() {
//		return refresh_expires_in;
//	}
//
//	public void setRefresh_expires_in(String refresh_expires_in) {
//		this.refresh_expires_in = refresh_expires_in;
//	}

}
