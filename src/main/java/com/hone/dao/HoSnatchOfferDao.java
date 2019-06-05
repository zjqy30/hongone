package com.hone.dao;

import com.hone.entity.HoSnatchOffer;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoSnatchOfferDao extends TkBaseMapper<HoSnatchOffer> {


    HoSnatchOffer findByOfferIdAndUserId(@Param("offerId") String offerId, @Param("userId")String userId);
}