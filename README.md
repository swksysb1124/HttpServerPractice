# HttpServerPractice

在此範例中，對HttpHandler做了簡單的封裝：繼承 BaseHandler，僅需直接實現接收到不同 HTTP Method 的請求的處理方式即可。


## 基本說明

幾個重要的類別
- HttpServer
- HttpExchange
- HttpHandler

### HttpServer

提供Http Server的實體。一旦啟動就會阻塞執行續直到它關閉。

```java
private HttpServer mServer;
```
初始化HttpServer
```java
private void initServer() throws IOException {
    mServer = HttpServer.create(new InetSocketAddress(PORT), 0); // bind port and ip
    mServer.createContext("/device", new DeviceHandler()); // set context & its http handler
    mServer.setExecutor(Executors.newFixedThreadPool(4)); // set executor
}
```
開啟HttpServer
```java
mServer.start();
```

關閉HttpServer
```java
mServer.stop(0);
```
### HttpExchange

該類別提供 Http Request 的所有資訊，包含 Method, Headers, Request Body, Query ...

### HttpHandler

為抽象類別，必須實作`void handle(httpExchange)`方法，也就是在特定的Context下，處理不同 Http Request的方法。我
所謂的Context，其實指的就是相對於root URL的路徑(Path) ex: `/device`

```java
public class YourHandler implements HttpHandler{
	
    @Override
    public void handle(HttpExchange param) throws IOException {
		
        String method = param.getRequestMethod();
        Headers headers = param.getRequestHeaders();
        URI requestURI = param.getRequestURI();
        InetAddress remoteAddress = param.getRemoteAddress().getAddress();
        
		// 基於 Request 的 Method, Headers, URI, Qeuery 做處理 
        // ...
		
        param.close();	
	}
}

```

## Reference
- [HttpServer (Java HTTP Server ) - Oracle Docs](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html)
- [如何使用 Java 内置的 Http Server 构建 Web 应用（不使用 Web 容器)？](https://zhuanlan.zhihu.com/p/33014244)
