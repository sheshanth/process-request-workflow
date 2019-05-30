package com.accelerator.activitii.model;

import java.util.Map;

import org.activiti.engine.task.Task;

public class TaskResponseBody {

	private Task task;
	private Map<String, Object> processVariables;

	public TaskResponseBody() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaskResponseBody(Task task, Map<String, Object> processVariables) {
		super();
		this.task = task;
		this.processVariables = processVariables;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Map<String, Object> getProcessVariables() {
		return processVariables;
	}

	public void setProcessVariables(Map<String, Object> processVariables) {
		this.processVariables = processVariables;
	}

	@Override
	public String toString() {
		return "[taskId=" + task.getId() + ", taskName=" + task.getName()+ ", requestId=" + processVariables.get("requestId") + ", objectId=" + processVariables.get("objectId")+ ", objectType=" + processVariables.get("objectType") +"]";
	}

}
