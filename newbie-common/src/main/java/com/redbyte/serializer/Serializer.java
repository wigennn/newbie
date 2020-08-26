package com.redbyte.serializer;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
public abstract class Serializer {

    public abstract <T> byte[] serialize(T obj);

    public abstract Object deserialize(byte[] bytes, Class<?> clazz);
}
