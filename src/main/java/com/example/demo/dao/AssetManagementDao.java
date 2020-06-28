package com.example.demo.dao;

import com.example.demo.model.AssetManagement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetManagementDao {
    //查询表中所有信息
    List<AssetManagement> quearyAsset();
}
