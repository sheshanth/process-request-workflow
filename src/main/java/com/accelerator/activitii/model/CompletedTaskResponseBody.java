package com.accelerator.activitii.model;

import org.activiti.engine.history.HistoricTaskInstance;

public class CompletedTaskResponseBody {

	private HistoricTaskInstance task;
	//private Map<String, Object> processVariables;

	public CompletedTaskResponseBody() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompletedTaskResponseBody(HistoricTaskInstance task) {
		// TODO Auto-generated constructor stub
		this.task = task;
	}

	public HistoricTaskInstance getTask() {
		return task;
	}

	public void setTask(HistoricTaskInstance task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "CompletedTaskResponseBody [task=" + task + "]";
	}
	
}
