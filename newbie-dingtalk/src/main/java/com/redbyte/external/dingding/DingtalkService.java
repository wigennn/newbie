package com.redbyte.external.dingding;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@Component
public class DingtalkService {

    @Value("${dingtalk.appKey}")
    private String appKey = "dingusud5zvghtf1lobl";

    @Value("${dingtalk.appSecret}")
    private String appSecret = "SuJRGqSKeW0AMoDEdCKAzNaBL_TFJ1-JPt4wXAG3NC2xTvLC2BkfMSfPeBJc2bxp";

    /**
     * 获取accesstoken
     */
    public String getAccesstoken() throws Exception {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setCorpid(appKey);
        request.setCorpsecret(appSecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        return response != null ? response.getAccessToken() : null;
    }

    /**
     * 获取审批详情
     */


}
