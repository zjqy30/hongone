package com.hone.pc.backend.service;

import com.hone.dao.HoDictDao;
import com.hone.entity.HoDict;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/14.
 */

@Service
@Transactional
public class DictService {

    @Autowired
    private HoDictDao hoDictDao;

    /**
     * 标签列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String dictType = params.get("dictType");
        ParamsUtil.checkParamIfNull(params, new String[]{"dictType"});

        List<HoDict> dictList = hoDictDao.listByType(dictType);

        jsonResult.getData().put("dictList", dictList);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 删除标签
     *
     * @param params
     * @return
     */
    public JsonResult del(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String id = params.get("id");
        ParamsUtil.checkParamIfNull(params, new String[]{"id"});

        HoDict hoDict = hoDictDao.selectByPrimaryKey(id);
        hoDict.setEnableFlag("0");
        hoDictDao.updateByPrimaryKeySelective(hoDict);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 新建标签
     *
     * @param params
     * @return
     * @throws Exception
     */
    public JsonResult create(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String dictType = params.get("dictType");
        String dictValue = params.get("dictValue");

        ParamsUtil.checkParamIfNull(params, new String[]{"dictValue", "dictType"});

        HoDict hoDictQ = hoDictDao.selectByTypeAndValue(dictType, dictValue);
        if (hoDictQ != null) {
            jsonResult.globalError("标签已存在");
            return jsonResult;
        }

        HoDict hoDict = new HoDict();
        hoDict.setDictType(dictType);
        hoDict.setDictValue(dictValue);
        hoDict.setPid("0");
        hoDict.setDictSort("99");
        hoDict.preInsert();
        hoDictDao.insert(hoDict);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 标签更换图片
     *
     * @param params
     * @return
     */
    public JsonResult updatePic(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String id = params.get("id");
        String pic = params.get("pic");

        ParamsUtil.checkParamIfNull(params, new String[]{"id", "pic"});

        HoDict hoDict = hoDictDao.selectByPrimaryKey(id);
        if (hoDict == null) {
            jsonResult.globalError("标签不存在");
            return jsonResult;
        }

        hoDict.setDictPic(pic);
        hoDictDao.updateByPrimaryKeySelective(hoDict);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 商家内幕列表
     *
     * @param params
     * @return
     */
    public JsonResult sellerTagList(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String type = params.get("type");
        ParamsUtil.checkParamIfNull(params, new String[]{"type"});


        List<HoDict> dictList = hoDictDao.sellerTagList(type);

        jsonResult.getData().put("dictList", dictList);
        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 新增商家内幕
     *
     * @param params
     * @return
     */
    public JsonResult createSellerTag(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String pid_1 = params.get("pid_1");
        String pid_2 = params.get("pid_2");
        String value = params.get("value");

        ParamsUtil.checkParamIfNull(params, new String[]{"value"});

        HoDict hoDict = new HoDict();
        hoDict.preInsert();
        hoDict.setDictSort("0");
        hoDict.setDictType("sellerTag");
        hoDict.setDictValue(value);
        hoDict.setPid("0");

        //新增三级标签
        if (!StringUtils.isEmpty(pid_1) && !StringUtils.isEmpty(pid_2)) {
            hoDict.setPid(pid_2);
        }
        //新增二级标签
        if (!StringUtils.isEmpty(pid_1) && StringUtils.isEmpty(pid_2)) {
            hoDict.setPid(pid_1);
        }
        hoDictDao.insert(hoDict);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
