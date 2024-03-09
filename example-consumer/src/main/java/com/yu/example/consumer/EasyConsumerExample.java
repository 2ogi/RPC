package com.yu.example.consumer;

import com.yu.example.common.model.User;
import com.yu.example.common.service.UserService;
import com.yu.rpc.proxy.ServiceProxyFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 简易服务消费者示例
 */
@Slf4j
public class EasyConsumerExample {

    public static void main(String[] args){
//        // 静态代理
//        UserService userService = new UserServiceProxy();
        // 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("2ogi");
        // 调用
        User newUser = userService.getUser(user);
        if(newUser != null){
            System.out.println(newUser.getName());
        }else {
            System.out.println("user == null");
        }
    }
}
