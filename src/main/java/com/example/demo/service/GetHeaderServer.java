package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.util.GetTimeIntervalUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GetHeaderServer {
    //Key
    String key = "5c2c7c61103d60b76bc4ee2d";
    //appSecret
    String appSecret = "02939eacd6ffd2ddfb66eda2f48e626a";
    //code
    String code = "enbudf";
    //ts
    String ts;
    String sign;

    //获得Get的Sign，只是简单的?vin=
    public String getSignbyVIN(String vin) {
        //获取时间戳
        ts = Long.toString(System.currentTimeMillis());

        sign = key + "&vin=" + vin + "&" + ts + "&" + code + "&" + appSecret;
      //  sign = key + "&vin=" + vin + "&" + "1584932195239" + "&" + code + "&" + appSecret;
        sign = DigestUtils.md5DigestAsHex(sign.getBytes());
        System.out.println("sign:" + sign);
        return sign;
    }
    //获得sign,用于批量查询定位信息
    public String getPostSign(String vin){
        //获取时间戳
        ts = Long.toString(System.currentTimeMillis());
        Map<String, String> maps = new HashMap<>();

        JSONArray jsonArray=new JSONArray();
        /*for(int i=0;i<vin.length;i++){*/
          //  maps.put("vin",vin[i]);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("vin",vin);
            jsonArray.add(jsonObject);
        /*}*/
       // sign=key+"&"+"vinList=[{\"vin\":\""+vin+"\"}]"+"&"+ ts + "&" + code + "&" + appSecret;
        sign=key+"&"+"vinList="+jsonArray.toString()+"&"+ ts + "&" + code + "&" + appSecret;
        System.out.println("sign:" + sign);
        sign = DigestUtils.md5DigestAsHex(sign.getBytes());
        System.out.println("加密后sign:" + sign);
        return sign;
    }

    //获得sign

   //返回Key
    public String getKey(){
        return key;
    }
    //返回appSecret
    public String getAppSecret(){
        return appSecret;
    }
    //返回code
    public String getCode(){
        return code;
    }
    //返回ts
    public String getTs(){
        return ts;
    }

    //用于获取本周里程和本周启动次数
    public String getSignData(String vin) {
        //获取时间戳
        ts = Long.toString(System.currentTimeMillis());
        //获取当前日期
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        String currentData = f.format(new Date());
        //获取本周一日期
        GetTimeIntervalUtil getTimeIntervalUtil1=new GetTimeIntervalUtil();
        String yz_time=getTimeIntervalUtil1.getTimeInterval(new Date());//获取本周一时间
        sign=key+"&"+"endDate="+currentData +"&onDate="+yz_time +"&vin="+vin+"&"+ ts + "&" + code + "&" + appSecret;

        sign = DigestUtils.md5DigestAsHex(sign.getBytes());

        return sign;
    }

    //获得行程的sign
    public String getTripSign(String vin){
        ts = Long.toString(System.currentTimeMillis());
        //获取当前日期
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        String currentData = f.format(new Date());
        //获取前七天的日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        Date beforeDay = calendar.getTime();
        String bf=f.format(beforeDay);

        sign=key+"&"+"beginDate="+bf +"&endDate="+currentData +"&vin="+vin+"&"+ ts + "&" + code + "&" + appSecret;

        sign = DigestUtils.md5DigestAsHex(sign.getBytes());

        return sign;
    }

    //获得指定日期范围内的启动sign
    public String getStartSign(String vin,String startTime,String endTime){
        ts = Long.toString(System.currentTimeMillis());

        sign=key+"&"+"endDate="+endTime +"&onDate="+startTime +"&vin="+vin+"&"+ ts + "&" + code + "&" + appSecret;
        sign = DigestUtils.md5DigestAsHex(sign.getBytes());
        return sign;
    }
    //获得指定日期范围内的里程sign
//    public String getMileageSign(String vin,String startTime,String endTime){
//        ts = Long.toString(System.currentTimeMillis());
//
//        sign=key+"&"+"endDate="+endTime +"&onDate="+startTime +"&vin="+vin+"&"+ ts + "&" + code + "&" + appSecret;
//        sign = DigestUtils.md5DigestAsHex(sign.getBytes());
//        return sign;
//    }
    //获取单台车驾驶时长
    public String getTime(String vin,String begintime,String endtime){
        ts = Long.toString(System.currentTimeMillis());
        sign=key+"&"+"beginDate="+begintime +"&endDate="+endtime +"&vin="+vin+"&"+ ts + "&" + code + "&" + appSecret;
        sign = DigestUtils.md5DigestAsHex(sign.getBytes());
        return sign;
    }
}
