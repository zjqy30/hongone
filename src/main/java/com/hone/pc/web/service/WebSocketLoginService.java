package com.hone.pc.web.service;

import com.hone.dao.HoSocketLoginDao;
import com.hone.entity.HoSocketLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lijia on 2019/7/8.
 *
 */

@Service
@Transactional
public class WebSocketLoginService {

    @Autowired
    private HoSocketLoginDao hoSocketLoginDao;

    public HoSocketLogin findBySocketId(String socketId) {
        return hoSocketLoginDao.findBySocketId(socketId);
    }

    public void update(HoSocketLogin hoSocketLogin) {
        hoSocketLoginDao.updateByPrimaryKeySelective(hoSocketLogin);
    }
}
