package com.hone.pc.backend.service;

import com.hone.dao.HoApplyWithdrawDao;
import com.hone.pc.backend.repo.ApplyWithDrawListRepo;
import com.hone.system.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/7/12.
 */

@Transactional
@Service
public class ApplayWithDrawService {

    @Autowired
    private HoApplyWithdrawDao hoApplyWithdrawDao;


    /**
     * 申请提现列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult=new JsonResult();

        List<ApplyWithDrawListRepo> repoList=hoApplyWithdrawDao.listForBackend();

        jsonResult.getData().put("pageData",repoList);
        jsonResult.globalSuccess();
        return jsonResult;
    }



}
