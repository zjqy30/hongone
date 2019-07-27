package com.hone.dao;

import com.hone.entity.HoSnatchPureOffer;
import com.hone.entity.TkBaseMapper;
import com.hone.pc.backend.repo.SnatchPureOfferListRepo;
import com.hone.pc.web.repo.PureOfferListRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-07-25
 */
@Mapper
public interface HoSnatchPureOfferDao extends TkBaseMapper<HoSnatchPureOffer> {


    HoSnatchPureOffer findByOfferIdAndUserId(@Param("offerId") String offerId, @Param("userId")String loginUserId);

    List<SnatchPureOfferListRepo> listForBackend();
}