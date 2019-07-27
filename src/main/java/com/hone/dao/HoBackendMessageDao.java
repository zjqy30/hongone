package com.hone.dao;

import com.hone.entity.HoBackendMessage;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-07-20
 */
@Mapper
public interface HoBackendMessageDao extends TkBaseMapper<HoBackendMessage> {


    List<HoBackendMessage> findAll();

    int deleteByObjectId(@Param("objectId") String objectId);
}