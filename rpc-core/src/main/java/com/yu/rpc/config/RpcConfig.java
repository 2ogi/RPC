package com.yu.rpc.config;

import com.yu.rpc.loadbalancer.LoadBalancerKeys;
import com.yu.rpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * Rpc框架配置
 */
@Data
public class RpcConfig {

    // 名称
    private String name = "yu-rpc";

    // 版本号
    private String version = "1.0";

    // 服务器主机名称
    private String serverHost = "localhost";

    // 服务器端口号
    private Integer serverPort = 8080;

    // 模拟调用
    private boolean mock = false;

    // 序列化器
    private String serializer = SerializerKeys.JDK;

    // 注册中心配置
    private RegistryConfig registryConfig = new RegistryConfig();

    // 负载均衡器
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;
}
