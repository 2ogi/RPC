package com.yu.examplespringbootconsumer;

import com.yu.example.common.model.User;
import com.yu.example.common.service.UserService;
import com.yu.rpc.springboot.starter.annotation.RpcReference;
import com.yu.rpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("2ogi");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
