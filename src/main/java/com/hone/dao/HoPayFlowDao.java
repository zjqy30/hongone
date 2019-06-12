package com.hone.dao;

import com.hone.entity.HoPayFlow;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoPayFlowDao extends TkBaseMapper<HoPayFlow> {


    HoPayFlow findByOfferIdAndType(@Param("offerId") String offerId, @Param("type") String type);

    HoPayFlow findByOutTradeNo(@Param("outTradeNo") String outTradeNo);
}