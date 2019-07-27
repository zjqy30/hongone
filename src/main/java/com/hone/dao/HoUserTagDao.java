package com.hone.dao;

import com.hone.entity.HoUserTag;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoUserTagDao extends TkBaseMapper<HoUserTag> {


    List<HoUserTag> findListByUserId(@Param("userId") String userId);

    int deleteByUserId(String appletUserId);
}