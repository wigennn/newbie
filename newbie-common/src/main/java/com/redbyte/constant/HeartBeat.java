package com.redbyte.constant;

import com.redbyte.common.NewbieRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
public class HeartBeat {

    public static final int BEAT_INTERVAL = 30;
    public  static final int BEAT_TIMEOUT = BEAT_INTERVAL * 3;
    public static final String BEAT_ID = "BEAT_PING_PONG";

    public static NewbieRequest BEAT_REQUEST;

    static {
        BEAT_REQUEST = new NewbieRequest();
        BEAT_REQUEST.setRequestId(BEAT_ID);
    }
}
