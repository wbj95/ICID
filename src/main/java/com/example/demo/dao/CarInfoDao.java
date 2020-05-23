package com.example.demo.dao;

import com.example.demo.model.carInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface CarInfoDao {
    //查询所有车辆
    List<carInfo> queryCarInfo();
    //通过VIN号查询车辆信息
    carInfo queryCarInfoByVin(String vin);
    //通过ID修改车辆信息
    int updateCarInfoByID(@Param("ID")int ID,@Param("projectName") String projectName, @Param("vin") String vin, @Param("borrower") String borrower,
                          @Param("configuration") String configuration,@Param("state") String state);
    //删除车辆
    int deleteCarInfoByID(int ID);
    //增加车辆
//    int insertCarInfo(@Param("vin")String vin,@Param("projectName")String projectName,@Param("borrower")String borrower,
//                      @Param("longitude")String longitude,@Param("latitude")String latitude,@Param("batElectric")String batElectric,
//                      @Param("remainMileage")String remainMileage);
    int insertCarInfo(carInfo carInfo);
    //通过VIN号修改车辆所在城市、地址
    int updateCarCityAndAddressByVin(@Param("city")String city,@Param("address") String address, @Param("vin") String vin);
    //通过vin号更新车辆周里程和周启动次数
    int updataCarMileageAndStartByVin(@Param("datamileage")int datamileage,@Param("dataStart") int dataStart, @Param("vin") String vin);
    //按照周里程降序读取
    List<carInfo> queryCarInfoByMileage();
    //按照周启动次数降序读取
    List<carInfo> queryCarInfoByStart();
    //根据vin号将启动次数赋值为NULL
    int updateCarstartNumByVin( @Param("vin") String vin);
    //根据VIN号更新里程
    int updateMileageByVin(@Param("datamileage")int datamileage, @Param("vin") String vin);
    //更新借车人信息
    int updateBorrow(@Param("vin") String vin, @Param("borrower")String borrower, @Param("phoneNum")String phoneNum);

    //通过vin号模糊查询
    List<carInfo> fuzzyQueryCarInfo(@Param("vin") String vin);

    //将获得的经纬度插入数据库中
    int updateLocation(@Param("longitude")String longitude,@Param("latitude")String latitude,@Param("vin")String vin);
}
