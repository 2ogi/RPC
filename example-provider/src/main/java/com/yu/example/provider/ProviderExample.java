package com.yu.example.provider;

import com.yu.example.common.service.UserService;
import com.yu.rpc.RpcApplication;
import com.yu.rpc.bootstrap.ProviderBootstrap;
import com.yu.rpc.config.RegistryConfig;
import com.yu.rpc.config.RpcConfig;
import com.yu.rpc.model.ServiceMetaInfo;
import com.yu.rpc.model.ServiceRegisterInfo;
import com.yu.rpc.registry.LocalRegistry;
import com.yu.rpc.registry.Registry;
import com.yu.rpc.registry.RegistryFactory;
import com.yu.rpc.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务提供者示例
 */
public class ProviderExample {

    public static void main(String[] args){
        // 注册服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
