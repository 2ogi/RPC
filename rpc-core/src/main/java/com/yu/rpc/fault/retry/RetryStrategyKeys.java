package com.yu.rpc.fault.retry;

/**
 * 重试策略键名常量
 */
public interface RetryStrategyKeys {

    // 不重试
    String NO = "no";

    // 固定时间间隔
    String FIXED_INTERVAL = "fixedInterval";

    // 随机延迟
    String RANDOM_DELAY = "randomDelay";

    // 指数退避
    String EXPONENTIAL_BACKOFF = "exponentialBackoff";
}
