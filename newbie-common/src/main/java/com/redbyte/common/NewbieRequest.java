package com.redbyte.common;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@Data
public class NewbieRequest implements Serializable {
    private static final long serialVersionUID = -5036066084392621169L;

    private String requestId;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;

    private String version;
}
