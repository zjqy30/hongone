package com.hone.pc.backend.service;

import com.hone.dao.HoBannersDao;
import com.hone.entity.HoBanners;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/14.
 */

@Service
@Transactional
public class BannerService {

    @Autowired
    private HoBannersDao hoBannersDao;


    /**
     * 轮播图列表
     *
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) {
        JsonResult jsonResult = new JsonResult();

        List<HoBanners> bannersList = hoBannersDao.findAll();

        jsonResult.getData().put("bannersList", bannersList);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 创建轮播图
     *
     * @param params
     * @return
     */
    public JsonResult create(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String imgs = params.get("imgs");
        String pages = params.get("pages");
        String title = params.get("title");
        String type = params.get("type");
        String content = params.get("content");
        String h5_url = params.get("h5_url");

        ParamsUtil.checkParamIfNull(params, new String[]{"type", "title", "pages", "imgs"});

        HoBanners hoBanners = new HoBanners();
        hoBanners.setSort("0");
        hoBanners.setH5Url(h5_url);
        hoBanners.setImgs(imgs);
        hoBanners.setPages(pages);
        hoBanners.setContent(content);
        hoBanners.setType(type);
        hoBanners.setTitle(title);
        hoBanners.preInsert();
        hoBannersDao.insert(hoBanners);

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 删除轮播图
     *
     * @param params
     * @return
     */
    public JsonResult del(Map<String, String> params) throws Exception {
        JsonResult jsonResult = new JsonResult();

        String id = params.get("id");

        ParamsUtil.checkParamIfNull(params, new String[]{"id"});

        HoBanners hoBanners=hoBannersDao.selectByPrimaryKey(id);
        hoBanners.setEnableFlag("0");
        hoBannersDao.updateByPrimaryKeySelective(hoBanners);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
