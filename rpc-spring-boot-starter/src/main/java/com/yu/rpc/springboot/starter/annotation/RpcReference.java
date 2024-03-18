package com.yu.rpc.springboot.starter.annotation;

import com.yu.rpc.constant.RpcConstant;
import com.yu.rpc.fault.retry.RetryStrategy;
import com.yu.rpc.fault.retry.RetryStrategyKeys;
import com.yu.rpc.fault.tolerant.TolerantStrategyKeys;
import com.yu.rpc.loadbalancer.LoadBalancerKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RpcReference {

    /**
     * 服务接口类
     * @return
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     * @return
     */
    String serviceVision() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡器
     * @return
     */
    String LoadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     * @return
     */
    String retryStrategy() default RetryStrategyKeys.NO;

    /**
     * 容错策略
     * @return
     */
    String tolerantStrategy() default TolerantStrategyKeys.FAIL_FAST;

    /**
     * 模拟调用
     * @return
     */
    boolean mock() default false;
}
