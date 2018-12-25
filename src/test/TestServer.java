package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import server.DeviceHandler;

public class TestServer {
	
	private HttpServer mServer;
	
	public static final int PORT = 8001;
	
	public TestServer() {
		try {
			initServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initServer() throws IOException {
		mServer = HttpServer.create(new InetSocketAddress(PORT), 0);
		mServer.createContext("/device", new DeviceHandler());
		mServer.setExecutor(Executors.newFixedThreadPool(4)); // create a default executor
	}
	
	public void start() {
		if(mServer != null) {
			mServer.start();
		}
	}
	
	public void stop() {
		if(mServer != null) {
			mServer.stop(0);
		}
	}
}
