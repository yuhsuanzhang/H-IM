## H-IM(分布式IM聊天服务)

1.基于netty通信,netty消息序列化使用protobuf;

2.使用zookeeper作为注册中心进行IM服务端的注册和管理;

3.redis作为缓存以及分布式锁;

4.kafka作为异步通信中间件;

5.持久化存储使用mysql
