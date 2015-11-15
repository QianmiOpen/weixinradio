package com.qianmi.service;

import com.ctc.wstx.util.StringUtil;
import com.qianmi.AppConstants;
import com.qianmi.domain.EventStatus;
import com.qianmi.domain.User;
import com.qianmi.weixin.WeixinComponent;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.script.ScriptService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import com.qianmi.domain.Event;
import com.qianmi.repository.EventRepository;
import org.springframework.util.StringUtils;

/**
 * 事件Service
 * Created by aqlu on 15/8/21.
 */
@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private WeixinComponent weixinComponent;

    public Event getById(String id) {
        return eventRepository.findOne(id);
    }

    public Event save(Event event) {
        Event result = eventRepository.save(event);

        User user = userService.get(event.getWorkNo());

        // 发送告警
        weixinComponent.templateSend(user.getWxOpenId(), event.getContent(), event.getOccurredTime(), event.getId());
        return result;
    }

    public Page<Event> findByWorkNo(String workNo, Pageable pageable) {
        return eventRepository.findByWorkNo(workNo, pageable);
    }

    public Page<Event> search(String workNo, String conentKeyword, Pageable pageable) {
        QueryBuilder queryBuilder = StringUtils.hasText(workNo) ? QueryBuilders.matchQuery("workNo", workNo) : QueryBuilders.matchAllQuery();
        FilterBuilder filterBuilder = StringUtils.hasText(conentKeyword) ? FilterBuilders.termFilter("content", conentKeyword) : FilterBuilders.matchAllFilter();

        QueryBuilder filteredQueryBuilder = QueryBuilders.filteredQuery(queryBuilder, filterBuilder);

        return eventRepository.search(filteredQueryBuilder, pageable);
    }

    public void delete(String id) {
        eventRepository.delete(id);
    }

    public void confirmEvent(String id) {
        UpdateQuery updateQuery = new UpdateQueryBuilder().withIndexName(AppConstants.ES_INDEX_NAME).withType("event")
                .withId(id)
                .withUpdateRequest(
                        new UpdateRequest().script("ctx._source.status = status; ctx._source.confirmTime = new Date()", ScriptService.ScriptType.INLINE)
                                .addScriptParam("status", EventStatus.VERIFYER))
                .build();

        esTemplate.update(updateQuery);
    }
}
