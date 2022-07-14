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
import java.util.List;
@Slf4j
public class TestCreat {
    /**
     * 数据库表结构的创建
     */
    @Test
    public void testCreateDbTable(){
        /**
         * 创建 processEngine 时，会自动加载 /resources 目录下的 activiti.cfg.xml 文件
         * 进行 mysql 数据库中表的创建
         */
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        log.info(processEngine.getName());
    }

}
