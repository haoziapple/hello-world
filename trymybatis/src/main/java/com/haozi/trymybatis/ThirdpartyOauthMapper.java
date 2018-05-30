package com.haozi.trymybatis;

import org.apache.ibatis.annotations.Mapper;

/**
 * 第三方授权绑定表
 * Created by Administrator on 2018/5/24 0024.
 */
@Mapper
public interface ThirdpartyOauthMapper {
    ThirdpartyOauthDO item(int id);

    int updateRemarkById(ThirdpartyOauthDO thirdpartyOauthDO);
}
