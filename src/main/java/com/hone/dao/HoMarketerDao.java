package com.hone.dao;

import com.hone.entity.HoMarketer;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoMarketerDao extends TkBaseMapper<HoMarketer> {


    List<HoMarketer> findListBackend();

    String countInviteNums(@Param("marketerId") String id);
}