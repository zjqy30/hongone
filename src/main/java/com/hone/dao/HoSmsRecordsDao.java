package com.hone.dao;

import com.hone.entity.HoSmsRecords;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Lijia on 2019/5/20.
 */

@Mapper
public interface HoSmsRecordsDao extends TkBaseMapper<HoSmsRecords> {

    int  verifyCode(@Param("phoneNo") String phoneNo, @Param("code")String code);

    int delByPhoneNo(@Param("phoneNo") String phoneNo);
}
