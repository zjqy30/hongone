package com.hone.service;

import com.hone.dao.HoSmsRecordsDao;
import com.hone.entity.HoSmsRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lijia on 2019/5/22.
 */

@Service
public class HoSmsRecordsService {

    @Autowired
    private HoSmsRecordsDao hoSmsRecordsDao;

    public void save(HoSmsRecords smsRecords) {
        hoSmsRecordsDao.insert(smsRecords);
    }

    public void delByPhoneNo(String phoneNo) {
        hoSmsRecordsDao.delByPhoneNo(phoneNo);
    }
}
