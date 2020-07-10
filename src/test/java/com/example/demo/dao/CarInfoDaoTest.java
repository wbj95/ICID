package com.example.demo.dao;
import com.example.demo.model.Staff;
import com.example.demo.model.CarInfo;
import com.example.demo.service.CarInfoServer;
import com.example.demo.util.AliyunOSSUtil;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Service
class CarInfoDaoTest {

    @Autowired
    private CarInfoDao carInfoDao;
    private JSONObject JSONObjectjson;
    CarInfoServer c=new CarInfoServer();
    AliyunOSSUtil aliyun=new AliyunOSSUtil();
    private StaffDao staffDao;

    @Test
 //   查询所有车辆
    void queryCarInfo() throws Exception{
        List<CarInfo> car_info=carInfoDao.queryElectricCarInfo();

        System.out.println(car_info.get(0));
        JSONObjectjson=c.queryElectricCarInfo();

    }
   //通过VIN号查询车辆
    @Test
    void queryCarInfoByVin() throws Exception{
        CarInfo car_Info=carInfoDao.queryCarInfoByVin("LK6ADCE29KB150869");
        System.out.println(car_Info.getBorrower()+""+car_Info.getDataStart()+"111");
   }
//   //更新车辆
//    @Test
//   void updateCarInfoByID() throws Exception{
//     int up_result = carInfoDao.updateCarInfoByID(1,"E200","LK6ADCE29KB150869","baojunwei","LV4","OTS2");
//        System.out.println("更新结果："+up_result);
//    }
    //删除车辆
//    @Test
//    void deleteCarInfoByID() {
//      int delete_result = carInfoDao.deleteCarInfoByID(8);
//      System.out.println("删除结果："+delete_result);
//    }
    //新增车辆
//    @Test
//   void insertCarInfo() {
//        carInfo c=new carInfo();
//        c.setVin("LZWADAGAEJB645555");
//        c.setProjectName("E500");
//        c.setBorrower("韦保俊");
//        c.setConfiguration("LV3");
//        c.setState("NS");
//        int result = carInfoDao.insertCarInfo(c);
//        System.out.println(result);
//    }
    //查询调用接口数据
// @Test
//    void queryByVin(){
//
//        JSONObjectjson= c.queryCarInfoByVin("LK6ADCE29KB150869");
//        System.out.println(JSONObjectjson.toString());
//    }
    //查询剩余电量
    @Test
    void  querybatElectricByVin(){
        JSONObjectjson= c.queryCarInfoInfoByVin("LK6ADCE29KB150869");
        System.out.println(JSONObjectjson.toString());
    }

    //查询经度、纬度
    @Test
    void queryLocationByVin(){
        String s="LK6ADCE21JB104631";
        JSONObjectjson=c.queryLocationByVin(s);
        System.out.println(JSONObjectjson.toString());
    }

//    @Test
//    void test1(){
//        String result=c.queryLocationByVin1("LK6ADCE21JB104631");
//        System.out.println(result);
//    }

    //删除车辆
//    void deleteCar(){
//        System.out.println("删除结果"+carInfoDao.deleteCarInfoByID(7));
//    }
    //获取本周一至周日时间范围
//    @Test
//    void getTime(){
//       GetTimeIntervalUtil getTimeIntervalUtil1=new GetTimeIntervalUtil();
//        String yz_time=getTimeIntervalUtil1.getTimeInterval(new Date());//获取本周一时间
//        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
//        String currentData = f.format(new Date());
//        System.out.println(yz_time  + " " + currentData);
////
//        JSONObjectjson=c.queryCarInfoInfoByVin("LK6ADCE21JB104631");
//        System.out.println(JSONObjectjson.toString());
//    }
//    @Test
//    void getAdress(){
//        GetLocation g=new GetLocation();
//        JSONObject add = g.getAddress("108.867338","24.796695");
//        System.out.println(add);
//    }
//    //更新城市和地址
//    @Test
//    void updateCity(){
//        System.out.println(carInfoDao.updateCarCityAndAddressByVin("liuzhou","五菱家园","LK6ADCE29KB150869"));
//    }
    //查询周里程，周启动次数排行
//    @Test
//    void queryRanking(){
////        List<carInfo> allCarVin = carInfoDao.queryCarInfo();
////        JSONArray jsonArray = JSONArray.fromObject(allCarVin);
////        JSONObject json=new JSONObject();
////        json.put("data",jsonArray);
//        System.out.println(c.queryRankin());
//    }
    //更新周里程和周启动次数
//        @Test
//    void updateDataMileage(){
//        System.out.println(carInfoDao.updataCarMileageAndStartByVin(30,6,"LK6ADCE29KB150869"));
//    }
    //数据库查询按照里程降序
//            @Test
//    void queryDataMileage(){
//        List<carInfo> c= carInfoDao.queryCarInfoByMileage();
//        for (int i=0;i<c.size();i++){
//            System.out.println(c.size()+"  "+c.get(i));
//        }
//    }

//    @Test
//    void updateStartToNULL(){
//        carInfoDao.updateCarstartNumByVin("LK6ADAE29KB998870");
//    }

    //更新借车人
    @Test
    void updataborrow(){
        carInfoDao.updateBorrow("LK6ADCE29KB150869","陈涛","135645345646");
    }

    //vin号模糊查询
    @Test
    void fuzzyQuery(){

        System.out.println(carInfoDao.fuzzyQueryCarInfo("8862").size());
    }

    //将经纬度插入数据库中
    @Test
    void updateLocation(){
        System.out.println(carInfoDao.updateLocation("116.456023","40.018171","LK6ADAE20LB990321"));

    }
    //获取最近一周的行程
    @Test
    void getTrip(){
        System.out.println(c.findTrip("LK6ADCE25JB011448"));
    }
    //获取阿里云所有文件
    @Test
    void listAliyunFile(){
//     List<String> l=   aliyun.listALLfile();
        String[] s=new String[1];
        s[0]="诊断";
        JSONObject j= c.findFile(s);
        System.out.println(j);
        // 1. 普通遍历方式
//        for (int i = 0; i < l.size(); i++) {
//            System.out.println(l.get(i));
//        }
      //  aliyun.fileFolder("files");
    }
    //获得下载路径
    @Test
    void geturl(){
     URL u= aliyun.getUrl("UDS诊断体系结构20170329.pptx");
     System.out.println(u);
    }
    //获取制定目录下的所有文件
    @Test
    void getfileBydir(){
       List<String> l= aliyun.listfileByDir("zhilianjiaohu/知识产权/");
        // 1. 普通遍历方式
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i));
        }
    }
    //获取制定目录下的所有文件,JSON格式
    @Test
    void getfileBydirJSON(){
        c.getDir();
    }

    //查询OSS中的所有video
    @Test
    void getALLvideo(){
        aliyun.listvideoALLfile("视频/");
    }
    @Test
    void queryAllStaff(){
        List<Staff>  list=staffDao.queryAllStaff();

        System.out.println(list.get(0).getStaffName());
    }



}