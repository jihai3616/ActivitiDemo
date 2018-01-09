package com.jihai3616.activiti.demo;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.Map;

public class ActivitiHelloWorldDemo2 {

	public static void main(String[] args) {
		// 创建流程引擎，使用内存数据库
		ProcessEngine processEngine = ProcessEngineConfiguration.
				createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();

		// 部署流程定义文件
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String bpmnFile = "SayHelloToLeave.bpmn";
		repositoryService.createDeployment().addClasspathResource(bpmnFile).deploy();
//		repositoryService.createDeployment().
//				addInputStream("SayHelloToLeave.bpmn", this.getClass().getClassLoader().getResourceAsStream(bpmn))
//				.deploy();

		// 验证已部署的流程定义 -- 不是在测试可以不验证
//		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
//		System.out.println(processDefinition.getKey());

		// 启动流程并返回流程定义
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("applyUser", "zoubb");
		vars.put("days", 3);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Demo2", vars);
		if (processInstance != null) {
			System.out.println("pid = " + processInstance.getId() +
								", pdid = " + processInstance.getProcessDefinitionId());
		}

		// 运行到这里代表什么？为什么没有输出审批结果

		// 查询组（Group）deptLeader的未签收任务  并验证任务的名称
		TaskService taskService = processEngine.getTaskService();
		Task leaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		if(leaderTask != null) {
			System.out.println("流程执行到哪个了：" + leaderTask.getName());
		}
		// 通过claim方法签收该任务归用户leader所有
		taskService.claim(leaderTask.getId(), "leader");
		vars = new HashMap<String, Object>();
		vars.put("approved", true);
		// 完成任务的同时以流程变量的形式设置审批结果
		taskService.complete(leaderTask.getId(), vars);

		// 这里只是为了让读者更好的明白执行结果，因为流程已经走完，再次查询组deptLeader的任务已经为空
		leaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		if(leaderTask == null)
			System.out.println("你猜对了，流程已经执行完了");

		// 通过流程引擎获取历史记录查询接口，统计已完成的流程实例数量
		HistoryService historyService = processEngine.getHistoryService();
		long count = historyService.createHistoricProcessInstanceQuery().finished().count();
		System.out.println("count = " + count);
	}
}
