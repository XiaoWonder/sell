package com.imooc.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 潇
 * @create 2019-07-01 11:14
 */
@Component
public class WeChatPayConfig {

    @Autowired
    WeChatAccountConfig weChatAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayService(){
        BestPayServiceImpl bestPayService=new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config=new WxPayH5Config();
        wxPayH5Config.setAppId(weChatAccountConfig.getMpAppid());
        wxPayH5Config.setAppSecret(weChatAccountConfig.getMpAppSecret());
        wxPayH5Config.setKeyPath(weChatAccountConfig.getKeyPath());
        wxPayH5Config.setMchId(weChatAccountConfig.getMchID());
        wxPayH5Config.setMchKey(weChatAccountConfig.getMchKey());
        wxPayH5Config.setNotifyUrl(weChatAccountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}
