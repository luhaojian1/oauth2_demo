package com.oauth2.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.oauth2.demo.model.Oauth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    @Autowired
    private Oauth2Properties oauth2Properties;

    @GetMapping("/authorize")
    public String authorize() {
        String url = oauth2Properties.getAuthorizeUrl() +
                "?client_id=" + oauth2Properties.getClientId() +
                "&redirect_uri=" + oauth2Properties.getRedirectUrl();
        System.out.println(url);
        return "redirect:" + url;
    }


    @GetMapping("/oauth2/callback")
    public String callback(@RequestParam("code") String code) {
        // code换token
        String accessToken = getAccessToken(code);
        // token换userInfo
        String userInfo = getUserInfo(accessToken);
        return "redirect:/index";
    }

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        return "login success";
    }

    private String getAccessToken(String code) {
        String url = oauth2Properties.getAccessTokenUrl() +
                "?client_id=" + oauth2Properties.getClientId() +
                "&client_secret=" + oauth2Properties.getClientSecret() +
                "&code=" + code +
                "&grant_type=authorization_code";
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回json格式
        requestHeaders.add("accept", "application/json");
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // post 请求方式
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        String responseStr = response.getBody();

        // 解析响应json字符串
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        return jsonObject.getString("access_token");
    }

    private String getUserInfo(String accessToken) {
        String url = oauth2Properties.getUserInfoUrl();
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        // 指定响应返回json格式
        requestHeaders.add("accept", "application/json");
        // AccessToken放在请求头中
        requestHeaders.add("Authorization", "token " + accessToken);
        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // get请求方式
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(response.getBody());
        System.out.println(jsonObject);
        return response.getBody();
    }
}

