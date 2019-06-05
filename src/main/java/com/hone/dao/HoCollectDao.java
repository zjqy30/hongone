package com.hone.dao;

import com.hone.entity.HoCollect;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoCollectDao extends TkBaseMapper<HoCollect> {


    List<HoCollect> listForApi(@Param("userId") String userId);
}