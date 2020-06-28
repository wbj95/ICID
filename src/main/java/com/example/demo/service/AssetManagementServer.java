package com.example.demo.service;

import com.example.demo.dao.AssetManagementDao;
import com.example.demo.model.AssetManagement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetManagementServer {

    @Autowired
    AssetManagementDao assetManagementDao;

    //查询所有资产
    public JSONObject queryAsset(){
        JSONObject j=new JSONObject();

       List<AssetManagement> l= assetManagementDao.quearyAsset();

        JSONArray jsonArray = JSONArray.fromObject(l);

        j.put("data",jsonArray);
        return j;

    }
}
