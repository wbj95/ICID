package com.example.demo.dao;

import com.example.demo.model.BookRegister;
import com.example.demo.model.Staff;
import com.example.demo.model.carInfo;
import com.example.demo.service.BookRegistServer;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Service
public class booKTest {
    @Autowired
    private BookRegisterDao bookRegisterDao;
    private StaffDao staffDao;
    private BookRegistServer bookRegistServer;
    @Test
    void queryAllBook(){
      //  List<BookRegister>  list=bookRegisterDao.querayAllBook();
//bookRegistServer.queryAllBook();
      // System.out.println(list.size());
    }
    @Test
    void queryAllStaff(){
        List<Staff>  li=staffDao.queryAllStaff();

        System.out.println(li.size());
    }

    //更新借出表
    @Test
    void updateBOOK(){
        int  li=bookRegisterDao.updateBook("#01","借出","王五","1362383992984","20200517","20200617");

        System.out.println("结果"+li);
    }
    //查询借书人
    @Test
    void updateName(){
        //int  li=bookRegisterDao.updateBook("#01","借出","王五","1362383992984","20200517","20200617");
        BookRegister b=bookRegisterDao.queryBorrowerName("#03");
        System.out.println("结果"+b.getBookName());
    }
    //还书
    @Test
    void returnbook(){
        //int  li=bookRegisterDao.updateBook("#01","借出","王五","1362383992984","20200517","20200617");
      //  BookRegister b=bookRegisterDao.queryBorrowerName("#03");
       int i= bookRegisterDao.returnbook("#03","在库");
        System.out.println("结果"+i);
    }
}
