package com.qianmi.controller;

import com.qianmi.AppConstants;
import com.qianmi.domain.User;
import com.qianmi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import com.qianmi.domain.Resource;
import com.qianmi.service.ResourceService;
import com.qianmi.util.UUIDGen;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件Controller
 * Created by aqlu on 15/8/21.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping( method = RequestMethod.GET)
    public String list(HttpSession session, Model model,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "30") int size) {
        String user = (String) session.getAttribute("user");

        Page<Resource> list = resourceService.search(user, keyword, new PageRequest(page, size));
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);

        return "resource";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "resource-add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String save(HttpSession session, Integer detectPeriod, String method, String url) {
        if (StringUtils.isNotEmpty(method) && StringUtils.isNotEmpty(url)) {
            logger.debug("detectPeriod: {}, method: {}, url: {}", detectPeriod, method, url);
            String username = (String) session.getAttribute("user");
            User user = userService.get(username);
            Resource resource = new Resource();
            resource.setWorkNo(username);
            resource.setId(UUIDGen.systemUuid());
            resource.setLastDetectTime(DateTime.now());
            resource.setBody("");
            resource.setDetectPeriod(detectPeriod);
            resource.setUrl(url);
            resource.setMethod(HttpMethod.valueOf(method.toUpperCase()));
            resource.setStatus(0);
            resource.setMaintainPeriod(user.getMaintainPeriod());
            resource = resourceService.save(resource);
        }
        return "redirect:/resource";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam("id") String id) {
        resourceService.deleteById(id);
        return "success";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> send(String url, String method) {
        Map<String, String> result = new HashMap<>();
        try {
            if (StringUtils.isNoneEmpty(method) && StringUtils.isNoneEmpty(url)) {
                if (StringUtils.endsWithIgnoreCase(method, "get")) {
                    result.put("result", restTemplate.getForObject(url, String.class));
                } else {
                    result.put("result", restTemplate.postForObject(url, "", String.class));
                }
            }
            return result;
        } catch (Exception e) {
            result.put("result", "error");
            return result;
        }
    }

    //
    // @RequestMapping(value="/scan", method = RequestMethod.GET)
    // @ResponseBody
    // public Iterable<Resource> scan(){
    // return resourceService.scanResource();
    // }
}
