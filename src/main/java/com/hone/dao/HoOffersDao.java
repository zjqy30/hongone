package com.hone.dao;

import com.hone.applet.repo.HoOfferInfoRepo;
import com.hone.applet.repo.HoOffersListRepo;
import com.hone.applet.repo.HoSellerOfferListRepo;
import com.hone.applet.repo.HoStarSnatchOfferListRepo;
import com.hone.entity.HoOffers;
import com.hone.entity.TkBaseMapper;
import com.hone.pc.backend.repo.OfferListRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoOffersDao extends TkBaseMapper<HoOffers> {



    List<HoOffersListRepo> listForApiNoTag();

    List<HoOffersListRepo> listForApiTag(@Param("platIdsList") List<String> platIdsList, @Param("orderBy") String orderBy, @Param("sex") String sex);

    HoOfferInfoRepo detailsById(@Param("id") String id);

    List<HoStarSnatchOfferListRepo> starSnatchList(@Param("starUserId") String starUserId,@Param("type") String type);

    List<HoSellerOfferListRepo> sellerOfferList(@Param("userId") String userId,@Param("type") String type);

    List<OfferListRepo> listForBackend(@Param("type") String type,@Param("wxName") String wxName);

    List<OfferListRepo> listForRefund();
}