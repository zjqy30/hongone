package com.hone.dao;

import com.hone.entity.HoDict;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-30
 */
@Mapper
public interface HoDictDao extends TkBaseMapper<HoDict> {


    HoDict selectByTypeAndValue(@Param("type") String label, @Param("value")  String value);

    List<HoDict> listByType(@Param("type") String type);
}