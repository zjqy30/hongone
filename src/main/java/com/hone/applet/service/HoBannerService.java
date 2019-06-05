package com.hone.applet.service;

import com.alibaba.fastjson.JSONObject;
import com.hone.dao.HoBannersDao;
import com.hone.dao.HoDictDao;
import com.hone.entity.HoBanners;
import com.hone.entity.HoDict;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/30.
 */

@Service
@Transactional
public class HoBannerService {

    @Autowired
    private HoBannersDao hoBannersDao;


    public JsonResult initData(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String filePath=params.get("filePath");

        BufferedReader bufferedReader=new BufferedReader(new FileReader(filePath));
        String line="";
        while ((line=bufferedReader.readLine())!=null){

            JSONObject jsonObject= (JSONObject) JSONObject.parse(line);
            HoBanners hoBanners=new HoBanners();
            hoBanners.setImgs(jsonObject.getString("p_id"));
            hoBanners.setPages("1");
            hoBanners.setSort("0");
            hoBanners.preInsert();
            hoBannersDao.insert(hoBanners);

        }

        bufferedReader.close();

        jsonResult.globalSuccess();
        return jsonResult;
    }


    /**
     * 获取banner列表
     * @param params
     * @return
     */
    public JsonResult list(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        String pages=params.get("pages");
        ParamsUtil.checkParamIfNull(params,new String[]{"pages"});

        List<HoBanners> bannersList=hoBannersDao.findByPages(pages);
        jsonResult.globalSuccess();
        jsonResult.getData().put("bannersList",bannersList);
        return jsonResult;
    }


    /**
     * 保存banner
     * @param params
     * @return
     */
    public JsonResult save(Map<String, String> params) throws Exception {
        JsonResult jsonResult=new JsonResult();

        //参数校验
        String type=params.get("type");
        String pages=params.get("pages");
        String[] strs=new String[]{"type","pages"};
        ParamsUtil.checkParamIfNull(params,strs);
        if(type.equals("1")){
            strs[2]="imgs";
        }
        if(type.equals("2")){
            strs[2]="content";
        }
        if(type.equals("3")){
            strs[2]="h5Url";
        }
        ParamsUtil.checkParamIfNull(params,strs);


        //保存banner
        HoBanners hoBanners=new HoBanners();
        hoBanners.preInsert();
        hoBanners.setType(type);
        hoBanners.setContent(params.get("content"));
        hoBanners.setPages(pages);
        hoBanners.setImgs(params.get("imgs"));
        hoBanners.setH5Url(params.get("h5Url"));
        hoBanners.setSort(params.get("sort"));
        hoBannersDao.insert(hoBanners);

        jsonResult.globalSuccess();
        return jsonResult;
    }
}
