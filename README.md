# YU-RPC
## 项目介绍
本项目是一个基于Etcd + Vert.x + 自定义协议的RPC框架。开发者引入Spring Boot Starter，就可以通过注解和配置文件的方式快速使用该框架调用远程服务，还支持通过SPI机制扩展序列化器、负载均衡器、重试和容错策略等。

## 快速使用
### 1. 注解启动
#### 1.1 对于服务提供方
具体代码参考 example-springboot-provider <br>
在服务提供方启动类通过 @EnableRpc 初始化并启动 RPC 框架 <br>
```java
@SpringBootApplication
@EnableRpc
public class ExampleSpringbootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringbootProviderApplication.class, args);
    }

}
```
在对于服务实现类通过 @RpcService 进行服务注册
```java
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名" + user.getName());
        return user;
    }
}
```
#### 1.2 对于服务消费方
具体代码参考 example-springboot-consumer <br>
在服务消费方启动类通过 @EnableRpc(needServer = false) 初始化并启动 RPC 框架 <br>
```java
@SpringBootApplication
@EnableRpc(needServer = false)
public class ExampleSpringbootConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringbootConsumerApplication.class, args);
    }

}
```
通过 @RpcReference 调用远程服务
```java
@RpcReference
private UserService userService;
```
### 2. 自定义配置
在使用 RPC 框架时，可以通过配置文件的方式自定义 RPC 框架配置
具体地，在 /src/main/resources 中创建 application.properties 文件，内容如下：
```properties
# 名称
rpc.name=yurpc
# 版本号，默认为 1.0
rpc.version=2.0
# 主机，默认为 localhost
rpc.serverHost=localhost
# 端口，默认为 8080
rpc.serverPort=8087
# 注册中心，默认为etcd。可选范围[etcd, zookeeper]
rpc.registryConfig.registry=etcd
# 是否使用 mock，默认为 false
rpc.mock=false
# 序列化器，默认为 jdk。可选范围[jdk, json, kryo, hessian]
rpc.serializer=jdk
# 负载均衡,默认为 roundRobin。可选范围为[roundRobin, random, consistentHash]
rpc.loadBalancer=roundRobin
# 重试策略，默认为 no。可选范围为[no, fixedInterval, randomDelay, exponentialBackoff]
rpc.retryStrategy=no
# 容错策略，默认为 failOver。可选范围为[failOver, failBack, failFast, failSafe]
rpc.tolerantStrategy=failOver
```