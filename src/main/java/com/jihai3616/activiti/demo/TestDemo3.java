package com.jihai3616.activiti.demo;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public class TestDemo3 {

	public static void main(String[] args) {
		// 创建流程引擎
		ProcessEngine processEngine = ProcessEngineConfiguration.
				createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();

		// 部署流程定义文件
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String bpmnFile = "Demo3.bpmn";
		Deployment dp = repositoryService.createDeployment().addClasspathResource(bpmnFile).deploy();

		// 部署的流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.deploymentId(dp.getId()).singleResult();
		System.out.println(processDefinition.getKey());

		// 启动流程并返回流程定义
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Demo3");

//		TaskService taskService = processEngine.getTaskService();
//		// 这里查询的任务结果要唯一  ：否则会报诸如此类的错Query return 3 results instead of max 1
//		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
//		if(task != null) {
//			System.out.println("流程执行到：" + task.getName());
//		}

		// 通过流程引擎获取历史记录查询接口，统计已完成的流程实例数量
		HistoryService historyService = processEngine.getHistoryService();
		long count = historyService.createHistoricProcessInstanceQuery().count();
		System.out.println("已完成的流程数量：" + count);
	}
}
