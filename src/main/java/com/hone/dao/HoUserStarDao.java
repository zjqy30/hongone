package com.hone.dao;

import com.hone.applet.repo.HoUserStarListRepo;
import com.hone.entity.HoUserStar;
import com.hone.entity.TkBaseMapper;
import com.hone.pc.backend.repo.StarUserInfoRepo;
import com.hone.pc.backend.repo.StarUserListRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Mapper
public interface HoUserStarDao extends TkBaseMapper<HoUserStar> {


    List<HoUserStarListRepo> listByStar(@Param("platformId") String platType, @Param("tagList") List<String> tag);

    List<StarUserListRepo> listByStarBackend(@Param("platName")String platName, @Param("sex")String sex, @Param("wxName")String wxName, @Param("orderBy")String orderBy);

    StarUserInfoRepo starInfoBackend(@Param("userId") String userId);

    List<StarUserInfoRepo> starApproveList();

    String findPlatName(@Param("userId") String id);
}