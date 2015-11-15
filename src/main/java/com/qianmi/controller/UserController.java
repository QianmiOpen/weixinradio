package com.qianmi.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import com.qianmi.AppConstants;
import com.qianmi.domain.User;
import com.qianmi.service.UserService;
import com.qianmi.weixin.WeixinComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 用户中心
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private WeixinComponent weixinComponent;

    @Autowired
    private UserService userService;

    /**
     * 微信绑定
     * @param session
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/bind", method = RequestMethod.GET)
    public String bind(HttpSession session, Model model) throws UnsupportedEncodingException {
        String username = (String) session.getAttribute("user");
        if (StringUtils.isNoneEmpty(username)) {
            // Cache 缓存二维码时间,防止恶意刷
            String qrUrl = weixinComponent.makeQrUrl(username.replace("of", ""));
            model.addAttribute("qrUrl", qrUrl);
            return "user-bind";
        }
        return "/error";
    }

    @RequestMapping(value = "/unbind", method = RequestMethod.GET)
    public String unbind(HttpSession session) {
        String username = (String) session.getAttribute("user");
        User user = userService.get(username);
        user.setWxOpenId("");
        userService.save(user);
        return "redirect:/user/bind";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = AppConstants.MEDIA_TYPE_JSON)
    @ResponseBody
    public User get(@PathVariable("id") String id) {
        return userService.get(id);
    }

    @RequestMapping(value = "/{id}", method = { RequestMethod.POST,
            RequestMethod.PUT }, produces = AppConstants.MEDIA_TYPE_JSON)
    public String save(HttpSession session, @RequestParam("maintainPeriodMinute") int maintainPeriodMinute,
            @PathVariable("id") String id) {
        String loginId = (String) session.getAttribute("user");

        User user = userService.get(loginId);
        if (loginId.equals(id)) {
            user.setMaintainPeriod(maintainPeriodMinute * 60);
            userService.save(user);
        }

        return "redirect:/user";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setting(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("user");

        User user = userService.get(loginId);

        model.addAttribute("userSetting", user);

        return "setting";
    }

}
