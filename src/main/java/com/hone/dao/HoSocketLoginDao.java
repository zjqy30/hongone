package com.hone.dao;

import com.hone.entity.HoSocketLogin;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author lijia
 * Date  2019-07-08
 */
@Mapper
public interface HoSocketLoginDao extends TkBaseMapper<HoSocketLogin> {


    HoSocketLogin findBySocketId(@Param("socketId") String socketId);
}