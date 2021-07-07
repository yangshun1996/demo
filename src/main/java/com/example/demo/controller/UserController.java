package com.example.demo.controller;


import com.example.demo.Dto.Permission;
import com.example.demo.common.Constant;
import com.example.demo.dao.PermissionMapper;
import com.example.demo.dao.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.StyledEditorKit;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@Api(description = "activiti")
@RestController
@RequestMapping("/user")
public class UserController {
    HashMap<String,Object> sessionMap = new HashMap<>();
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @ApiOperation(value = "登录", httpMethod = "POST", response = String.class, notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name", value="name", dataType="String", paramType="query", required=true),
            @ApiImplicitParam(name="pwd", value="pwd", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/login")
    public Object queryAllProcess(
            @RequestParam(value = "name", defaultValue = "")String name,
            @RequestParam(value = "pwd", defaultValue = "")String pwd
    ){
        Integer id = userMapper.queryUser(name, pwd);
        if (id == null){
            return "no";
        }
        List<Permission> hashMaps = permissionMapper.queryPermissionByUserId(id);
        sessionMap.put(name + "roleName" , hashMaps.get(0).getRoleName());
        sessionMap.put(name + "id" , id);
        return hashMaps;
    }

    @ApiOperation(value = "申请", httpMethod = "POST", response = String.class, notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name", value="name", dataType="String", paramType="query", required=true),
//            @ApiImplicitParam(name="pwd", value="pwd", dataType="String", paramType="query", required=true),
    })
    @RequestMapping("/apply")
    public String queryAllProcess(
            @RequestParam(value = "name", defaultValue = "")String name
//            @RequestParam(value = "pwd", defaultValue = "")String pwd
    ){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        RuntimeService runtimeService = Constant.processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("myProcess_1");//流程的名称，也可以使用ByID来启
        TaskService taskService = Constant.processEngine.getTaskService();
        //根据assignee(代理人)查询任务
        String roleName  = (String) sessionMap.get(name + "roleName");
       Integer  id = (Integer) sessionMap.get(name + "id" );
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(roleName).list();// 动流程

        Integer taskId = userMapper.queryLastTaskId();

        userMapper.insertTaskHis(taskId,id);
        for (Task task : tasks) {
            if("apply".equals(task.getName())){
                Constant.processEngine.getTaskService()
                        .complete(task.getId());
            }
        }
        return "ok";
    }
}
