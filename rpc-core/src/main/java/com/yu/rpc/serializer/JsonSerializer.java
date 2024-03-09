package com.yu.rpc.serializer;

import com.alibaba.fastjson.JSON;
import com.yu.rpc.model.RpcRequest;
import com.yu.rpc.model.RpcResponse;

import java.io.IOException;

/**
 * JSON 序列化器
 */
/**
 * 快速 JSON 序列化工厂
 *
 * @author cong
 * @date 2024/03/07
 */
public class JsonSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T t) {
        String jsonStr = JSON.toJSONString(t);
        return jsonStr.getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data),clazz);
    }

}
