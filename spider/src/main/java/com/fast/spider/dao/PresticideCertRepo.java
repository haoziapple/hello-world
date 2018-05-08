package com.fast.spider.dao;

import com.fast.spider.icama.PresticideCert;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-08 13:54
 */
public interface PresticideCertRepo extends MongoRepository<PresticideCert, String> {
}
