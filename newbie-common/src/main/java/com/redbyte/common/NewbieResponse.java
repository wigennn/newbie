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
public class NewbieResponse implements Serializable {
    private static final long serialVersionUID = -943268330425752237L;

    private String requestId;

    private String errorMsg;

    private Object data;
}
