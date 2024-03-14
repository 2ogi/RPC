package com.yu.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.yu.rpc.RpcApplication;
import com.yu.rpc.config.RpcConfig;
import com.yu.rpc.constant.RpcConstant;
import com.yu.rpc.model.RpcRequest;
import com.yu.rpc.model.RpcResponse;
import com.yu.rpc.model.ServiceMetaInfo;
import com.yu.rpc.protocol.*;
import com.yu.rpc.registry.Registry;
import com.yu.rpc.registry.RegistryFactory;
import com.yu.rpc.serializer.JdkSerializer;
import com.yu.rpc.serializer.Serializer;
import com.yu.rpc.serializer.SerializerFactory;
import com.yu.rpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)){
            throw new RuntimeException("暂无服务地址");
        }
        // 暂取第一个
        ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

        // 发送 TCP 请求
        RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
        return rpcResponse.getData();
    }
}
