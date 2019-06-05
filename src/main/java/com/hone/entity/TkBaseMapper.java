package com.hone.entity;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用mapper基类
 */
public interface TkBaseMapper<T> extends Mapper<T>,MySqlMapper<T> {

    public  T findUniqueByProperty(@Param(value="propertyName")String propertyName, @Param(value="value")Object value);


}
