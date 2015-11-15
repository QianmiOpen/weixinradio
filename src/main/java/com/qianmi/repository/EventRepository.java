package com.qianmi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.qianmi.domain.Event;

/**
 * 事件仓库
 * Created by aqlu on 15/8/21.
 */
public interface EventRepository extends ElasticsearchRepository<Event, String> {

    Page<Event> findByWorkNo(String workNo, Pageable pageable);
}
