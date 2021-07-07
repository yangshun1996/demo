package com.example.demo.service.impl;

import com.example.demo.dao.TestMapper;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestMapper testMapper;

    @Override
    public Object queryAllProcess() {
        return testMapper.queryAllProcess();
    }

    @Override
    public List<HashMap<String, Object>> queryTasksByProcessId(String processId) {
        return testMapper.queryTasksByProcessId(processId);
    }


}
