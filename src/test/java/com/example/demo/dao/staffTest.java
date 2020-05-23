package com.example.demo.dao;

import com.example.demo.model.Staff;
import com.example.demo.service.BookRegistServer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Service
public class staffTest {
    @Autowired
    private StaffDao staffDao;
    BookRegistServer bookRegistServer;
    @Test
    void queryAllStaff(){
        List<Staff> li=staffDao.queryAllStaff();

        System.out.println(li.size());
    }
    @Test
    void queryStaffByName(){
//        Staff name=staffDao.queryStaffByName("韦保俊");
//
//        if (name!=null){
//            System.out.println(name.getStaffName());
//        }else {
//            System.out.println("空值");
//        }
        List<Staff> lr=staffDao.queryStaffByName("韦保俊");
        System.out.println(lr.size());

        bookRegistServer.borrowBook("韦保俊","123123","#03");
    }
}
