package com.example.demo.model;

public class BookRegister {
    //书ID
    private Integer idbookRegister;
    //书名
    private String bookName;
    //书编号
    private String bookNo;
    //借阅状态
    private String state;
    //借书人
    private String borrowerName;
    //借阅人电话
    private String borrowerIphone;
    //开始借阅时间
    private String startTime;
    //结束借阅时间
    private String endTime;
    //续借次数
    private String renew;
    //书本详细描述
    private String describle;
    //封面url

    private String bookImagesUrl;



    public String getBookImagesUrl() {
        return bookImagesUrl;
    }

    public void setBookImagesUrl(String bookImagesUrl) {
        this.bookImagesUrl = bookImagesUrl;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public String getRenew() {
        return renew;
    }

    public void setRenew(String renew) {
        this.renew = renew;
    }

    public Integer getIdbookRegister() {
        return idbookRegister;
    }

    public void setIdbookRegister(Integer idbookRegister) {
        this.idbookRegister = idbookRegister;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerIphone() {
        return borrowerIphone;
    }

    public void setBorrowerIphone(String borrowerIphone) {
        this.borrowerIphone = borrowerIphone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
