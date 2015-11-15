package com.qianmi.controller;

import com.qianmi.controller.form.EventForm;
import com.qianmi.domain.Event;
import com.qianmi.domain.EventStatus;
import com.qianmi.domain.User;
import com.qianmi.service.EventService;
import com.qianmi.service.UserService;
import com.qianmi.util.UUIDGen;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 事件Controller
 * Created by aqlu on 15/8/21.
 */
@Controller
@RequestMapping("/event")
public class EventController extends BaseController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(HttpSession session, Model model, @RequestParam(value = "contentKeyword", required = false) String contentKeyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "30") int size) {
        String user = (String) session.getAttribute("user");
        Page<Event> list = eventService.search(user, contentKeyword, new PageRequest(page, size));
        logger.debug("list: {}", list);
        model.addAttribute("list", list);
        model.addAttribute("contentKeyword", contentKeyword);
        return "event";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable("id") String id) {
        eventService.delete(id);
        return "success";
    }

    @RequestMapping(value = "/{id}/confirm", method = {RequestMethod.POST, RequestMethod.GET})
    public String confirm(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        eventService.confirmEvent(id);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/event/show";
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(@RequestParam("id") String id, Model model) {
        logger.debug("param id: {}", id);
        Event event = eventService.getById(id);
        logger.debug("get event: {}", event);
        model.addAttribute("event", event);
        return "event-show"; // mobile
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "事件接口", httpMethod = "POST", notes = "用户自定义事件", response = Event.class)
    @ResponseBody
    public Event add(@RequestBody EventForm eventForm) {
        Event result = new Event();
        String workNo = eventForm.getWorkNo();
        User user = userService.get(workNo);
        logger.debug("user:{}", user);
        if (null != user) {
            Event event = new Event(UUIDGen.systemUuid(), eventForm.getOccurredTime(), null, eventForm.getWorkNo(),
                    eventForm.getContent(), EventStatus.INITIALIZATION, eventForm.getResourceId());
            result = eventService.save(event);
        }

        return result;
    }

}
