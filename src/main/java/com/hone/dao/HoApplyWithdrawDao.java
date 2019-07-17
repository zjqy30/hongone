package com.hone.dao;

import com.hone.applet.repo.HoReceiverListRepo;
import com.hone.entity.HoApplyWithdraw;
import com.hone.entity.TkBaseMapper;
import com.hone.pc.backend.repo.ApplyWithDrawListRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoApplyWithdrawDao extends TkBaseMapper<HoApplyWithdraw> {


    List<ApplyWithDrawListRepo> listForBackend();

    List<HoReceiverListRepo> receiverList(@Param("userId") String userId);
}