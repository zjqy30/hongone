package com.hone.dao;

import com.hone.entity.HoUserSeller;
import com.hone.entity.TkBaseMapper;
import com.hone.pc.backend.repo.SellerUserInfoRepo;
import com.hone.pc.backend.repo.SellerUserListRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoUserSellerDao extends TkBaseMapper<HoUserSeller> {


    List<SellerUserInfoRepo> sellerApproveList();

    List<SellerUserListRepo> listBySellerBackend(@Param("industry") String industry,@Param("sex") String sex,@Param("wxName") String wxName, @Param("orderBy") String orderBy);

    SellerUserInfoRepo sellerInfoBackend(@Param("userId") String appletUserId);

    String findIndustry(@Param("userId")String id);
}