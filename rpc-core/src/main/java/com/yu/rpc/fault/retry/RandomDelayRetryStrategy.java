package com.yu.rpc.fault.retry;

import com.github.rholder.retry.*;
import com.yu.rpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 随机延迟 - 重试策略
 */
@Slf4j
public class RandomDelayRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class)
                // 随机等待策略，最小等待时间1秒，最大等待时间5秒
                .withWaitStrategy(WaitStrategies.randomWait(1L, TimeUnit.SECONDS, 5L, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试次数 {}, 距离第一次重试的延迟 {} 毫秒", attempt.getAttemptNumber()-1, attempt.getDelaySinceFirstAttempt());
                    }
                })
                .build();

        return retryer.call(callable);
    }
}
