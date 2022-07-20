package com.wj.activiti.controller;

import com.wj.activiti.entity.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/process")
public class ActivitiController {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private TaskService taskService;


    /**
     * 部署流程定义
     * @param name
     * @param filePath
     * @return
     */
    @PostMapping("deploy")
    public ResponseResult deployProcess(@RequestParam(name = "name") String name,
                                        @RequestParam(name= "filePath") String filePath){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource(filePath)
                .deploy();
        log.info("{} 流程定义完成部署",deploy.getName());
        return ResponseResult.getSuccessResult(deploy);
    }

    /**
     * 查询流程
     * @param key
     * @return
     */
    @GetMapping(value = {"/list/{key}","/list"})
    public ResponseResult getProcessList(@PathVariable(name = "key",required = false) String key) {
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> definitionList;
        if (key!=null){
            definitionList = definitionQuery
                    .processDefinitionKey(key)
                    .list();
        }

       definitionList = definitionQuery.list();

        List<String> processList = new ArrayList<>();
        for (ProcessDefinition processDefinition : definitionList) {
            processList.add(processDefinition.getName());
        }
        return ResponseResult.getSuccessResult(processList);
    }

    /**
     * 启动流程定义（由流程定义-》流程实例）
     * @param key
     * @return
     */
    @PostMapping("start/{key}")
    public ResponseResult startProcess(@PathVariable(name = "key") String key){
        Map<String,Object> map = new HashMap<>();
        map.put("assignee0","Jack");
        map.put("assignee1","Marry");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key,map);
        ResponseResult result =ResponseResult.getSuccessResult(processInstance.getProcessDefinitionName());
        log.info("流程实例的内容：{}",processInstance);
        return result;

    }


    /**
     * 查看任务列表
     * @return
     */
    @GetMapping("/task/list")
    public ResponseResult getMyTaskList(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取当前登录的用户名
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        if (principal instanceof Principal) {
            username = ((Principal) principal).getName();
        }
        //获取任务
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(username)
                .list();
        //获取任务名称列表
        List<String> TaskList = tasks.stream()
                .map(Task::getName)
                .collect(Collectors.toList());

        return ResponseResult.getSuccessResult(TaskList);
    }

    /**
     * 完成任务
     * @param key
     * @param assigne
     * @return
     */
    @PostMapping("complete")
    public ResponseResult doTask(@RequestParam(name = "key") String key,@RequestParam(name = "assignee")String assigne){
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(key)
                .taskAssignee(assigne)
                .list();
        if (tasks!=null && tasks.size()>0){
            for (Task task : tasks) {
                log.info("任务名称：{}",task.getName());
                taskService.complete(task.getId());
                log.info("{}，任务已完成",task.getName());

            }
        }
        return ResponseResult.getSuccessResult(null);

    }

    /**
     * 删除部署
     * @param deploymentId
     * @return
     */
    @PostMapping("delete/{id}")
    public ResponseResult deleteDeployment(@PathVariable(name = "id") String deploymentId){
        /**
         * deleteDeployment() 方法的第二个参数 cascade 设置为 true,表示需要进行级联删除，从而可以删除掉未完成的任务
         */
        repositoryService.deleteDeployment(deploymentId,true);
        return ResponseResult.getSuccessResult(null);
    }
}
