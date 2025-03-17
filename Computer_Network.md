

## 基本概念

### 网络

<img src="/Users/yueyangdong/Library/Application Support/typora-user-images/image-20250314120132467.png" alt="image-20250314120132467" style="zoom:50%;" />

### Layer 层次

```
五层模型结构

| 层级       | 功能                          | 协议示例         | 数据单位       |
|---------- |-----------------------------  |--------------  |---------------|
| 应用层     | 提供应用程序的网络服务            | HTTP、FTP、DNS  | 报文 (Message) |
| 传输层     | 端到端通信，确保数据可靠或快速传输  | TCP、UDP        | 段 (Segment)  |
| 网络层     | 选择最佳路径，负责 IP 地址寻址    | IP、ICMP、ARP    | 数据包 (Packet)|
| 数据链路层  | 组帧、差错控制、MAC 地址通信      | 以太网、Wi-Fi    | 帧 (Frame)    |
| 物理层     | 物理介质传输比特流               | 光纤、双绞线      | 比特 (Bit)     |
```

### Message 报文

- Message = Data + Target Address

### Node 节点

- 终端节点：电脑、手机 ...
- 中间节点：路由器、交换机

### Router 路由

- 在 Routing Table 中 查找 next hoop 

### Protocol 协议、 Service

- 协议：水平，对等层间的数据交换的规则和约定
- 服务：垂直，下层的协议为上层提供服务，上层的协议实现借助下层协议

### 分组&转发

- 0101 1111 --> 0101  +  1111

- Packet = Data segment + Heading：Packet1: 【(Header) +0101】
  - Packet1:【(Header) + 0101】
  - Packet2:【(Header) +1111】
- Segmentation ->Encapsulation -> Transmission -> Buffering  -> Routing 

### Interface 接口

- 层与层之间的数据交换的端点
- **软件：API**
-  **传输层接口: TCP / UDP Socket**
- **应用层接口：HTTP / HTTPS**
- **网络层接口*：IP地址 127.0.0.1**
- 数据链路层接口：MAC 地址接口

- 物理层接口：光纤接口


## 网络层

网络层需要进行 **寻址（Addressing）** 和 **路由（Routing）**，确保数据包能从源主机正确传输到目标主机

### **IP 与 Port (端口)**

- 数据的发送的起点与终点，eg：IP：192.168.1.1， 端口：10000
- 每个主机设备都有自己的 **IP**，每个设备中的每个应用程序都被主机分配了**唯一的端口号**
  - 设备 --> 酒店，应用 --> 酒店客房
  - IP = 酒店地址，Port = 客房门牌号
  - 特殊IP ：127.0.0.1 回环自检，表示本机地址
    - 向这个ip发数据 = 给自己这个电脑发数据，不会出现在网络上
- 数据在网络的发送与传输 
  - 192.202.1.1酒店，客房10086 --> 快递 --> 192.168.1.1酒店，客房10000

### Routing 路由

- **基本概念**

  - 下一跳（Next Hop）：数据的下一个地址 == 快递的中转站

  - Router 路由：网络层的功能，在数据转发的过程中查找数据下一个hoop的目的地址 

  - Router Table 路由表：记录数据目的地的信息，通过查表获取 == 快递中转站地图

  - 路由器：转发数据的设备 

- **路由过程**

  ```
  - 主机封装，端口发送：【要发送的数据信息 + 目标端口 + 目标IP】
  	↓
  	↓  根据目标地址，查询路由表转发next hoop
  	↓
  - 路由器1: 接收 + 拆包 + 转发
  	↓
  	↓  根据目标地址，查询路由表转发next hoop
  	↓
     ...
  - 路由器n: 
  	↓
  	↓  转发next hoop
  	↓
  - 目标主机: 找到端口，交给应用进程
  	
  ```

- **网络层协议**
  - IP 协议：IPv4 / IPv6
  - ![image-20250314134951027](/Users/yueyangdong/Library/Application Support/typora-user-images/image-20250314134951027.png)
    - 协议protocol = 对等层的数据传输规则
    - IP地址就是 IP 协议规定的目标地址和源地址的表现形式
    - IP Message: 【 (Header) + 数据部分（01010...）】
      - Header：
        - 源地址：**Source Address**，用 IP 地址 + Port表示
        - 目标地址：**Destination Address**，用 IP 地址 + Port表示
- 套接字编程

## 传输层

### Socket 套接字

每一个语言，C、C++、Java 都会提供 Socket API，我们在项目中也会大量使用Socket API完成 C/S 通信

- IP协议约定，通过【 目标IP + 目标端口】号来指明 message 的 Teminal Address

- **Socket = 应用层 与 传输层（TCP/UDP） 之间的 API**，是下层为上层提供服务的接口

- 通过这个API，封装了IP协议所需要的参数，用于传输层（TCP/UDP）建立连接

  ```
  传输层：TCP/UDP 
      ⬆️ Socket API，封装传递所需的所有参数
  网络层：IP 协议        
  ```

  

### 传输层协议

![image-20250314141650929](/Users/yueyangdong/Library/Application Support/typora-user-images/image-20250314141650929.png)

### Java 建立 TCP 连接

- 客户端请求连接

- ```java
  private static final String SEVER_IP = "127.0.0.1";
  private static final int SERVER_PORT = 10087;
  
  Socket socket = new Socket(SEVER_IP, SERVER_PORT);
  ```

- 服务端确认连接

  ```java
  // 封装socket对象，监听指定端口
  ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
  while (true) {
      // 阻塞等待客户端连接，一旦连接，返回socket，表示开启专属通信通道
      Socket socket = serverSocket.accept();
  }
  ```

- 双方通信

- ```java
  // 利用 socket 读取 message
  InputStream in = socket.getInputStream(); 
  // 利用 socket 发送 message
  OutputStream out = socket.getOutputStream(); 
  ```

## 应用层

### C/S 模型

- **应用层（Application Layer）** 的网络架构模型，
  
  - 基于 **HTTP**（网页访问）、FTP（文件传输）、SMTP（邮件服务）协议
  - 由 **传输层** 基于 **TCP / UDP 协议**提供服务， 
  
- CS 模型 = Service (服务器) + 客户端 (Client)

  - 服务器必须**一直保持开启，不能关闭**，等待客户端连接
  - 客户端上线 --> 与服务器建立通信连接 --> 相互传输数据
    - 客户端1 --> Server --> 客户端2，客户端3 ...
    - 客户端 <---> Server

  ### Request & Response

  - Request

    - 请求行、请求头、请求体

    ```http
    POST /login HTTP/1.1
    Host: www.example.com
    Content-Type: application/x-www-form-urlencoded
    Content-Length: 29
    
    username=admin&password=123456
    ```

  - Response

    - 响应行、响应头、响应体

    ```http
    HTTP/1.1 200 OK
    Content-Type: text/html
    Content-Length: 1024
    
    <html>
        <body>
            <h1>Welcome to HTTP!</h1>
        </body>
    \</html>
    ```
  
  ### 域名系统 DNS
  
  - 域名（www.gooogle.com） = IP地址(142.250.190.46) 的 映射
  
  - DNS 
    - C / S 架构
      - DNS 客户端（DNS Resolver，解析器）
      - DNS 服务器：学校里、企业里、运营商提供，树状结构，不断向上级服务器查询
  - 流程
    - **发送目标域名 → DNS 服务器解析 → 返回目标 IP → 计算机使用 IP 访问**

​	

  

  

  





























