package com.fast.spider.dao;

import com.fast.spider.icama.PresticideDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author wanghao
 * @Description
 * @date 2018-05-07 14:41
 */
public interface PresticideRepo extends MongoRepository<PresticideDetail, String> {

    Page<PresticideDetail> findAllByCertificateCodeLike(Pageable pageable, String certificateCode);
}
