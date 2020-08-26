# newbie
rpc named newbie, learn from nettyRpc

### 一、整体架构
![newbie-rpc](docs/imgs/newbie-rpc.png)

### 二、newbie rpc组成
- 注册中心
    负责存储server地址，方便client连接时能获取到需要调用接口的url
- client
    订阅注册中心，获取server url地址
- server
    发布接口服务

### 三、FAQ    
