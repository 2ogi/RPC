package com.yu.rpc.fault.tolerant;

import cn.hutool.core.collection.CollUtil;
import com.yu.rpc.loadbalancer.LoadBalancer;
import com.yu.rpc.loadbalancer.LoadBalancerFactory;
import com.yu.rpc.loadbalancer.LoadBalancerKeys;
import com.yu.rpc.model.RpcRequest;
import com.yu.rpc.model.RpcResponse;
import com.yu.rpc.model.ServiceMetaInfo;
import com.yu.rpc.server.tcp.VertxTcpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * （故障转移）转移到其他服务节点 - 容错机制
 */
public class FailOverTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) throws ExecutionException, InterruptedException {
        List<ServiceMetaInfo> serviceMetaInfoList = (List<ServiceMetaInfo>) context.get("serviceList");
        ServiceMetaInfo errorService = (ServiceMetaInfo) context.get("errorService");
        RpcRequest rpcRequest = (RpcRequest) context.get("rpcRequest");
        // 从服务列表中移除错误服务
        serviceMetaInfoList.remove(errorService);
        // 重新调用其他服务
        if (CollUtil.isNotEmpty(serviceMetaInfoList)) {
            // 重新调用其他服务
            // 负载均衡
            // 将调用方法名（请求路径）作为负载均衡参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(LoadBalancerKeys.ROUND_ROBIN);
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
            return VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
        }
        return null;
    }
}
