package com.jihai3616.activiti.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class DepartMentManager implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("监听JavaDelegate事件:---- class DepartMentManager execute()----部门经理审批  task !!!");
	}

}
