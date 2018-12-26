package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class DeviceHandler extends BaseHandler {
	
	private List<Device> mDevices;
	
	public DeviceHandler() {
		super();
		mDevices = restoreDevices();
	}
	
	private List<Device> restoreDevices() {
		List<Device> devices = new ArrayList<>();
		devices.add(new Device("1","RTV9008w","4162946921","AIS xDSL Router 1"));
		devices.add(new Device("2","RTV9008w","4162946922","AIS xDSL Router 2"));
		devices.add(new Device("3","RTV9008w","4162946923","AIS xDSL Router 3"));
		return devices;
	}
	
	@Override
	protected BaseResponse createResponseByGET(HttpExchange param) {
		
		BaseResponse response = null;
		String query = param.getRequestURI().getQuery();
		
		
		
		
		
		Map<String, String> queryMap = getQeuryMap(query);
		if(queryMap.get("id")!=null) {
			String id = queryMap.get("id");
			Device device = getDeviceById(id);
			if(device != null) {
				response = new BaseResponse(200, toJson(device));
			}else {
				response =  new BaseResponse(401, "no device with id: " + id);
			}
		}else {
			response =  new BaseResponse(400, "no query");
		}
		
		return response;		
	}

	@Override
	protected BaseResponse createResponseByPOST(HttpExchange param) {
		String req_body = toString(param.getRequestBody());
		Device de = fromJson(req_body, Device.class);
		updateDeviceName(de.id, de.name);
		return new BaseResponse(200, "Update Device Success");
	}

	@Override
	protected BaseResponse createResponseByPUT(HttpExchange param) {
		String req_body = toString(param.getRequestBody());
		Device de = fromJson(req_body, Device.class);
		updateDevice(de.id, de);
		return new BaseResponse(200, "Update Device Success");
	}

	@Override
	protected BaseResponse createResponseByDELETE(HttpExchange param) {
		return null;
	}
	
	private synchronized void updateDeviceName(String id, String name) {
		Device device = getDeviceById(id);
		if(device != null) {
			device.name = name;
		}
	}
	
	private synchronized void updateDevice(String id, Device de) {
		Device device = getDeviceById(id);
		if(device != null) {
			device.id = de.id;
			device.name = de.name;
			device.model = de.model;
			device.sn = de.sn;
		}
	}
	
	private Device getDeviceById(String id) {
		for(Device d: mDevices) {
			if(d.id.equals(id)) {
				return d;
			}
		}
		return null;
	}

}
