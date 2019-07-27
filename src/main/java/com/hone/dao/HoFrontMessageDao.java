package com.hone.dao;

import com.hone.entity.HoFrontMessage;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author lijia
 * Date  2019-07-24
 */
@Mapper
public interface HoFrontMessageDao extends TkBaseMapper<HoFrontMessage> {


    List<HoFrontMessage> listForApi();
}