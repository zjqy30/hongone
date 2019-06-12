package com.hone.dao;

import com.hone.entity.HoOfferTemplate;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoOfferTemplateDao extends TkBaseMapper<HoOfferTemplate> {


    List<HoOfferTemplate> listAll();
}