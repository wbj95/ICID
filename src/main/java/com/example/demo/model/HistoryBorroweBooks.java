package com.example.demo.model;

public class HistoryBorroweBooks {

    //主键ID
    private String idhistoryBorrowBooks;
    private String bookNo;
    private String bookName;
    private String borrowerName;
    private String starttime;
    private String endtime;

    public String getIdhistoryBorrowBooks() {
        return idhistoryBorrowBooks;
    }

    public void setIdhistoryBorrowBooks(String idhistoryBorrowBooks) {
        this.idhistoryBorrowBooks = idhistoryBorrowBooks;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
