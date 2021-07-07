package com.example.demo.controller;

import com.example.demo.common.Constant;
import com.example.demo.dao.TaskInfo;
import com.example.demo.dao.UserMapper;
import com.example.demo.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Api(description = "activiti")
@RestController
@RequestMapping("/act")
public class ActivitiControler {
    @Autowired
    private TestService testService;

    @Autowired
    private UserMapper mapper;

    @ApiOperation(value = "部署流程", httpMethod = "POST", response = String.class, notes = "部署之后就可以在act_re_procdef表中看到对相应的流程信息")
    @ApiImplicitParams({
//            @ApiImplicitParam(name="accountCode", value="accountCode", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/deployProcess")
    public String deployProcess(
//            @RequestParam(value = "accountCode", defaultValue = "")String accountCode
    ){
        RepositoryService repositoryService = Constant.processEngine.getRepositoryService();
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("test.bpmn");//bpmn文件的名称
        builder.deploy();
        return "ok";
    }
    @ApiOperation(value = "驳回", httpMethod = "POST", response = String.class, notes = "驳回")
    @ApiImplicitParams({
            @ApiImplicitParam(name="taskId", value="accountCode", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/reject")
    public String reject(
            @RequestParam(value = "taskId", defaultValue = "")String taskId
    ){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        //设置流程变量day=3
        paramMap.put("a",  200   );


        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService().complete(taskId , paramMap);
        return "ok";
    }


    @ApiOperation(value = "查看所有流程", httpMethod = "POST", response = String.class, notes = "部署之后就可以在act_re_procdef表中看到对相应的流程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="processId", value="processId", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/queryAllProcess")
    public Object queryAllProcess(
    ){
        Object result = testService.queryAllProcess();
        return result;
    }
    @ApiOperation(value = "根据流程id查看任务", httpMethod = "POST", response = String.class, notes = "根据流程id查看任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="processId", value="processId", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/queryTasksByProcessId")
    public Object queryTasksByProcessId(
            @RequestParam(value = "processId", defaultValue = "")String processId
    ){
        Object result = testService.queryTasksByProcessId(processId);
        return result;
    }



    @ApiOperation(value = "启动流程", httpMethod = "POST", response = String.class, notes = "存在act_ru_task表中，可以查看任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="processId", value="processId", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/startProcess")
    public String startProcess(
            @RequestParam(value = "processId", defaultValue = "")String processId
    ){
        RuntimeService runtimeService = Constant.processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey(processId);//流程的名称，也可以使用ByID来启动流程
        return "ok";
    }

    @ApiOperation(value = "根据代理人查看任务", httpMethod = "POST", response = Object.class, notes = "查看任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="assignee", value="assignee", dataType="String", paramType="query", required=true),
            @ApiImplicitParam(name="user", value="user", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/queryTask")
    public Object queryTask(
            @RequestParam(value = "assignee", defaultValue = "")String assignee,
            @RequestParam(value = "user", defaultValue = "")String user
    ){
        TaskService taskService = Constant.processEngine.getTaskService();
        //根据assignee(代理人)查询任务
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();

        int size = tasks.size();
        for (int i = 0; i < size; i++) {
            Task task = tasks.get(i);

        }
        //首次运行的时候这个没有输出，因为第一次运行的时候扫描act_ru_task的表里面是空的，但第一次运行完成之后里面会添加一条记录，之后每次运行里面都会添加一条记录
        List<HashMap<String,Object>> maps = new ArrayList<>();

        for (Task task : tasks) {
            System.out.println("taskId:" + task.getId() +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + task.getCreateTime());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("taskId",task.getId());
            map.put("taskName",task.getName());
            map.put("assignee",task.getAssignee());
            map.put("createTime",task.getCreateTime());
            maps.add(map);
        }

        tasks.toString();
        return maps;
    }
    @ApiOperation(value = "根据代理人查看任务", httpMethod = "POST", response = Object.class, notes = "查看任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="assignee", value="assignee", dataType="String", paramType="query", required=true),
            @ApiImplicitParam(name="user", value="user", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/queryTask2")
    public Object queryTask2(
            @RequestParam(value = "assignee", defaultValue = "")String assignee,
            @RequestParam(value = "user", defaultValue = "")String user
    ){
        List<TaskInfo> taskInfos = mapper.queryTaskHis(user);
        return taskInfos;
    }

    @ApiOperation(value = "完成任务", httpMethod = "POST", response = Object.class, notes = "完成任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="taskId", value="taskId", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/completePersonalTask")
    public Object completePersonalTask(
            @RequestParam(value = "taskId", defaultValue = "")String taskId
    ){


        Integer integer = mapper.queryTaskId(Integer.valueOf(taskId));
        mapper.updateState(integer);

        Constant.processEngine.getTaskService()
                .complete(taskId);
//        Constant.processEngine.getTaskService().gatew
        return "ok";
    }

    @ApiOperation(value = "查询流程状态（判断流程是正在执行还是结束）", httpMethod = "POST", response = Object.class, notes = "查询流程状态（判断流程是正在执行还是结束）")
    @ApiImplicitParams({
            @ApiImplicitParam(name="processId", value="processId", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/isProcessEnd")
    public Object isProcessEnd(
            @RequestParam(value = "processId", defaultValue = "")String processId
    ){
        ProcessInstance pi = Constant.processEngine.getRuntimeService()//表示正在执行的流程实例和执行对象
                .createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(processId)//使用流程实例ID查询
                .singleResult();
        if(pi==null){
            return "结束";
        }
        else{
           return "没有结束";
        }
    }

    @ApiOperation(value = "查询代理人历史任务", httpMethod = "POST", response = Object.class, notes = "查询代理人历史任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="assignee", value="assignee", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/findHistoryTask")
    public Object findHistoryTask(
            @RequestParam(value = "assignee", defaultValue = "")String assignee
    ){
        List<HistoricTaskInstance> list = Constant.processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                .taskAssignee(assignee)//指定历史任务的办理人
                .list();
        if(list!=null && list.size()>0){
           return "没有任务";
        }

        return list.toString();
    }

    @ApiOperation(value = "查询历史流程实例", httpMethod = "POST", response = Object.class, notes = "查询历史流程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name="processId", value="processId", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/findHistoryProcessInstance")
    public String findHistoryProcessInstance(
            @RequestParam(value = "processId", defaultValue = "")String processId
    ){
        String processInstanceId = processId;
        HistoricProcessInstance hpi = Constant.processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricProcessInstanceQuery()//创建历史流程实例查询
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        if(hpi == null){
            return "没有历史";
        }else{
            String  str= hpi.getId()+"    "+hpi.getProcessDefinitionId()+"    "+hpi.getStartTime()+"    "+hpi.getEndTime()+"     "+hpi.getDurationInMillis();
            return  str;
        }
    }



}
