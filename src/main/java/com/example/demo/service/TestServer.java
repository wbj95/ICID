package com.example.demo.service;

import com.example.demo.util.SendGetPostUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestServer {
    GetHeaderServer getHeaderServer=new GetHeaderServer();
    SendGetPostUtil sendGetPostUtil =new SendGetPostUtil();

    //批量查询每天点火车辆数
    public JSONObject queryCar(String[] AllCarVin,String[] Date){
        //访问本周启动次数接口
        String  suningUrl="https://dataapi.sgmwcloud.com.cn/api/car/suning/start/times";

        JSONObject j=new JSONObject();
        //一台一台遍历
            for (int z=0;z<Date.length;z++){
                int starttimes=0;
                for (int i=0;i<AllCarVin.length;i++){
                    HttpHeaders headers = new HttpHeaders();
                headers.add("sign",getHeaderServer.getStartSign(AllCarVin[i],Date[z],Date[z]));
                headers.add("key",getHeaderServer.getKey());
                headers.add("code",getHeaderServer.getCode());
                headers.add("ts",getHeaderServer.getTs());
                headers.add("Content-Type","application/json");
                JSONObject jsonObject3=sendGetPostUtil.getDataStartHTTP(suningUrl, HttpMethod.POST,AllCarVin[i],headers,Date[z],Date[z]);
                JSONObject jj=JSONObject.fromObject(jsonObject3.get("data"));
                //获取数据状态
                String code= String.valueOf(jj.get("code"));
                if (!code.equals("0")){
                    //无数据
                }else {
                    //启动车辆数加1
                    starttimes=starttimes+1;
                }
            }
                j.put(Date[z]+"startData:",starttimes);
        }
        return j;
    }

    //计算划分不同行驶里程的车辆数
    public JSONObject queryMileage(String[] AllVin,String beginDate,String endDate){
        System.out.println("总车辆数："+AllVin.length);
        //访问周期里程
        String  mileageURL="https://dataapi.sgmwcloud.com.cn/api/car/suning/date/mileage";
        JSONObject j=new JSONObject();
        int count1=0;//>10&&<50
        int count2=0;//>50&&<100
        int count3=0;//>100&&<200
        int count4=0;//>200&&<300
        int count5=0;//>300&&<400
        int count6=0;//>400
        int count7=0;//<10
       //遍历vin
        for (int i=0;i<AllVin.length;i++){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type","application/json");
            headers.add("key",getHeaderServer.getKey());
            headers.add("code",getHeaderServer.getCode());
            headers.add("sign",getHeaderServer.getStartSign(AllVin[i],beginDate,endDate));
            headers.add("ts",getHeaderServer.getTs());
            JSONObject jsonM=sendGetPostUtil.getDataStartHTTP(mileageURL, HttpMethod.POST,AllVin[i],headers,beginDate,endDate);
            JSONObject jj=JSONObject.fromObject(jsonM.get("data"));
            JSONObject jj2=JSONObject.fromObject(jj.get("data"));
            //获取数据状态
            String code= String.valueOf(jj.get("code"));

            if (!code.equals("0")){
                //无数据
            }else {
                int mileage= (Integer) jj2.get("Trip");
                if (mileage>10&mileage<=50){
                    count1++;
                }else if (mileage>50&mileage<=100){
                    count2++;
                }else if (mileage>100&mileage<=200){
                    count3++;
                }else if (mileage>200&mileage<=300){
                    count4++;
                }else if (mileage>300&mileage<=400){
                    count5++;
                }else if (mileage>=400){
                    count6++;
                }else {
                    //《10
                    count7++;
                }
                System.out.println("行驶里程:"+mileage);
            }
          //

        }
        System.out.println(">10&<=50:"+count1);
        System.out.println(">50&<=100:"+count2);
        System.out.println(">100&<=200:"+count3);
        System.out.println(">200&<=300:"+count4);
        System.out.println(">300&<=400:"+count5);
        System.out.println(">400:"+count6);
        System.out.println("<10:"+count7);
        return j;
}
//日均行驶里程
    public void rijunMileage(String[] vin,String[] date){
        //访问周期里程
        String  mileageURL="https://dataapi.sgmwcloud.com.cn/api/car/suning/date/mileage";
    //遍历时间
    for (int i=0;i<date.length;i++){
        int countMileage=0;
        int count=0;
        //遍历vin
        for (int j=0;j<vin.length;j++){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type","application/json");
            headers.add("key",getHeaderServer.getKey());
            headers.add("code",getHeaderServer.getCode());
            headers.add("sign",getHeaderServer.getStartSign(vin[j],date[i],date[i]));
            headers.add("ts",getHeaderServer.getTs());
            JSONObject jsonM=sendGetPostUtil.getDataStartHTTP(mileageURL, HttpMethod.POST,vin[j],headers,date[i],date[i]);
            JSONObject jj=JSONObject.fromObject(jsonM.get("data"));
            JSONObject jj2=JSONObject.fromObject(jj.get("data"));
            //获取数据状态
            String code= String.valueOf(jj.get("code"));
            if (!code.equals("0")){
                //无数据
            }else {
                //有数据
                count++;
                int mileage= (Integer) jj2.get("Trip");
                countMileage=countMileage+mileage;

            }
        }
        //总里程/车辆数
        System.out.println("日均行驶里程"+date[i]+":"+countMileage/count+"KM");
    }
    }
    //计算划分不同启动次数的车辆数
    public void startTimes(String[] vin,String beginDate,String endDate){
        //访问本周启动次数接口
        String  suningUrl="https://dataapi.sgmwcloud.com.cn/api/car/suning/start/times";
        int count1=0;//>0&&<10
        int count2=0;//>10&<=20
        int count3=0;//>20&&<30
        int count4=0;//>30&&<40
        int count5=0;//>40&&<50
        int count6=0;//>50&&<60
        int count7=0;//>60
        //遍历vin
        for (int i=0;i<vin.length;i++){
            int statrcount=0;
            HttpHeaders headers = new HttpHeaders();
            headers.add("sign",getHeaderServer.getStartSign(vin[i],beginDate,endDate));
            headers.add("key",getHeaderServer.getKey());
            headers.add("code",getHeaderServer.getCode());
            headers.add("ts",getHeaderServer.getTs());
            headers.add("Content-Type","application/json");
            JSONObject jsonObject3=sendGetPostUtil.getDataStartHTTP(suningUrl, HttpMethod.POST,vin[i],headers,beginDate,endDate);
            JSONObject jj=JSONObject.fromObject(jsonObject3.get("data"));
            //获取数据状态
            String code= String.valueOf(jj.get("code"));
            if (!code.equals("0")){
                //无数据
            }else {
                //计算总次数
                JSONObject jj2=JSONObject.fromObject(jj.get("data"));
                JSONArray jsonArray= jj2.getJSONArray("rspArray");
                int countstart=0;
              //遍历JSONArray取数
                for (int j=0;j<jsonArray.size();j++){
                    countstart = (int) jsonArray.getJSONObject(j).get("numberOfStarts")+countstart;

                }
                System.out.println(vin[i]+":"+countstart);
                if (countstart>0&countstart<=10){
                    count1++;
                }else if(countstart>10&countstart<=20){
                    count2++;
                }else if(countstart>20&countstart<=30){
                    count3++;
                }else if(countstart>30&countstart<=40){
                    count4++;
                }else if(countstart>40&countstart<=50){
                    count5++;
                }else if(countstart>50&countstart<=60){
                    count6++;
                }else if(countstart>60){
                    count7++;
                }
            }
        }
        System.out.println(">0&<=10:"+count1);
        System.out.println(">10&<=20:"+count2);
        System.out.println(">20&<=30:"+count3);
        System.out.println(">30&<=40:"+count4);
        System.out.println(">40&<=50:"+count5);
        System.out.println(">50&<=60:"+count6);
        System.out.println(">60:"+count7);
    }

    //日均启动次数
   public void rijunstart(String[] vin,String[] date){
       String  suningUrl="https://dataapi.sgmwcloud.com.cn/api/car/suning/start/times";
        //先循环时间
       for (int i=0;i<date.length;i++){
           //所有车总次数
           int allcarTimes=0;
           //有数据的车辆数
           int car=0;
           //循环vin号
           for (int j=0;j<vin.length;j++){
               HttpHeaders headers = new HttpHeaders();
               headers.add("sign",getHeaderServer.getStartSign(vin[j],date[i],date[i]));
               headers.add("key",getHeaderServer.getKey());
               headers.add("code",getHeaderServer.getCode());
               headers.add("ts",getHeaderServer.getTs());
               headers.add("Content-Type","application/json");
               JSONObject jsonObject3=sendGetPostUtil.getDataStartHTTP(suningUrl, HttpMethod.POST,vin[j],headers,date[i],date[i]);
               JSONObject jj=JSONObject.fromObject(jsonObject3.get("data"));
               //获取数据状态
               String code= String.valueOf(jj.get("code"));
               if (!code.equals("0")){
                   //无数据
               }else {
                   //有数据
                   car++;
                   JSONObject jj2=JSONObject.fromObject(jj.get("data"));
                   JSONArray jsonArray= jj2.getJSONArray("rspArray");
                   int countstart=0;
                   //遍历JSONArray取数
                   for (int z=0;z<jsonArray.size();z++){
                       countstart = (int) jsonArray.getJSONObject(z).get("numberOfStarts")+countstart;
                   }
//                    System.out.println(vin[j]);
                   allcarTimes=allcarTimes+countstart;
               }
           }
           System.out.println(car);
           System.out.println("单日日均："+date[i]+":"+allcarTimes/car);

       }
   }
    //计算划分不同行驶时间的车辆数
   public void rijunDriving(String[] vin,String[] date){
        String url="https://dataapi.sgmwcloud.com.cn/api/car/drive/charge/time";
        //遍历日期
       for (int i=0;i<date.length;i++){
           //当日总驾驶时长
           int allTime=0;
           //计数
           int count=0;
           //遍历车辆
            for (int j=0;j<vin.length;j++){
                //获取单台车驾驶时长
                HttpHeaders headers = new HttpHeaders();
                headers.add("sign",getHeaderServer.getTime(vin[j],date[i],date[i]));
                headers.add("key",getHeaderServer.getKey());
                headers.add("code",getHeaderServer.getCode());
                headers.add("ts",getHeaderServer.getTs());
                headers.add("Content-Type","application/json");
                JSONObject jsonObject3=sendGetPostUtil.getDrivingTimeHttpClient(url, HttpMethod.POST,vin[j],headers,date[i],date[i]);
                JSONObject jj=JSONObject.fromObject(jsonObject3.get("data"));
                //获取数据状态
                String code= String.valueOf(jj.get("code"));
                if (!code.equals("0")){
                    //无数据
                }else {
                    //有数据

                    JSONObject jj2=JSONObject.fromObject(jj.get("data"));
                    if (jj2.containsKey("drivingTime")){
                        count++;
                        int oneTime= (Integer) jj2.get("drivingTime");
                        allTime=oneTime+allTime;
                    }
                }
            }
            System.out.println("当日日均驾驶时长"+date[i]+":"+allTime/count);
       }
   }
}
