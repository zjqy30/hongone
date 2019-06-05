package com.hone.dao;

import com.hone.entity.HoOfferTag;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoOfferTagDao extends TkBaseMapper<HoOfferTag> {


    //根据offerId 获取拼接后的 tag
    String findStringByOfferId(@Param("offerId") String offerId);
}