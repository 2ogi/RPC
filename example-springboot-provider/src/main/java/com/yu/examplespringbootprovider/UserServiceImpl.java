package com.yu.examplespringbootprovider;

import com.yu.example.common.model.User;
import com.yu.example.common.service.UserService;
import com.yu.rpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名" + user.getName());
        return user;
    }
}
