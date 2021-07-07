package com.example.demo.dao;


import com.example.demo.Dto.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Permission> queryPermissionByUserId (@Param("id") Integer id);
}
