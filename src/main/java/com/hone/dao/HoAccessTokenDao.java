package com.hone.dao;

import com.hone.entity.HoAccessToken;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author lijia
 * Date  2019-06-14
 */
@Mapper
public interface HoAccessTokenDao extends TkBaseMapper<HoAccessToken> {


    void deleteAll();

    HoAccessToken findLast();
}