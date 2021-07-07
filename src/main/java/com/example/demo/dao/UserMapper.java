package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {

    Integer queryUser (@Param("name") String name , @Param("pwd") String pwd);

    Integer queryLastTaskId ();

    void insertTaskHis(@Param("taskId") Integer taskId , @Param("userId") Integer userId);

    List<TaskInfo> queryTaskHis (@Param("name")String name);
    Integer queryTaskId (@Param("id") Integer id);

    void updateState(@Param("id") Integer id);
}
