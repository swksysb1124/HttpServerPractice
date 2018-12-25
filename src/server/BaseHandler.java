package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class BaseHandler implements HttpHandler{
	
	@Override
	public void handle(HttpExchange param) 
			throws IOException {
		
		String method = param.getRequestMethod();
		
		System.out.print(method+" "+param.getRequestURI());
		System.out.print("\t"+param.getRemoteAddress().getAddress().toString());
		System.out.println("@"+new Date().toString());
		
		BaseResponse b_response = null;
		
		switch(method) {
		
			case "GET":
				b_response = createResponseByGET(param);
				break;
				
			case "POST":
				b_response = createResponseByPOST(param);
				break;
			
			case "PUT":
				b_response = createResponseByPUT(param);
				break;
				
			case "DELETE":
				b_response = createResponseByDELETE(param);
				break;
				
		}
		
		if(b_response.response == null) {
			b_response.response = "not valid parameter or path";
			b_response.statusCode = 401;
		}
		
		System.out.println("Response: "+b_response.toString());
		
		param.sendResponseHeaders(b_response.statusCode, b_response.response.length());
		
		
		OutputStream os = param.getResponseBody();
		os.write(b_response.response.getBytes());
		os.flush();
		
		param.close();
		
	}
	
	protected String toJson(Object bean) {
		Gson gson = new Gson();
		return gson.toJson(bean);
	}
	
	protected <T> T fromJson(String json, Class<T> classofT) {
		Gson gson = new Gson();
		return gson.fromJson(json, classofT);
	}
	
	protected String toString(InputStream is) {
		int ch;
		StringBuilder sb = new StringBuilder();
		try {
			while((ch = is.read()) != -1) {
				sb.append((char)ch);
			}
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected Map<String, String> getQeuryMap(String query) {
		Map<String, String> map = new HashMap<>();
		if(query != null) {
			String[] queryPairs = query.split("&");
			for(String pair: queryPairs) {
				String[] keyValue = pair.split("=");
				map.put(keyValue[0], keyValue[1]);
			}
 		}
		return map;
	}
	
	protected abstract BaseResponse createResponseByGET(HttpExchange param);
	protected abstract BaseResponse createResponseByPOST(HttpExchange param);
	protected abstract BaseResponse createResponseByPUT(HttpExchange param);
	protected abstract BaseResponse createResponseByDELETE(HttpExchange param);
	
}
