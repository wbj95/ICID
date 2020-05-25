package com.example.demo.dao;

import com.example.demo.model.BookRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookRegisterDao {
    //查询所有书籍
   List<BookRegister> querayAllBook();
   //更新数据表
   int updateBook(@Param("bookNo")String bookNo, @Param("state") String state, @Param("borrowerName") String borrowerName,
                  @Param("borrowerIphone") String borrowerIphone, @Param("starttime") String starttime, @Param("endtime") String endtime);
   //查询借书人
    BookRegister queryBorrowerName(@Param("bookNo")String bookNo);
    //还书，更新表
    int returnbook(@Param("bookNo")String bookNo,@Param("state")String state);
    //续借
    int renewBook(@Param("renew")String renew,@Param("bookNo")String bookNo,@Param("starttime") String starttime, @Param("endtime") String endtime);

    //更新数据表，将URL放入表中。
    int updateUrl(@Param("bookImagesUrl")String bookImagesUrl,@Param("bookNo")String bookNo);

    //根据BOOKID查询书名
    BookRegister quearyBookName(@Param("bookNo")String bookNo);
}
