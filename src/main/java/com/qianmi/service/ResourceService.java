package com.qianmi.service;

import com.qianmi.AppConstants;
import com.qianmi.mq.TaskProducer;
import com.qianmi.domain.EventStatus;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.script.ScriptService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.qianmi.domain.Resource;
import com.qianmi.repository.ResourceRepository;
import org.springframework.util.StringUtils;

/**
 * 资源
 * Created by aqlu on 15/8/21.
 */
@Service
public class ResourceService {

    private static Logger logger = LoggerFactory.getLogger(ResourceService.class);

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private TaskProducer taskProducer;

    /**
     * 更新上次探测时间与状态
     * @param id 资源ID
     * @param dateTime 上次探测时间
     * @param status 探测状态
     */
    public void updateLastDetectTimeAndStatusById(String id, DateTime dateTime, Integer status) {
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withIndexName(AppConstants.ES_INDEX_NAME)
                .withType("resource")
                .withId(id)
                .withUpdateRequest(new UpdateRequest()
                        .script("ctx._source.lastDetectTime = dateTime; ctx._source.status = status", ScriptService.ScriptType.INLINE)
                        .addScriptParam("dateTime", dateTime)
                        .addScriptParam("status", status))
                .build();

        esTemplate.update(updateQuery);
    }

    public Resource save(Resource resource) {
        return resourceRepository.save(resource);
    }

    public void deleteById(String id) {
        resourceRepository.delete(id);
    }

    public Page<Resource> findByWorkNo(String workNo, Pageable pageable) {

        return resourceRepository.findByWorkNo(workNo, pageable);
    }

    public Page<Resource> search(String workNo, String keyWord, Pageable pageable) {

        QueryBuilder queryBuilder = StringUtils.hasText(workNo) ? QueryBuilders.matchQuery("workNo", workNo) : QueryBuilders.matchAllQuery();
        FilterBuilder filterBuilder = StringUtils.hasText(keyWord)
                ? FilterBuilders.orFilter(FilterBuilders.termFilter("url", keyWord), FilterBuilders.termFilter("method", keyWord))
                : FilterBuilders.matchAllFilter();

        QueryBuilder filteredQueryBuilder = QueryBuilders.filteredQuery(queryBuilder, filterBuilder);

        return resourceRepository.search(filteredQueryBuilder, pageable);
    }



    /**
     * 任务扫描
     */
    @Scheduled(fixedRate = 1000, initialDelay = 1000)
    public void scanTask() {

        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("status", 0);
        ScriptFilterBuilder filterBuilder = FilterBuilders
                .scriptFilter("doc['lastDetectTime'].value + doc['detectPeriod'].value*1000 < new Date().getTime()");
        FilteredQueryBuilder filteredQueryBuilder = QueryBuilders.filteredQuery(queryBuilder, filterBuilder);

        PageImpl<Resource> resources = (PageImpl<Resource>) resourceRepository.search(filteredQueryBuilder);

        logger.debug("[scanTask] scanning {} resources", resources.getTotalElements());

        resources.forEach(taskProducer::send);

    }

    /**
     * 定时解锁“可疑”与“故障”的十分钟前的服务
     */
    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void scanUnlock(){

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("status", 2))
                .should(QueryBuilders.matchQuery("status", 3));

        ScriptFilterBuilder filterBuilder = FilterBuilders
                .scriptFilter("doc['lastDetectTime'].value + doc['maintainPeriod'].value*1000 < new Date().getTime()");

        FilteredQueryBuilder filteredQueryBuilder = QueryBuilders.filteredQuery(queryBuilder, filterBuilder);

        PageImpl<Resource> resources = (PageImpl<Resource>) resourceRepository.search(filteredQueryBuilder);
        logger.debug("[scanUnlock] scanning {} resources", resources.getTotalElements());

        resources.forEach(consumer -> {
            UpdateResponse response = esTemplate.update(new UpdateQueryBuilder()
                    .withIndexName(AppConstants.ES_INDEX_NAME)
                    .withType("resource")
                    .withId(consumer.getId())
                    .withUpdateRequest(new UpdateRequest(AppConstants.ES_INDEX_NAME, "resource", consumer.getId())
                            .script("ctx._source.status = status", ScriptService.ScriptType.INLINE)
                            .addScriptParam("status", consumer.getStatus()))
                    .build());
            logger.debug("[scanUnlock] update {} resources", response.getId());
        });
    }

}
