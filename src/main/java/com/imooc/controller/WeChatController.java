package com.imooc.controller;

import com.imooc.config.WeChatConfig;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.AbstractWxMpServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * @author 潇
 * @create 2019-06-28 20:05
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {
    @Autowired
    WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl){
        //配置
        //调用方法
        String url="http://xiaohat.natapp1.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        log.info("result={}", redirectUrl);
        return "redirect:"+redirectUrl;
    }

    @GetMapping("/userInfo")
    public String getUserInfo(@RequestParam("code")String code,
                            @RequestParam("state")String returnUrl){
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessTokentoken = wxMpService.oauth2getAccessToken(code);
            String openId = wxMpOAuth2AccessTokentoken.getOpenId();
            return "redirect:"+returnUrl+"?openid="+openId;
        } catch (WxErrorException e) {
            log.error("微信网页授权:{}", e);
            throw new SellException(ResultEnum.WX_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }

    }
}
