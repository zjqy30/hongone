package com.hone.dao;

import com.hone.applet.repo.HoUserStarSelfInfo;
import com.hone.entity.HoUserBasic;
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
public interface HoUserBasicDao extends TkBaseMapper<HoUserBasic> {


    int updateLogin(HoUserBasic hoUserBasic);

    HoUserStarSelfInfo starSelfInfo(@Param("userId") String userId,@Param("openId") String openId);

    List<InviteUserListRepo> inviteUserListBackend(@Param("marketerId") String marketerId);
}