package com.example.demo.service;

import com.example.demo.dao.CarInfoDao;
import com.example.demo.model.carInfo;
import com.example.demo.util.AliyunOSSUtil;
import com.example.demo.util.GetLocation;
import com.example.demo.util.GetTimeIntervalUtil;
import com.example.demo.util.SendGetPostUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CarInfoServer {
    @Autowired
    CarInfoDao carInfoDao;

    GetHeaderServer getHeaderServer=new GetHeaderServer();
    SendGetPostUtil sendGetPostUtil =new SendGetPostUtil();
    JSONObject jsonObject = new JSONObject();
    AliyunOSSUtil aliyun=new AliyunOSSUtil();
  //  GetTimeIntervalUtil getTimeIntervalUtil=new GetTimeIntervalUtil();
  MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    //查询所有车辆
    public JSONObject queryAllCarInfo(){
        JSONObject json = new JSONObject();
        //构造json数据，先查询出数据库中所有的车辆vin
        List<carInfo> allCarInfo = carInfoDao.queryCarInfo();

        JSONObject j=new JSONObject();
        //定义String 数组，装vin号
        String[] vin=new String[allCarInfo.size()];
        //查询车辆定位信息经纬度
        for (int i=0;i<allCarInfo.size();i++){
            vin[i]=allCarInfo.get(i).getVin();
        }
           j=queryLocationByVin(vin);

        //通过经纬度查询地址
        JSONArray jr= JSONArray.fromObject(j.get("data"));
        GetLocation g=new GetLocation();
        List l=new ArrayList();
        for (int i =0;i<jr.size();i++){
            String lo=null;//经度
            String la=null;//纬度
            String v=null; //vin
            lo= (String) jr.getJSONObject(i).get("longitude");
            la= (String) jr.getJSONObject(i).get("latitude");
            v= (String) jr.getJSONObject(i).get("vin");

            //将获得的经纬度插入数据库中
            carInfoDao.updateLocation(lo,la,v);

            String city=null;//城市
            String Address=null;//地址
            //多层json,解析出城市和地址
            JSONObject jj2= g.getAddress(lo,la);
           JSONObject jso= JSONObject.fromObject(jj2.get("data"));
           jso.put("vin",v);
           JSONObject jj=JSONObject.fromObject(jso.get("regeocode"));
           JSONObject jjj=JSONObject.fromObject(jj.get("addressComponent"));
           System.out.println(vin[i]+jjj);
        //    city = (String) jjj.get("city");
          // city = (String) jjj.get("province");

            city= String.valueOf(jjj.get("city"));
            System.out.println(city);
            //System.out.println(city2==null);
           if (city.equals("[]")){
               city=String.valueOf(jjj.get("province"));
           }

           Address=jj.getString("formatted_address");
          //System.out.println(city+Address+v);
           //根据vin号，将城市，位置插入数据库
            carInfoDao.updateCarCityAndAddressByVin(city,Address,v);

        }
        //再重新读取数据库中的值，将数据组成一个json传给前端
        List<carInfo> allCarInfo2 = carInfoDao.queryCarInfo();
        JSONArray jsonArray = JSONArray.fromObject(allCarInfo2);
        json.put("data",jsonArray);
        return json;
    }
    //查询单台车辆
    public JSONObject queryCarInfoByVin(String Vin) {

        //访问的接口
        String DataUrl = "https://dev-dataapi.sgmwcloud.com.cn/api/car/info/mileage?vin="+Vin;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", getHeaderServer.getKey());
        params.add("code", getHeaderServer.getCode());
        params.add("sign",getHeaderServer.getSignbyVIN(Vin));
        params.add("ts", getHeaderServer.getTs());
        System.out.println("时间戳"+params.get("ts"));
        jsonObject=sendGetPostUtil.sendGetRequest(DataUrl,params);

        return jsonObject;
    }
//   //查询定位经纬度
    public JSONObject queryLocationByVin(String[] vin) {
        //访问的接口
        String url="https://dataapi.sgmwcloud.com.cn/api/car/info/gps/batch";

        HttpHeaders headers = new HttpHeaders();

        headers.add("sign",getHeaderServer.getPostSign(vin));
        headers.add("key",getHeaderServer.getKey());
        headers.add("code",getHeaderServer.getCode());
        headers.add("ts",getHeaderServer.getTs());
        headers.add("Content-Type","application/json");
       String result=sendGetPostUtil.getHttpClient(url, HttpMethod.POST,vin,headers);
        jsonObject= JSONObject.fromObject(result);
        return jsonObject;
    }
//通过vin号查询车辆
    public carInfo queryOneCarInfo(String vin){
        return carInfoDao.queryCarInfoByVin(vin);
    }
    //添加车辆
    public int AddCar(carInfo c){
      return carInfoDao.insertCarInfo(c);
    }
    //删除车辆
    public int DeleteCar(int id){
        return carInfoDao.deleteCarInfoByID(id);
    }
    //修改车辆信息
    public int updateCar(int id, String projectName, String vin, String borrower,String configuration,String state) {

        return carInfoDao.updateCarInfoByID(id, projectName, vin, borrower,configuration,state);
    }
    //查询车辆剩余电量和续航里程，本周里程，本周启动次数  进入车辆详情的时候
    public JSONObject queryCarInfoInfoByVin(String vin) {
        //访问续航里程的接口
       // String DataUrl = "https://dev-dataapi.sgmwcloud.com.cn/api/car/info/mileage?vin="+vin;
        //访问电量的接口
        String url="https://dataapi.sgmwcloud.com.cn/api/car/info/battery?vin="+vin;
        //访问本周里程接口
       String  mileageURL="https://dataapi.sgmwcloud.com.cn/api/car/suning/date/mileage";
        //访问本周启动次数接口
        String  suningUrl="https://dataapi.sgmwcloud.com.cn/api/car/suning/start/times";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", getHeaderServer.getKey());
        params.add("code", getHeaderServer.getCode());
        params.add("sign",getHeaderServer.getSignbyVIN(vin));
        params.add("ts", getHeaderServer.getTs());
        System.out.println("时间戳"+params.get("ts"));
        JSONObject jsonObject = new JSONObject();
        //调用电量的接口
        JSONObject jsonObject1 = sendGetPostUtil.sendGetRequest(url, params);
            jsonObject.put("batteryDate",jsonObject1.get("data"));

        //调用周期里程接口
        HttpHeaders headers = new HttpHeaders();
        headers.add("sign",getHeaderServer.getSignData(vin));
        headers.add("key",getHeaderServer.getKey());
        headers.add("code",getHeaderServer.getCode());
        headers.add("ts",getHeaderServer.getTs());
        headers.add("Content-Type","application/json");
        JSONObject jsonObject2=sendGetPostUtil.getDataHttpClient(mileageURL, HttpMethod.POST,vin,headers);

            jsonObject.put("mileageData",jsonObject2.get("data"));

        //调用周期启动次数接口

        JSONObject jsonObject3=sendGetPostUtil.getDataHttpClient(suningUrl, HttpMethod.POST,vin,headers);
            jsonObject.put("suningData",jsonObject3.get("data"));

            //查询单台车数据
        carInfo c=carInfoDao.queryCarInfoByVin(vin);
        System.out.println("lalall"+c.getVin()+c.getBorrower());
        jsonObject.put("oneCar",c);
        return jsonObject;
    }
    //查询排行
    public JSONObject queryRankin() {
        //System.out.println(queryAllCarInfo());
        JSONObject jRank=new JSONObject();
        List<carInfo> allCarVin = carInfoDao.queryCarInfo();
        System.out.println("1111");
        //定义String 数组，装vin号
        String[] vin=new String[allCarVin.size()];
       //一台台查本周里程
        for (int i=0;i<allCarVin.size();i++){
            vin[i]=allCarVin.get(i).getVin();
        }
        //访问本周里程接口
        String  mileageURL="https://dataapi.sgmwcloud.com.cn/api/car/suning/date/mileage";

        //访问本周启动次数接口
        String  suningUrl="https://dataapi.sgmwcloud.com.cn/api/car/suning/start/times";
        //调用周期里程接口
        for (int i=0;i<vin.length;i++){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type","application/json");
            headers.add("key",getHeaderServer.getKey());
            headers.add("code",getHeaderServer.getCode());
            headers.add("sign",getHeaderServer.getSignData(vin[i]));

            headers.add("ts",getHeaderServer.getTs());
            JSONObject jsonM=sendGetPostUtil.getDataHttpClient(mileageURL, HttpMethod.POST,vin[i],headers);
           // System.out.println("每个的结果"+jsonM);
            JSONObject jj=JSONObject.fromObject(jsonM.get("data"));
            JSONObject jj2=JSONObject.fromObject(jj.get("data"));
            int mileage=0;//里程
            //读取里程数据
           mileage= (Integer) jj2.get("Trip");

            //读取车辆启动次数
            JSONObject jsonObject3=sendGetPostUtil.getDataHttpClient(suningUrl, HttpMethod.POST,vin[i],headers);

            JSONObject jj3=JSONObject.fromObject(jsonObject3.get("data"));
            //获取数据状态
            String code= String.valueOf(jj3.get("code"));
            System.out.println("1231"+code);
            //如果启动次数无数据
            if (!code.equals("0")){
                //更新数据库,将启动次数删除
                carInfoDao.updateCarstartNumByVin(vin[i]);
                carInfoDao.updateMileageByVin(mileage,vin[i]);
            }else{
                //将启动次数累加
                JSONObject jj4=JSONObject.fromObject(jj3.get("data"));
                JSONArray jsonArray= jj4.getJSONArray("rspArray");
                int sumStart = 0;//启动次数
                for (int j=0;j<jsonArray.size();j++){
                    sumStart= (Integer) jsonArray.getJSONObject(j).get("numberOfStarts")+sumStart;
                }

                //更新数据库里程
                carInfoDao.updataCarMileageAndStartByVin(mileage,sumStart,vin[i]);
            }

        }

        //从数据库中按照里程降序读取
        List<carInfo> CarMilege = carInfoDao.queryCarInfoByMileage();
        List<carInfo> CarStart = carInfoDao.queryCarInfoByStart();
        JSONArray jsonArray = JSONArray.fromObject(CarMilege);
        JSONArray jsonArray2 = JSONArray.fromObject(CarStart);
        jRank.put("CarMilege",jsonArray);
        jRank.put("CarStart",jsonArray2);
        return jRank;
    }
   //更新借车人
    public int updateBorrow(String vin, String name, String phoneNum) {
        return carInfoDao.updateBorrow(vin,name,phoneNum);
    }
   //模糊查询
    public JSONObject fuzzyQuery(String vin) {
        JSONObject json = new JSONObject();
        List<carInfo> allCarInfo2 = carInfoDao.fuzzyQueryCarInfo(vin);
        JSONArray jsonArray = JSONArray.fromObject(allCarInfo2);
        System.out.println(allCarInfo2.size()+"lalal");
        json.put("fuzzyQuery",jsonArray);
        return json;
    }
    //获取最近7天，通过vin号
    public JSONObject findTrip(String vin){
        JSONObject json = new JSONObject();
        GetLocation getLocation=new GetLocation();
        String url="https://dataapi.sgmwcloud.com.cn/api/car/drive/trip/list";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        headers.add("key", getHeaderServer.getKey());
        headers.add("code", getHeaderServer.getCode());
        headers.add("sign",getHeaderServer.getTripSign(vin));
        headers.add("ts", getHeaderServer.getTs());
       JSONObject jsonObject=sendGetPostUtil.getTripHttpClient(url,HttpMethod.POST,vin,headers);
       //json.put("oneWeek",jsonObject);
        JSONObject j1=JSONObject.fromObject(jsonObject.get("data"));
        JSONObject j2=JSONObject.fromObject(j1.get("data"));
        JSONArray  j3=JSONArray .fromObject(j2.get("infoArray"));

        for (int i=0;i<j3.size();i++){

        //获取开始的经纬度
        String beginGPS=(String)j3.getJSONObject(i).get("beginGPS");
        //获取结束经纬度
            String endGPS=(String)j3.getJSONObject(i).get("endGPS");

            String startlng=beginGPS.substring(0, beginGPS.indexOf(","));
            String startlat=beginGPS.substring(startlng.length()+1, beginGPS.length());
            JSONObject j4=getLocation.getAddress(startlng,startlat);
            JSONObject jso1= JSONObject.fromObject(j4.get("data"));
            JSONObject jj1=JSONObject.fromObject(jso1.get("regeocode"));
            String startAddress=jj1.getString("formatted_address");
            System.out.println("开始"+startAddress);
            j3.getJSONObject(i).put("startAdress",startAddress);
            j3.getJSONObject(i).put("id",i);
            String endlng=endGPS.substring(0, endGPS.indexOf(","));
            String endlat=endGPS.substring(endlng.length()+1,endGPS.length());
            JSONObject j5=getLocation.getAddress(endlng,endlat);
            JSONObject jso= JSONObject.fromObject(j5.get("data"));
            JSONObject jj=JSONObject.fromObject(jso.get("regeocode"));
           String endAddress=jj.getString("formatted_address");
            System.out.println("结束"+endAddress);
            j3.getJSONObject(i).put("endAdress",endAddress);

            System.out.println(j3);
        }
        json.put("Trip",j3);
        System.out.println(json);
        return  json;
    }

    //根据关键字查询文件
    public JSONObject findFile(String[] array){
        //先查询OSS中的文件列表
        List<String> l=   aliyun.listALLfile("zhilianjiaohu/");
        //先查出子目录list
    //    List<String> listdir=aliyun.listALLfile();
        //再遍历子目录下的所有文件
        List<String> allList=new ArrayList<>();

        List<String> noDir=new ArrayList<>();
       for (int z=0;z<l.size();z++){
           Map<String, List<String>> res= aliyun.listALLfileBydir(l.get(z));
           allList.addAll(res.get("hasDir"));//有前缀
           noDir.addAll(res.get("noDir"));//没有前缀
       }

        JSONArray jsonArray=new JSONArray();
        Map map=new HashMap<>();
        //遍历关键字
        for (int i=0;i<array.length;i++){
            String keywords=array[i];//关键词
            for (int j = 0; j < allList.size(); j++) {
                //如果文件名包含关键词
                if(allList.get(j).contains(keywords)){
                    JSONObject jj=new JSONObject();
                    //先去掉files/
                    System.out.println("有前缀"+allList.get(j));
                    System.out.println("wu前缀"+noDir.get(j));
                    String file=allList.get(j);
                    map.put(file,file);
                    //获取url
                    URL u=aliyun.getUrl(file);
                    jj.put("fileName",noDir.get(j));
                    jj.put("url",u.toString().replace("http","https"));
                    jsonArray.add(jj);
                }
            }
        }
        //遍历hashmap，变成list
//        Iterator iter = map.entrySet().iterator();
//        while (iter.hasNext()) {
//            JSONObject j=new JSONObject();
//            Map.Entry entry=(Map.Entry) iter.next();
//            System.out.println(entry.getValue());
//            //获取每一个文件的下载地址
//            URL url= aliyun.getUrl((String) entry.getValue());
//            j.put("fileName",(String) entry.getValue());
//            j.put("url",url.toString());
//            jsonArray.add(j);
////           result.add(j);
//        }
        //将list变成json
        JSONObject jsonObj = new JSONObject();//转化为json格式
        jsonObj.put("files",jsonArray);
        return jsonObj;
    }

    //根据文件名查询文档下载地址
    public JSONObject findUrl(String fileName){
       URL url= aliyun.getUrl(fileName);
       System.out.println(url.toString());
       JSONObject j=new JSONObject();
       j.put("url",url.toString());
       return j;
    }

    //文件夹功能
    public JSONObject getDir(){
        JSONObject j=new JSONObject();
        List<String> list=new ArrayList<>();
        //先获取所有目录
        List<String> dir=aliyun.listdirectory();
        //根据目录获取目录下的文件
        for (int i=0;i<dir.size();i++){
           JSONArray file=aliyun.listfileByDir(dir.get(i));
JSONObject j2=new JSONObject();
j2.put("directory",dir.get(i));

       j2.put("dataFileName",file);
       j2.put("id",i);
       list.add(j2.toString());
        }
j.put("data",list);
        System.out.println(j);
        return j;
        }

        //批量获取OSS上面的视频文件
    public JSONObject getvideoList(){
        JSONObject j=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        //先获取视频文件名
        List<String> listvideo=aliyun.listvideoALLfile("视频/");
        //遍历视频文件名，获取URL
        for (int i=0;i<listvideo.size();i++){
            //获取文件url
            URL url=aliyun.getUrl("视频/"+listvideo.get(i));
            JSONObject jj=new JSONObject();
            jj.put("videoName",listvideo.get(i));
            jj.put("url",url.toString());
            jsonArray.add(jj);
        }
        j.put("data",jsonArray);
        return j;

    }

}

