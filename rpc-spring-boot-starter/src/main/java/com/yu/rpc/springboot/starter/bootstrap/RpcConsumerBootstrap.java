package com.yu.rpc.springboot.starter.bootstrap;

import com.yu.rpc.proxy.ServiceProxyFactory;
import com.yu.rpc.springboot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * Rpc 服务消费者启动
 */
@Slf4j
public class RpcConsumerBootstrap implements BeanPostProcessor {

    /**
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        // 遍历对象的所有属性
        Field[] declareFields = beanClass.getDeclaredFields();
        for (Field field : declareFields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if(rpcReference != null){
                // 为属性生成代理对象
                Class<?> interfaceClass = rpcReference.interfaceClass();
                if (interfaceClass == void.class){
                    interfaceClass = field.getType();
                }
                field.setAccessible(true);
                Object proxyObject = ServiceProxyFactory.getProxy(interfaceClass);
                try {
                    field.set(bean, proxyObject);
                    field.setAccessible(true);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("为字段注入对象失败", e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
