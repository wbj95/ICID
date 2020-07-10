package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.service.GetHeaderServer;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntityEnclosingRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SendGetPostUtil {
    GetHeaderServer getHeaderServer;
    JSONObject jsonObject = new JSONObject();
    private HttpEntityEnclosingRequest httpPost;
    RestTemplate template = new RestTemplate();
    RestTemplate client = new RestTemplate();
    //普通的GET请求
    public JSONObject sendGetRequest(String url,MultiValueMap<String, String> params){


        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;


        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers,params);
        ResponseEntity<String> response=client.exchange(url, method,requestEntity,String .class);

        jsonObject.put("data",response.getBody());

        return jsonObject;
    }
   //POST请求，此方法暂时无法使用
    public JSONObject sendPostRequest(String url,MultiValueMap<String, String> params){

        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(headers,params);
        ResponseEntity<String> response=client.exchange(url, method,requestEntity,String .class);
        jsonObject.put("data",response.getBody());

        return jsonObject;
    }
   //获取批量定位信息的POST请求
    public String getHttpClient(String url, HttpMethod method,String[] vin, HttpHeaders headers) {
        RestTemplate template = new RestTemplate();

        //将请求体封装到map集合里面
        Map<String, String> maps = new HashMap<>();
        //将vin的数组放到maps里面
        //将vin封装成json
        JSONArray jsonArray=new JSONArray();
          //  maps.put("vin",vin);
        for (int i=0;i<vin.length;i++){
            //将请求头部和参数合成一个请求
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("vin",vin[i]);
            jsonArray.add(jsonObject1);
        }

       JSONObject jsonObject2  = new JSONObject();
       jsonObject2.put("vinList",jsonArray.toString());
        //请求头和请求体合成一起
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(jsonObject2, headers);
        //执行HTTP请求
        ResponseEntity<String> response = template.exchange(url, method, requestEntity, String.class);


        return response.getBody();
    }
   //获取本周里程和本周启动次数的POST请求
    public JSONObject getDataHttpClient(String url, HttpMethod method, String vin, HttpHeaders headers) {
        RestTemplate template = new RestTemplate();
        //将请求头部和参数合成一个请求
        JSONObject jsonObject = new JSONObject();

        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        String currentData = f.format(new Date());
        GetTimeIntervalUtil getTimeIntervalUtil1=new GetTimeIntervalUtil();
        String yz_time=getTimeIntervalUtil1.getTimeInterval(new Date());//获取本周一时间

        //将vin封装成json
        jsonObject.put("vin" , vin);
        jsonObject.put("endDate",currentData);
        jsonObject.put("onDate",yz_time);

        //请求头和请求体合成一起
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(jsonObject, headers);
        //执行HTTP请求
        ResponseEntity<String> response = template.exchange(url, method, requestEntity, String.class);

        jsonObject.put("data",response.getBody());
        return jsonObject;
    }
    //获得最近七天行车列表
    public JSONObject getTripHttpClient(String url, HttpMethod method, String vin, HttpHeaders headers) {
        RestTemplate template = new RestTemplate();
        //将请求头部和参数合成一个请求
        JSONObject jsonObject = new JSONObject();

        //获取当前日期
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        String currentData = f.format(new Date());
        //获取前七天的日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        Date beforeDay = calendar.getTime();
        String bf=f.format(beforeDay);

        //将vin封装成json
        jsonObject.put("vin" , vin);
        jsonObject.put("beginDate",bf);
        jsonObject.put("endDate",currentData);

        //请求头和请求体合成一起
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(jsonObject, headers);
        //执行HTTP请求
        ResponseEntity<String> response = template.exchange(url, method, requestEntity, String.class);

        jsonObject.put("data",response.getBody());
        return jsonObject;
    }

    //获取指定日期内的启动次数
    public JSONObject getDataStartHTTP(String url, HttpMethod method, String vin, HttpHeaders headers,String beginData,String endDate) {
        RestTemplate template = new RestTemplate();
        //将请求头部和参数合成一个请求
        JSONObject jsonObject = new JSONObject();

        //将vin封装成json
        jsonObject.put("vin" , vin);
        jsonObject.put("endDate",endDate);
        jsonObject.put("onDate",beginData);
        //请求头和请求体合成一起
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(jsonObject, headers);
        //执行HTTP请求
        ResponseEntity<String> response = template.exchange(url, method, requestEntity, String.class);

        jsonObject.put("data",response.getBody());
        return jsonObject;
    }

    public JSONObject getDrivingTimeHttpClient(String url, HttpMethod method, String vin, HttpHeaders headers,String beginDate,String endDate) {
        RestTemplate template = new RestTemplate();
        //将请求头部和参数合成一个请求
        JSONObject jsonObject = new JSONObject();

        //将vin封装成json
        jsonObject.put("vin" , vin);
        jsonObject.put("beginDate",beginDate);
        jsonObject.put("endDate",endDate);
        //请求头和请求体合成一起
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(jsonObject, headers);
        //执行HTTP请求
        ResponseEntity<String> response = template.exchange(url, method, requestEntity, String.class);

        jsonObject.put("data",response.getBody());
        return jsonObject;
    }
}
