package com.hone.dao;

import com.hone.entity.HoBanners;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoBannersDao extends TkBaseMapper<HoBanners> {


    List<HoBanners> findByPages(@Param("pages") String pages);
}