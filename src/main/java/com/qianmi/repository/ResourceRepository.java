package com.qianmi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.qianmi.domain.Resource;

/**
 * 资源仓库
 * Created by aqlu on 15/8/21.
 */
public interface ResourceRepository extends ElasticsearchRepository<Resource, String>{

    Page<Resource> findByWorkNo(String workNo, Pageable pageable);
}
