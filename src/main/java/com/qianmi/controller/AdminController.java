package com.qianmi.controller;

import com.qianmi.service.UserService;
import com.qianmi.weixin.WeixinComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianmi.AppConstants;
import com.qianmi.domain.User;

/**
 * 管理员中心
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private WeixinComponent weixinComponent;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "admin";
    }

    @RequestMapping(value = "/renew_token", method = RequestMethod.GET)
    public @ResponseBody String renewToken() {
        weixinComponent.getAccessToken();
        String token = AppConstants.caches.get(AppConstants.WEIXIN_ACCESS_TOKEN);
        return "success, new token: " + token;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = AppConstants.MEDIA_TYPE_JSON)
    @ResponseBody
    public Page<User> list(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "30") int size) {
        return userService.list(new PageRequest(page, size));
    }

}
