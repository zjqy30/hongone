package com.hone.pc.web.service;

import com.hone.dao.HoUserBasicDao;
import com.hone.entity.HoUserBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lijia on 2019/7/8.
 */

@Service
@Transactional
public class WebUserBasicService {

    @Autowired
    private HoUserBasicDao hoUserBasicDao;

    public HoUserBasic findByOpenId(String openId) {
        return hoUserBasicDao.findUniqueByProperty("open_id",openId);
    }
}
