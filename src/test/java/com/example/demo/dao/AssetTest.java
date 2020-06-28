package com.example.demo.dao;

import com.example.demo.model.AssetManagement;
import com.example.demo.service.AssetManagementServer;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Service
public class AssetTest {
    @Autowired
    AssetManagementDao assetManagementDao;

    @Autowired
    AssetManagementServer assetManagementServer;
    @Test
    void queryAsset(){
//        List<AssetManagement> l=assetManagementDao.quearyAsset();
//        for(int i=0;i<l.size();i++){
//            System.out.println(l.get(i).getAssetNumbles());
//        }
       JSONObject j= assetManagementServer.queryAsset();
       System.out.println(j.toString());

        }
}

