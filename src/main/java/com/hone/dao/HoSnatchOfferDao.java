package com.hone.dao;

import com.hone.applet.repo.HoSnatchUserListRepo;
import com.hone.entity.HoSnatchOffer;
import com.hone.entity.TkBaseMapper;
import com.hone.pc.backend.repo.InviteUserListRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoSnatchOfferDao extends TkBaseMapper<HoSnatchOffer> {


    HoSnatchOffer findByOfferIdAndUserId(@Param("offerId") String offerId, @Param("userId")String userId);

    List<HoSnatchOffer> findByOfferId(@Param("offerId") String offerId);

    HoSnatchOffer findByOfferIdSelect(@Param("offerId")String offerId);

    List<InviteUserListRepo> snatchListForBackend(@Param("offerId") String offerId);

    int deleteByOfferId(@Param("offerId") String offerId);

    List<HoSnatchUserListRepo> snatchListForApi(@Param("offerId") String offerId);
}