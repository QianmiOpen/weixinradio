package com.qianmi.service;

import com.qianmi.domain.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Http服务
 *
 * Created by aqlu on 15/8/21.
 */
@Component
public class HttpService {

    @Autowired
    private RestTemplate restTemplate;

    private static Logger logger = LoggerFactory.getLogger(HttpService.class);

    public boolean ping(Resource resource){
        try {
            ResponseEntity<String> response = invoke(resource);
            if (HttpStatus.OK.equals(response.getStatusCode())) {
                return true;
            }
        }catch (Exception ex){
            logger.info("ping {} failed! exMessage:{}", resource.getUrl(), ex.getMessage());
        }
        return false;
    }

    public ResponseEntity<String>  invoke(Resource resource) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(resource.getBody(), headers);

        return restTemplate.exchange(resource.getUrl(), resource.getMethod(), entity, String.class, resource.getParams());
    }

}
