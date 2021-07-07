package com.example.demo.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TestMapper {
    //查看所有流程
    List<HashMap<String,Object>> queryAllProcess ();

    //根据流程id查看任务
    List<HashMap<String,Object>> queryTasksByProcessId (@Param("processId") String processId);
}
