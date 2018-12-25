# HttpServerPractice

使用Java 的 HttpServer 類別，建立簡單的測試server

幾個重要的類別
- HttpServer
- HttpExchange
- HttpHandler

## HttpHandler
為抽象類別，必須實作`void handle(httpExchange)`方法，也就是在特定的Context下，處理不同 Http Request的方法。

## HttpExchange
該類別提供Http Request的所有資訊。

## HttpServer
提供Http Server的實體。一旦啟動就會阻塞執行續直到它關閉。

```java
private HttpServer mServer;
```
初始化HttpServer
```java
private void initServer() throws IOException {
    mServer = HttpServer.create(new InetSocketAddress(PORT), 0);
    mServer.createContext("/device", new DeviceHandler());
    mServer.setExecutor(Executors.newFixedThreadPool(4)); // create a default executor
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


在此範例中，對HttpHandler做了簡單的封裝：繼承 BaseHandler，僅需直接實現接收到不同 HTTP Method 的請求的處理方式即可。



## Reference
- [HttpServer (Java HTTP Server ) - Oracle Docs](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html)
- [如何使用 Java 内置的 Http Server 构建 Web 应用（不使用 Web 容器)？](https://zhuanlan.zhihu.com/p/33014244)
