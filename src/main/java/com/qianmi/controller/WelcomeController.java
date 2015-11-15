package com.qianmi.controller;

import com.qianmi.domain.User;
import com.qianmi.service.UserService;
import com.qianmi.weixin.WeixinComponent;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 入口
 * Created by li on 15/8/21.
 */
@Controller
public class WelcomeController extends BaseController {

    @Autowired
    private UserService userService;

    @Value("${root.username}")
    private String root;

    @RequestMapping("/") public String index() {
        return "welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login_index() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, Model model, HttpSession session) {
        if (StringUtils.isNoneBlank(username) && StringUtils.isNoneBlank(password)) {
            username = username.toLowerCase(); // ignore
            if (userService.authenticate(username, password)) {
                session.setAttribute("user", username);
                logger.debug("username:{}, rootname:{}", username, root);
                if (StringUtils.equalsIgnoreCase(username, root)) {
                    return "redirect:/admin";
                } else {
                    User user = userService.createIfNotExists(username);
                    if (StringUtils.isEmpty(user.getWxOpenId())) {
                        return "redirect:/user/bind";
                    }
                    return "redirect:/user";
                }
            }
        }
        model.addAttribute("message", "账号密码错误");
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @Autowired
    private WeixinComponent weixinComponent;

    @RequestMapping(value = "/testSend", method = RequestMethod.GET)
    @ResponseBody public String testSend(HttpSession session) {
        String username = (String) session.getAttribute("user");
        User user = userService.get(username);
        weixinComponent.templateSend(user.getWxOpenId(), "测试", DateTime.now(), "111");
        return "test";
    }
}
