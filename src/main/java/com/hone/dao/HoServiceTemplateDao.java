package com.hone.dao;

import com.hone.entity.HoServiceTemplate;
import com.hone.entity.TkBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lijia
 * Date  2019-07-09
 */
@Mapper
public interface HoServiceTemplateDao extends TkBaseMapper<HoServiceTemplate> {


    List<HoServiceTemplate> listAll();

    List<HoServiceTemplate> findByUserId(@Param("userId") String userId);
}