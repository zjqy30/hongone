package com.hone.dao;

import com.hone.entity.HoApplyRefund;
import com.hone.entity.TkBaseMapper;
import com.hone.pc.backend.repo.OfferListRepo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author lijia
 * Date  2019-07-12
 */
@Mapper
public interface HoApplyRefundDao extends TkBaseMapper<HoApplyRefund> {


    List<OfferListRepo> listForBackend();
}