package com.qianmi.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.qianmi.domain.User;

/**
 * 用户仓库
 * Created by aqlu on 15/8/21.
 */
public interface UserRepository extends ElasticsearchRepository<User, String> {
}
