package com.accelerator.activitii.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void startTheProcess(String requestId, String objectId, String objectType, String author, String reviewer,
			String approver, String processDefinitionId) {

		Map<String, Object> objectVariables = new HashMap<String, Object>();
		objectVariables.put("requestId", requestId);
		objectVariables.put("objectId", objectId);
		objectVariables.put("objectType", objectType);
		objectVariables.put("author", author);
		objectVariables.put("reviewer", reviewer);
		objectVariables.put("approver", approver);

		//runtimeService.startProcessInstanceByKey(processDefinitionId, objectVariables);
		runtimeService.startProcessInstanceByKey(processDefinitionId, requestId, objectVariables);
		logger.info("process {} is statred ", processDefinitionId);

	}

	public List<Task> getTasks(String assignee) {
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
		return taskList;
	}

	public void completeReviewerTask(String taskId, String reviewerApproved, String objectReviewerReview) {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("reviewerApproved", reviewerApproved);
		variables.put("objectReviewerReview", objectReviewerReview);

		taskService.complete(taskId, variables);

	}

	public void completeApproverTask(String taskId, String approverApproved, String objectApproverReview) {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("approverApproved", approverApproved);
		variables.put("objectApproverReview", objectApproverReview);

		taskService.complete(taskId, variables);

	}

}