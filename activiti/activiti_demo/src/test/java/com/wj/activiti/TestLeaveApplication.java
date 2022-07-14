package com.wj.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TestLeaveApplication {

    String key =  "leaveApplication";
    /**
     * 部署流程 --RepositoryService
     */
    @Test
    public void testDeploy(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deploy = repositoryService.createDeployment()
                .name("请假申请流程定义")
                .addClasspathResource("bpmn/leaveApplication.bpmn20.xml")
                .deploy();
        log.info("流程部署id:{}",deploy.getId());
        log.info("流程部署名称:{}",deploy.getName());
    }

    /**
     * 流程信息查询 RepositoryService
     */
    @Test
    public void qureyProcessDefinition(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key)
                .orderByProcessDefinitionAppVersion()
                .desc()
                .list();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            log.info("流程定义id:{}",processDefinition.getId());
            log.info("流程定义名称:{}",processDefinition.getName());
            log.info("流程定义key:{}",processDefinition.getKey());
            log.info("流程定义版本:{}",processDefinition.getVersion());
            log.info("流程部署id:{}",processDefinition.getDeploymentId());
        }
    }

    /**
     * 删除流程定义
     * 注意：如果流程没有完成，需要特殊处理：级联删除
     */

    @Test
    public void deletePeocessDefinition(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String deployId = "1";
        repositoryService.deleteDeployment(deployId);

    }

    /**
     * 下载资源文件
     */
    @Test
    public void getResourceFile() throws IOException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> definitionList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key)
                .list();
        for (ProcessDefinition processDefinition : definitionList) {
            System.out.println(processDefinition.getDeploymentId());
            String deploymentId = processDefinition.getDeploymentId();
            String resourceName = processDefinition.getResourceName();
            //获取文件流
            InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, resourceName);

            //构造 outPutStream 流
            File file = new File("/usr/local/var/activiti_file/evectionflow.bpmn");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //输入流、输出流的转换
            IOUtils.copy(resourceAsStream,fileOutputStream);
            //关闭流
            fileOutputStream.close();
            resourceAsStream.close();
        }

    }


    /**
     * 启动流程 --  RuntimeService
     *
     * ACT_HI_TASKINST  任务实例历史信息
     * ACT_HI_PROCINST  流程实例历史信息
     * ACT_HI_ACTINST    活动实例历史信息
     *
     * ACT_HI_IDENTITYLINK 流程的用户参与历史信息
     * ACT_RU_EXECUTION   流程正在执行信息
     * ACT_RU_TASK        任务信息
     * ACT_RU_IDENTITYLINK  流程的参与用户信息
     */
    @Test
    public void testStartProcess(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> map = new HashMap<>();
        map.put("assignee0","张三");
        map.put("assignee1","王经理");
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key,map);

        System.out.println("流程定义id:"+instance.getProcessDefinitionId());
        System.out.println("流程实例 id:"+instance.getId());
        System.out.println("当前活动的id:"+instance.getActivityId());

    }

    /**
     * 查询个人待执行的任务 -- TaskService
     */
    @Test
    public void testFindPersonalTaskList(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee("zhangsan")
                .list();
        for (Task task: taskList) {
            System.out.println("流程实例id:"+task.getProcessInstanceId());
            System.out.println("任务id:"+task.getId());
            System.out.println("任务负责人："+task.getAssignee());
            System.out.println("任务名称："+task.getName());

        }
    }

    /**
     * 完成任务 -- TaskService
     */
    @Test
    public void completeTask(){
//        String assignee = "张三";
        String assignee = "王经理";
        /**
         * 完成任务
         */

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(assignee)
                .singleResult();

        taskService.complete(task.getId());


    }




    /**
     * 查询历史信息
     */
    @Test
    public void  findHistoryInfo(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();

        instanceQuery.processInstanceId("2501");
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        List<HistoricActivityInstance> instanceList = instanceQuery.list();
        for (HistoricActivityInstance hi : instanceList) {
            System.out.println(hi.getActivityId());
            System.out.println(hi.getActivityName());
            System.out.println(hi.getAssignee());
            System.out.println(hi.getProcessDefinitionId());
            System.out.println("=====================");
        }

    }
}
