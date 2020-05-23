package com.example.demo.dao;

import com.example.demo.model.Staff;
import com.example.demo.service.TestServer;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Service
public class TestTest {

     TestServer testServer=new TestServer();
    //批量查询一定周期内车辆点火数
    @Test
    void queryCarStart(){
        String[] vin=new String[]{ "LK6ADAE26LB200505",
                "LK6ADAE23LB200459",
                "LK6ADAE24LB200504",
                "LK6ADAE26LB200603",
                "LK6ADAE25LB200527",
                "LK6ADAE25LB200558",
                "LK6ADAE25LB200561",
                "LK6ADAE28LB200554",
                "LK6ADAE26LB200567",
                "LK6ADAE29LB200580",
                "LK6ADAE23LB200574",
                "LK6ADAE29LB200501",
                "LK6ADAE21LB200587",
                "LK6ADAE22LB200520",
                "LK6ADAE2XLB200524",
                "LK6ADAE27LB200545",
                "LK6ADAE23LB200591",
                "LK6ADAE29LB200529",
                "LK6ADAE22LB200565",
                "LK6ADAE2XLB200541",
                "LK6ADAE29LB200465",
                "LK6ADAE2XLB200538",
                "LK6ADAE22LB200503",
                "LK6ADAE20LB200581"
        };
        String[] Date=new String[]{
                "2020-05-07",
                "2020-05-08",
                "2020-05-09",
                "2020-05-10",
                "2020-05-11",
                "2020-05-12",
                "2020-05-13",
                "2020-05-14"
        };
        JSONObject j=testServer.queryCar(vin,Date);
        System.out.println("启动次数："+j.toString());
    }

    //查询里程
    @Test
    void queryMileage() {
        String[] vin=new String[]{
                "LK6ADAE26LB200505",
                "LK6ADAE23LB200459",
                "LK6ADAE24LB200504",
                "LK6ADAE26LB200603",
                "LK6ADAE25LB200527",
                "LK6ADAE25LB200558",
                "LK6ADAE25LB200561",
                "LK6ADAE28LB200554",
                "LK6ADAE26LB200567",
                "LK6ADAE29LB200580",
                "LK6ADAE23LB200574",
                "LK6ADAE29LB200501",
                "LK6ADAE21LB200587",
                "LK6ADAE22LB200520",
                "LK6ADAE2XLB200524",
                "LK6ADAE27LB200545",
                "LK6ADAE23LB200591",
                "LK6ADAE29LB200529",
                "LK6ADAE22LB200565",
                "LK6ADAE2XLB200541",
                "LK6ADAE29LB200465",
                "LK6ADAE2XLB200538",
                "LK6ADAE22LB200503",
                "LK6ADAE20LB200581"
        };
        JSONObject j=testServer.queryMileage(vin,"2020-05-07","2020-05-14");

    }

    //查询日均里程

    @Test
    void queryrijun() {
        String[] vin=new String[]{
                "LK6ADAE26LB200505",
                "LK6ADAE23LB200459",
                "LK6ADAE24LB200504",
                "LK6ADAE26LB200603",
                "LK6ADAE25LB200527",
                "LK6ADAE25LB200558",
                "LK6ADAE25LB200561",
                "LK6ADAE28LB200554",
                "LK6ADAE26LB200567",
                "LK6ADAE29LB200580",
                "LK6ADAE23LB200574",
                "LK6ADAE29LB200501",
                "LK6ADAE21LB200587",
                "LK6ADAE22LB200520",
                "LK6ADAE2XLB200524",
                "LK6ADAE27LB200545",
                "LK6ADAE23LB200591",
                "LK6ADAE29LB200529",
                "LK6ADAE22LB200565",
                "LK6ADAE2XLB200541",
                "LK6ADAE29LB200465",
                "LK6ADAE2XLB200538",
                "LK6ADAE22LB200503",
                "LK6ADAE20LB200581"
        };
        String[] Date=new String[]{
                "2020-05-07",
                "2020-05-08",
                "2020-05-09",
                "2020-05-10",
                "2020-05-11",
                "2020-05-12",
                "2020-05-13",
                "2020-05-14"
        };
        testServer.rijunMileage(vin,Date);

    }

    //计算启动次数车辆
    @Test
    void start() {
        String[] vin=new String[]{
                "LK6ADAE26LB200505",
                "LK6ADAE23LB200459",
                "LK6ADAE24LB200504",
                "LK6ADAE26LB200603",
                "LK6ADAE25LB200527",
                "LK6ADAE25LB200558",
                "LK6ADAE25LB200561",
                "LK6ADAE28LB200554",
                "LK6ADAE26LB200567",
                "LK6ADAE29LB200580",
                "LK6ADAE23LB200574",
                "LK6ADAE29LB200501",
                "LK6ADAE21LB200587",
                "LK6ADAE22LB200520",
                "LK6ADAE2XLB200524",
                "LK6ADAE27LB200545",
                "LK6ADAE23LB200591",
                "LK6ADAE29LB200529",
                "LK6ADAE22LB200565",
                "LK6ADAE2XLB200541",
                "LK6ADAE29LB200465",
                "LK6ADAE2XLB200538",
                "LK6ADAE22LB200503",
                "LK6ADAE20LB200581"
        };
        testServer.startTimes(vin,"2020-05-07","2020-05-14");

    }

    //计算单日日均启动次数
    @Test
    void startrijun() {
        String[] vin=new String[]{
                "LK6ADAE26LB200505",
                "LK6ADAE23LB200459",
                "LK6ADAE24LB200504",
                "LK6ADAE26LB200603",
                "LK6ADAE25LB200527",
                "LK6ADAE25LB200558",
                "LK6ADAE25LB200561",
                "LK6ADAE28LB200554",
                "LK6ADAE26LB200567",
                "LK6ADAE29LB200580",
                "LK6ADAE23LB200574",
                "LK6ADAE29LB200501",
                "LK6ADAE21LB200587",
                "LK6ADAE22LB200520",
                "LK6ADAE2XLB200524",
                "LK6ADAE27LB200545",
                "LK6ADAE23LB200591",
                "LK6ADAE29LB200529",
                "LK6ADAE22LB200565",
                "LK6ADAE2XLB200541",
                "LK6ADAE29LB200465",
                "LK6ADAE2XLB200538",
                "LK6ADAE22LB200503",
                "LK6ADAE20LB200581"
        };
        String[] Date=new String[]{
                "2020-05-07",
                "2020-05-08",
                "2020-05-09",
                "2020-05-10",
                "2020-05-11",
                "2020-05-12",
                "2020-05-13",
                "2020-05-14"
        };
        testServer.rijunstart(vin,Date);

    }
    //计算日均驾驶时长
    @Test
    void rijunDriving() {
        String[] vin=new String[]{
                "LK6ADAE26LB200505",
                "LK6ADAE23LB200459",
                "LK6ADAE24LB200504",
                "LK6ADAE26LB200603",
                "LK6ADAE25LB200527",
                "LK6ADAE25LB200558",
                "LK6ADAE25LB200561",
                "LK6ADAE28LB200554",
                "LK6ADAE26LB200567",
                "LK6ADAE29LB200580",
                "LK6ADAE23LB200574",
                "LK6ADAE29LB200501",
                "LK6ADAE21LB200587",
                "LK6ADAE22LB200520",
                "LK6ADAE2XLB200524",
                "LK6ADAE27LB200545",
                "LK6ADAE23LB200591",
                "LK6ADAE29LB200529",
                "LK6ADAE22LB200565",
                "LK6ADAE2XLB200541",
                "LK6ADAE29LB200465",
                "LK6ADAE2XLB200538",
                "LK6ADAE22LB200503",
                "LK6ADAE20LB200581"
        };
        String[] Date=new String[]{
                "2020-05-07",
                "2020-05-08",
                "2020-05-09",
                "2020-05-10",
                "2020-05-11",
                "2020-05-12",
                "2020-05-13",
                "2020-05-14"
        };
        testServer.rijunDriving(vin,Date);
    }
}
