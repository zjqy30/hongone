package com.hone.dao;

import com.hone.entity.HoSystemUser;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoSystemUserDao extends TkBaseMapper<HoSystemUser> {


    HoSystemUser findByUserNameAndPass(@Param("userName") String userName,@Param("passWord") String passWord);
}