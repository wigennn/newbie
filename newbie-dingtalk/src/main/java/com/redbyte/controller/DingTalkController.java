package com.redbyte.controller;

import com.redbyte.external.dingding.DingtalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwq
 */
@RestController
public class DingTalkController {

    @Autowired
    private DingtalkService dingtalkService;

    @RequestMapping("/getAccessToken")
    public String getAccessToken() throws Exception {
        return dingtalkService.getAccesstoken();
    }
}
