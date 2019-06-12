package com.hone.dao;

import com.hone.entity.HoAccountCharge;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoAccountChargeDao extends TkBaseMapper<HoAccountCharge> {


    List<HoAccountCharge> listForApi(@Param("userId") String userId);
}