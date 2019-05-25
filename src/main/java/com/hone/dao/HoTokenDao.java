package com.hone.dao;

import com.hone.entity.HoSmsRecords;
import com.hone.entity.HoToken;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Lijia on 2019/5/20.
 */

@Mapper
public interface HoTokenDao extends TkBaseMapper<HoToken> {


    HoToken selectByToken(@Param("token") String token);
}
