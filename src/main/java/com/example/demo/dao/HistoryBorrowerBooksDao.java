package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HistoryBorrowerBooksDao {
    //更新历史借书表,插入
    int updateHistory(@Param("bookNo")String bookNo, @Param("bookName")String bookName,@Param("borrowerName")String borrowerName,
                  @Param("starttime")String starttime,@Param("endtime")String endtime);
}
