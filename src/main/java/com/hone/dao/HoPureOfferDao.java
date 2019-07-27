package com.hone.dao;

import com.hone.entity.HoPureOffer;
import com.hone.entity.TkBaseMapper;
import com.hone.pc.web.repo.PureOfferListRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-07-08
 */
@Mapper
public interface HoPureOfferDao extends TkBaseMapper<HoPureOffer> {


    List<PureOfferListRepo> listForWeb(@Param("productType") String productType, @Param("dateType") String dateType);

    List<PureOfferListRepo> selfListForWeb(@Param("userId") String userId, @Param("keys") String keys,@Param("orderBy") String orderBy);

    List<PureOfferListRepo> listForBackend(@Param("title") String title,@Param("status") String status);

    List<PureOfferListRepo> snatchListForWeb(@Param("userId") String userId, @Param("keys") String keys,@Param("orderBy") String orderBy);
}