package com.hone.dao;

import com.hone.applet.repo.HoOfferInfoRepo;
import com.hone.applet.repo.HoOffersListRepo;
import com.hone.entity.HoOffers;
import com.hone.entity.TkBaseMapper;
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

    List<HoOffersListRepo> listForApiTag(@Param("tagList") List<String> tagList);

    HoOfferInfoRepo detailsById(@Param("id") String id);
}