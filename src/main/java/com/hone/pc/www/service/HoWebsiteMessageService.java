package com.hone.pc.www.service;

import com.hone.dao.HoWebsiteMessageDao;
import com.hone.entity.HoWebsiteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lijia on 2019/5/20.
 */

@Service
public class HoWebsiteMessageService {

    @Autowired
    private HoWebsiteMessageDao hoWebsiteMessageDao;

    public List<HoWebsiteMessage> list() {

       return hoWebsiteMessageDao.selectAll();

    }
}
