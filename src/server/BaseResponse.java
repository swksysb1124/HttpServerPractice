package server;

public class BaseResponse {
	public int statusCode;
	public String response;
	
	public BaseResponse() {}

	public BaseResponse(int statusCode, String response) {
		super();
		this.statusCode = statusCode;
		this.response = response;
	}
	
	@Override
	public String toString() {
		return "["+statusCode+"] "+response;
	}
}
