package com.hone.dao;

import com.hone.entity.HoWxFormid;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoWxFormidDao extends TkBaseMapper<HoWxFormid> {


    HoWxFormid findOneByOpenId(@Param("openId") String openId);
}