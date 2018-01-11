package com.jihai3616.activiti.demo;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		ProcessEngine processEngine = ProcessEngineConfiguration.
										createStandaloneInMemProcessEngineConfiguration().
										buildProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();
		TaskService taskService = processEngine.getTaskService();

//		for (int i = 0; i < 10; i++) {
//			saveTask(taskService, String.valueOf(i+1));
//		}
//
//		taskService.deleteTask("1");
//		taskService.deleteTask("2", true);
//		taskService.deleteTask("3", "test");
//		List<String> deleteList = new ArrayList<String>();
//		deleteList.add("4");
//		deleteList.add("5");
//		taskService.deleteTasks(deleteList);
//		deleteList = new ArrayList<String>();
//		deleteList.add("6");
//		deleteList.add("7");
//		taskService.deleteTasks(deleteList, true);
//
//		taskService.deleteTask("3");

		// 保存Task
		User user = createUser(identityService, "user1");
		Task task = taskService.newTask("task1");
		task.setName("请假申请");
		taskService.saveTask(task);
		taskService.setOwner(task.getId(), user.getId());

		System.out.println("持有的任务数量：" + taskService.createTaskQuery()
												.taskOwner(user.getId()).count());


	}
	private static void saveTask(TaskService taskService, String taskId) {
		Task task = taskService.newTask(taskId);
		taskService.saveTask(task);
	}

	private static User createUser(IdentityService identityService, String id) {
		User user = identityService.newUser(id);
		user.setFirstName("zou");
		user.setLastName("bingbing");
		user.setEmail("1271704947@qq.com");
		user.setPassword("123456");
		identityService.saveUser(user);
		return identityService.createUserQuery().userId(id).singleResult();
	}
}
