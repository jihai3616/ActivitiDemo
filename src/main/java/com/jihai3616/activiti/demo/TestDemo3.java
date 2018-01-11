package com.jihai3616.activiti.demo;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class TestDemo3 {

	public static void main(String[] args) {
		// 1.创建流程引擎
		ProcessEngine processEngine = ProcessEngineConfiguration.
				createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();
		// 2.如果需要可以在创建流程引擎的时候加入activiti.cfg.xml文件
//		ProcessEngine processEngine = ProcessEngineConfiguration
//										.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
//										.buildProcessEngine();
		// 3.通过ProcessEngines创建流程引擎
//		ProcessEngines.init();// 初始化ProcessEngines的Map,
//		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();// 从map取出

		// 部署流程定义文件
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Deployment dp = repositoryService.createDeployment().addClasspathResource("Demo3.bpmn").deploy();
		System.out.println(dp.getId());

		// 部署的流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
												.deploymentId(dp.getId()).singleResult();
		// 测试证明：对应流程definitions中的 id
		System.out.println("ProcessDefinition Key:" + processDefinition.getKey());
		// 测试证明：对应流程definitions中的 id:版本号(可以通过processDefinition.getVersion()看到):流程定义在数据库中的id
		System.out.println("processDefinition Id:" + processDefinition.getId());
		// 测试证明：对应流程definitions中的 Name
		System.out.println("processDefinition Name: " + processDefinition.getName());
		System.out.println(processDefinition.getCategory() + " " + processDefinition.getDeploymentId()
							+" " + processDefinition.getEngineVersion() + " " + processDefinition.getDescription()
							+" " + processDefinition.getDiagramResourceName() + " " + processDefinition.getResourceName()
							+ " " + processDefinition.getTenantId() + " " + processDefinition.getVersion() + "!");

		// 启动流程并返回流程定义
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Demo3");

		// 估计就是数据定义对应的Id
		System.out.println("ProcessInstance Id: " + processInstance.getId());
		// name=null竟然没有...
		System.out.println("ProcessInstance Name: " + processInstance.getName());
		// 下面这两个就是ProcessDefinition对应的项
		System.out.println("ProcessInstance Id: " + processInstance.getProcessDefinitionId());
		System.out.println("ProcessInstance Key: " + processInstance.getProcessDefinitionKey());
		// DeploymentId也是null...amazing,,,跟Deployment竟然不一样
		System.out.println("ProcessInstance : " + processInstance.getDeploymentId());
		System.out.println(processInstance.getDescription() + "*" + processInstance.getTenantId() + "#" + processInstance.getBusinessKey()
							+ "$" + processInstance.getStartUserId() + "<" + processInstance.getLocalizedName() + ">" +
							processInstance.getStartTime());

		TaskService taskService = processEngine.getTaskService();
		// 这里查询的任务结果要唯一  ：否则会报诸如此类的错Query return 3 results instead of max 1
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("张三").singleResult();
		// 估计就是数据定义对应的 Id
		System.out.println("Task Id: " + task.getId());
		// 测试证明：对应流程userTask中的 name
		System.out.println("Task Name: " + task.getName());
		// owner和Assignee差别是什么？
		System.out.println("Task Owner: " + task.getOwner());
		System.out.println("Task Assignee: " + task.getAssignee());
		// 测试证明：task.getTaskDefinitionKey()对应流程userTask中的 id
		System.out.println(task.getTaskDefinitionKey() + "!" + task.getCategory() + "@" + task.getExecutionId() + "#"
							+task.getDescription() + "%" + task.getFormKey() + "&" +task.getPriority() + "*" +task.getParentTaskId());

		if(task != null) {
			System.out.println("流程执行到：" + task.getName() + "  " +  task.getId());
		}
		taskService.complete(task.getId());


		// 通过流程引擎获取历史记录查询接口，统计已完成的流程实例数量
		HistoryService historyService = processEngine.getHistoryService();
		long count = historyService.createHistoricProcessInstanceQuery().count();
		System.out.println("已完成的流程数量：" + count);
	}
}
