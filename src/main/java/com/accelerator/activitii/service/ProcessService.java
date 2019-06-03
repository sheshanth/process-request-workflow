package com.accelerator.activitii.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accelerator.activitii.model.CompletedTaskResponseBody;
import com.accelerator.activitii.model.TaskResponseBody;

@Service
public class ProcessService {

	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private IdentityService identityService;

	// start the process
	public void startTheProcess(Map<String, Object> parameters, String processDefinitionId) {
		
		//parameters.put("assigneeList", Arrays.asList("kermit","fozzo"));

		runtimeService.startProcessInstanceByKey(processDefinitionId, (String) parameters.get("requestId"), parameters);
		logger.info("process {} is started ", processDefinitionId);

	}

	// get all tasks by assignee
	public List<TaskResponseBody> getTasks(String assignee) {
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();

		List<TaskResponseBody> responseTaskList = new ArrayList<TaskResponseBody>();

		taskList.forEach(task -> {
			String taskId = task.getId();
			Map<String, Object> processVariables = taskService.getVariables(taskId);
			responseTaskList.add(new TaskResponseBody(task, processVariables));
		});
		
		return responseTaskList;
	}

	// get assignee task by requestId
	public List<TaskResponseBody> getAssigneeTaskByRequestId(String assignee, String requestId) {
		List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(requestId).taskAssignee(assignee)
				.list();

		List<TaskResponseBody> responseTaskList = new ArrayList<TaskResponseBody>();

		taskList.forEach(task -> {
			String taskId = task.getId();
			Map<String, Object> processVariables = taskService.getVariables(taskId);
			responseTaskList.add(new TaskResponseBody(task, processVariables));
		});

		return responseTaskList;
	}

	// get task by objectId for assignee
	public List<TaskResponseBody> getAssigneeTaskByObjectId(String assignee, String objectId) {

		List<Task> taskList = taskService.createTaskQuery().processVariableValueEquals(objectId).taskAssignee(assignee)
				.list();
		
		List<TaskResponseBody> responseTaskList = new ArrayList<TaskResponseBody>();
		
		taskList.forEach(task -> {
			String taskId = task.getId();
			Map<String, Object> processVariables = taskService.getVariables(taskId);
			responseTaskList.add(new TaskResponseBody(task, processVariables));
		});
		
		return responseTaskList;
	}

	// complete review
	public String completeReview(Map<String, Object> parameters) {
		List<Task> task = taskService.createTaskQuery().processInstanceBusinessKey((String) parameters.get("requestId"))
				.taskAssignee((String) parameters.get("assignee")).list();

		String taskId = task.get(0).getId();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("reviewerApproved", parameters.get("reviewerApproved"));
		variables.put("reviewerComments", parameters.get("reviewerComments"));

		taskService.complete(taskId, variables);

		return null;
	}

	// complete approval
	public String completeApproval(Map<String, Object> parameters) {
		List<Task> task = taskService.createTaskQuery().processInstanceBusinessKey((String) parameters.get("requestId"))
				.taskAssignee((String) parameters.get("assignee")).list();

		String taskId = task.get(0).getId();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("approverApproved", parameters.get("approverApproved"));
		variables.put("approverComments", parameters.get("approverComments"));

		taskService.complete(taskId, variables);

		return null;
	}

	// completed task by assignee
	public List<CompletedTaskResponseBody> completedTaskByAssignee(String assignee) {
		List<HistoricTaskInstance> completedTask = historyService.createHistoricTaskInstanceQuery().finished()
				.taskAssignee(assignee).list();

		List<CompletedTaskResponseBody> responseTaskList = new ArrayList<CompletedTaskResponseBody>();

		completedTask.forEach(task -> {
			// String taskId = task.getId();
			System.out.println(task.getName());
			// Map<String, Object> processVariables = taskService.getVariables(taskId);
			responseTaskList.add(new CompletedTaskResponseBody(task));
		});

		return responseTaskList;
	}

	// active instance current task
	public void activeInstanceTask() {
		System.out.println(taskService.createTaskQuery().orderByProcessInstanceId().asc().active().list().toString());

		System.out.println(historyService.createHistoricProcessInstanceQuery().finished().list().toString());
	}

}