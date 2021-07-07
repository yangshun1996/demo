package com.example.demo;

import com.example.demo.common.Constant;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@MapperScan("com.example.demo.dao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
//        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.example.activiti.dao").buildProcessEngine();

        Constant.processEngine = processEngine;

        System.out.println(Constant.processEngine);

    }

}
