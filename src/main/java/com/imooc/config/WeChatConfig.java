package com.imooc.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 潇
 * @create 2019-06-28 20:11
 */
@Component
public class WeChatConfig {
    @Autowired
    WeChatAccountConfig weChatAccountConfig;

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService=new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage=new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(weChatAccountConfig.getMpAppid());
        wxMpConfigStorage.setSecret(weChatAccountConfig.getMpAppSecret());
        return wxMpConfigStorage;
    }
}
