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

    /**
     *
     * @param phoneNo
     * @param code
     * @param type  1 官网联系 2 小程序  3 PC
     * @return
     */
    int  verifyCode(@Param("phoneNo") String phoneNo, @Param("code")String code,@Param("type") String type);

    int delByPhoneNo(@Param("phoneNo") String phoneNo, @Param("type") String type);
}
