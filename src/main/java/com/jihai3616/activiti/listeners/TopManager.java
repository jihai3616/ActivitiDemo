package com.jihai3616.activiti.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.JavaDelegate;

public class TopManager implements ExecutionListener{

//	@Override
//	public void execute(DelegateExecution execution) {
//		System.out.println("监听JavaDelegate事件:---- class TopManager execute()----总经理审批  task !!!");
//	}

	@Override
	public void notify(DelegateExecution delegateExecution) {
		System.out.println("监听JavaDelegate事件:---- class TopManager execute()----总经理审批  task !!!");
	}
}
