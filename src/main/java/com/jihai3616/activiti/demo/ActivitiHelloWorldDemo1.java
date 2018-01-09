package com.jihai3616.activiti.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public class ActivitiHelloWorldDemo1 {

	public static void main(String[] args) {
		// 创建流程引擎，使用内存数据库
		ProcessEngine processEngine = ProcessEngineConfiguration.
				createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();

		// 部署流程定义文件
		RepositoryService repositoryService =  processEngine.getRepositoryService();
		String bpmn = "sayhelloleave.bpmn";
		repositoryService.createDeployment().addClasspathResource(bpmn).deploy();

		// 验证已部署的流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
		System.out.println(processDefinition.getKey());

		// 启动流程并返回流程定义
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Demo1");
		if(processDefinition != null) {
			System.out.println("pid = " + processInstance.getId() +
								", pdid = " + processInstance.getProcessDefinitionId());
		}

	}
}
