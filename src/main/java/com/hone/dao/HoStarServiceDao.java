package com.hone.dao;

import com.hone.entity.HoStarService;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author lijia
 * Date  2019-07-09
 */
@Mapper
public interface HoStarServiceDao extends TkBaseMapper<HoStarService> {


    int deleteByUserId(@Param("userId") String userId);
}