package com.example.demo.controller;

import com.example.demo.service.AssetManagementServer;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/assetManage")
@RestController
public class AssetController {

    private AssetManagementServer assetManagementServer;

    public AssetController(AssetManagementServer assetManagementServer) {
        this.assetManagementServer = assetManagementServer;
    }
    //查询所有资产
    @RequestMapping(path = {"/getAllAsset"},method = {RequestMethod.GET})
    public JSONObject queryAsset(){
        JSONObject j= assetManagementServer.queryAsset();
        return j;
    }
}
