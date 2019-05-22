package com.hone.entity;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用mapper基类
 */
public interface TkBaseMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
