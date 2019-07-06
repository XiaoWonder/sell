package com.imooc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author 潇
 * @create 2019-06-28 18:58
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeiXinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code")String code){
      log.info("进入auth方法");
      log.info("code={}", code);
      String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx915d7b09631af813&secret=d31e0dd82028d434de3b9f4c7c83b90c&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        log.info("result={}", result);
    }
}
