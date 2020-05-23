package com.example.demo.service;

import com.example.demo.dao.StaffDao;
import com.example.demo.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServer {
    @Autowired
    StaffDao staffDao;
    //查询是否是本部门的员工
    public boolean isStaff(String name){
        boolean res=false;

        List<Staff> list=staffDao.queryStaffByName(name);
        if (list.size()==0){

        }else {
            res=true;
        }
        return res;
    }
}
