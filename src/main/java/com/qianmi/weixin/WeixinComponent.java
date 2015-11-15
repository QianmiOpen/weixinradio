package com.qianmi.weixin;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianmi.AppConstants;
import com.qianmi.weixin.template.SendData;
import com.qianmi.weixin.template.WarnTemplate;

@Component
public class WeixinComponent {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${weixin.base_url}")
    private String baseUrl;

    @Value("${weixin.mp_base_url}")
    private String mpBaseUrl;

    @Value("${weixin.appid}")
    private String appID;

    @Value("${weixin.appsecret}")
    private String appSecret;

    @Value("${weixin.callback_url}")
    private String callbackUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取和维护accessToken票据
     */
    @PostConstruct
    @Scheduled(fixedDelay = 7000000L, initialDelay = 10000L)
    public void buildAccessToken() {
        URI targetUrl= UriComponentsBuilder.fromUriString(baseUrl).path("/cgi-bin/token")
                .queryParam("grant_type", "client_credential")
                .queryParam("appid", appID)
                .queryParam("secret", appSecret)
                .build()
                .toUri();
        logger.debug("weixin, get access_token url: {}", targetUrl.toASCIIString());
        try {
            String result = restTemplate.getForObject(targetUrl, String.class);
            logger.debug("weixin, get access_token result: {}", result);

            JsonNode json = new ObjectMapper().readTree(result);
            if (json.has("access_token")) {
                logger.info("weixin, get access_token success");
                AppConstants.caches.put(AppConstants.WEIXIN_ACCESS_TOKEN, json.get("access_token").asText());
            } else {
                logger.error("weixin, get access_token failing");
            }
        } catch (SocketTimeoutException ste) {
            logger.warn("weixin, get access_token timeout exception", ste);
        } catch (IOException e) {
            logger.error("weixin, get access_token io exception", e);
        }
    }

    /**
     * 获取AccessToken
     * @return
     */
    public String getAccessToken() {
        return AppConstants.caches.get(AppConstants.WEIXIN_ACCESS_TOKEN);
    }

    /**
     * 创建二维码ticket
     * @param content
     */
    public String makeQrTicket(String content) {
        Map<String, String> sceneIdMap = new LinkedHashMap<>();
        // scene_str 必须 action_name=QR_LIMIT_SCENE,QR_LIMIT_STR_SCENE
        sceneIdMap.put("scene_id", content);

        Map<String, Map> sceneMap = new LinkedHashMap<>();
        sceneMap.put("scene", sceneIdMap);

        Map<String, Object> formData = new LinkedHashMap<>();
        formData.put("expire_seconds", AppConstants.WEIXIN_QR_EXPIRE_SECONDS);
        formData.put("action_name", "QR_SCENE");
        formData.put("action_info", sceneMap);

        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(new ObjectMapper().writeValueAsString(formData), headers);
            URI targetUrl = UriComponentsBuilder.fromUriString(baseUrl).path("cgi-bin/qrcode/create")
                    .queryParam("access_token", getAccessToken())
                    .build()
                    .toUri();
            logger.debug("weixin, 1. get qr ticket param: {}\n2. get qr targetUrl: {}\n3. requestEntity: {}",
                    formData, targetUrl, requestEntity);
            HttpEntity<String> response = restTemplate.postForEntity(targetUrl, requestEntity, String.class);
            logger.debug("weixin, get qr ticket response body: {}", response.getBody());
            JsonNode json = new ObjectMapper().readTree(response.getBody());
            if (json.has("ticket")) {
                logger.info("weixin, get qr ticket success");
                return json.get("ticket").asText();
            } else {
                logger.error("weixin, get qr ticket failing");
            }
        } catch (JsonProcessingException jpe) {
            logger.error("json process error", jpe);
        } catch (IOException e) {
            logger.error("weixin, get qr ticket io exception", e);
        }
        return null;
    }

    /**
     * 通过ticket和内容获取二维码URL
     * @param content
     * @return
     */
    public String makeQrUrl(String content) {
        logger.debug("qr content: {}", content);
        String ticket = makeQrTicket(content);
        logger.debug("qr ticket: {}", ticket);
        URI targetUrl = UriComponentsBuilder.fromUriString(mpBaseUrl).path("cgi-bin/showqrcode")
                .queryParam("ticket", ticket)
                .build()
                .toUri();
        logger.debug("qr url: {}", targetUrl);
        return targetUrl.toASCIIString();
    }

    /**
     * 发送告警消息
     * @param wxOpenId
     * @param content
     * @param occurredTime
     * @param eventId
     * @return
     */
    public String templateSend(String wxOpenId, String content, DateTime occurredTime, String eventId) {
        SendData sendData = new SendData();
        sendData.setTouser(wxOpenId);
        sendData.setTemplate_id("mzUEJLURu-cY7pRY0ehs8rztqnuaOYjgDfjzQfVjhXs");
        sendData.setUrl(callbackUrl + "/event/show?id=" + eventId);
        sendData.setTopcolor("#FF0000");

        WarnTemplate warnTemplate = new WarnTemplate();
        warnTemplate.getFirst().put("value", "云监控告警通知");
        warnTemplate.getContent().put("value", content);
        warnTemplate.getOccurtime().put("value", occurredTime.toString());
        sendData.setData(warnTemplate);

        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(new ObjectMapper().writeValueAsString(sendData), headers);
            URI targetUrl = UriComponentsBuilder.fromUriString(baseUrl).path("/cgi-bin/message/template/send")
                    .queryParam("access_token", getAccessToken())
                    .build()
                    .toUri();
//            String requestBody = new ObjectMapper().writeValueAsString(sendData);
            logger.debug("targetUrl: {}", targetUrl);
            logger.debug("requestBody: {}", requestEntity);
            String result = restTemplate.postForObject(targetUrl, requestEntity, String.class);
            logger.debug("template send result: {}", result);
        } catch (Exception e) {
            logger.error("error", e);
        }
        return "";
    }

}
