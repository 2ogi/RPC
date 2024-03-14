package com.yu.example.provider;

import com.yu.example.common.service.UserService;
import com.yu.rpc.RpcApplication;
import com.yu.rpc.config.RegistryConfig;
import com.yu.rpc.config.RpcConfig;
import com.yu.rpc.model.ServiceMetaInfo;
import com.yu.rpc.registry.LocalRegistry;
import com.yu.rpc.registry.Registry;
import com.yu.rpc.registry.RegistryFactory;
import com.yu.rpc.server.HttpServer;
import com.yu.rpc.server.VertxHttpServer;
import com.yu.rpc.server.tcp.VertxTcpServer;

/**
 * 服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args){
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        // 提供服务
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
