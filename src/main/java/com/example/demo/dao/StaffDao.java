package com.example.demo.dao;

import com.example.demo.model.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StaffDao {
    //查询所有职员
    List<Staff> queryAllStaff();
    //根据用户名查询
    List<Staff> queryStaffByName(@Param("staffName") String staffName);
}
