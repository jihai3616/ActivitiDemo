package com.jihai3616.activiti.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class Person implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("监听JavaDelegate事件:---- class Person execute()----个人请假申请  task !!!");
	}

}
