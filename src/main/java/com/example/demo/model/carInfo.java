package com.example.demo.model;

public class carInfo {
    //主键
    private int id;
    //项目名
    private String projectName;
    //VIN号
    private String vin;
    //借用人private
    private String borrower;
    //配置
    private String configuration;
    //类型
    private int vehicleType;
    //状态
    private String state;
    //城市
    private String city;
    //周里程
    private int dataMileage;
    //周启动次数
   private  int dataStart;
   //电话号码
    private  String iphoneNum;
    //经度
    private  String longitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    //纬度
    private  String latitude;
    public String getIphoneNum() {
        return iphoneNum;
    }

    public void setIphoneNum(String iphoneNum) {
        this.iphoneNum = iphoneNum;
    }

    public String getCity() {
        return city;
    }

    public int getDatamileage() {
        return dataMileage;
    }

    public void setDatamileage(int datamileage) {
        this.dataMileage = datamileage;
    }

    public int getDataStart() {
        return dataStart;
    }

    public void setDataStart(int dataStart) {
        this.dataStart = dataStart;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    //地址
    private String Address;

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

}
