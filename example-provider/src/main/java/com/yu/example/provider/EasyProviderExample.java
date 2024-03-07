package com.yu.example.provider;

import com.yu.example.common.service.UserService;
import com.yu.rpc.RpcApplication;
import com.yu.rpc.registry.LocalRegistry;
import com.yu.rpc.server.HttpServer;
import com.yu.rpc.server.VertxHttpServer;

/**
 * 服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args){
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
