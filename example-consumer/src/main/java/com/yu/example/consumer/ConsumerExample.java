package com.yu.example.consumer;

import com.yu.example.common.model.User;
import com.yu.example.common.service.UserService;
import com.yu.rpc.config.RpcConfig;
import com.yu.rpc.proxy.ServiceProxyFactory;
import com.yu.rpc.utils.ConfigUtils;

public class ConsumerExample {

    public static void main(String[] args) {
//        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpcConfig);
        // 获取代理
        UserService userService = ServiceProxyFactory.getMockProxy(UserService.class);
        User user = new User();
        user.setName("lin");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null){
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        long number = userService.getNumber();
        System.out.println(number);
    }
}
