package com.example.demo.service;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface TestService {
    Object queryAllProcess();

    List<HashMap<String,Object>> queryTasksByProcessId ( String processId);
}
