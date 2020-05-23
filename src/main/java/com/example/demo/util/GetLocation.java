package com.example.demo.util;


import net.sf.json.JSONObject;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
//用于将经纬度转换为地址
public class GetLocation {
    SendGetPostUtil sendGetPostUtil=new SendGetPostUtil();
  //  JSONObject j=new JSONObject();
    public JSONObject getAddress(String longitude,String latitude){
     //高德地图url
      String url="https://restapi.amap.com/v3/geocode/regeo?output=JSON&location="+longitude+","+latitude+"&key=5630a25249504628c5187f169ffefff4&radius=100&extensions=base";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("Content-Type","application/json");
   //   j=sendGetPostUtil.sendGetRequest(url,params);
   //   System.out.println("get结果"+j.get("data"));
      return  sendGetPostUtil.sendGetRequest(url,params);
    }
}
