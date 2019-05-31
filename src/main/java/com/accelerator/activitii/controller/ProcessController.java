package com.accelerator.activitii.controller;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.accelerator.activitii.model.CompletedTaskResponseBody;
import com.accelerator.activitii.model.TaskResponseBody;
import com.accelerator.activitii.service.ProcessService;

@RestController
public class ProcessController {

	@Autowired
	private ProcessService processService;

	// requestId - Auto generated from the caller
	// objectID - Object Type : ID eg: Lot - 2133, Campaign - 4445
	// objectType -
	// author -
	// reviewer -
	// approver -

	// Endpoint : /process-request method : POST ,..above is the body.

	@PostMapping(value = "/process-request")
	public String startObjectReviewProcess(@RequestBody(required = true) Map<String, Object> parameters) {

		processService.startTheProcess(parameters, "objApprovalProcess");
		
		//processService.activeInstanceTask();

		return "Process with requestId " + parameters.get("requestId") + " started.";
	}

	/*
	 * All takss that are waiting for this person /process-requests/<<person>>
	 * 
	 * 
	 * Response :
	 * 
	 * Using the requestID find the user task which is pending. Task ID : Task Name
	 * : <<Name of the task>> Review / Approve Request ID : Object ID : Lot -1333
	 * 
	 */

	@GetMapping(value = "/process-request/{assignee}")
	public String getTask(@PathVariable(name = "assignee") String assignee) {

		List<TaskResponseBody> taskList = processService.getTasks(assignee);

		return taskList.toString();
	}

	@GetMapping(value = "/process-request/{assignee}/requestId/{requestId}")
	public String getTask(@PathVariable(name = "assignee") String assignee,
			@PathVariable(name = "requestId") String requestId) {

		List<TaskResponseBody> taskList = processService.getAssigneeTaskByRequestId(assignee, requestId);

		return taskList.toString();
	}

	
	@GetMapping(value = "/process-request/{assignee}/objectId/{objectId}")
	public String getAssigneeTaskByObjectIdTask(@PathVariable(name = "assignee") String assignee,
			@PathVariable(name = "objectId") String objectId) {

		List<TaskResponseBody> tasklist = processService.getAssigneeTaskByObjectId(assignee, objectId);
		
		return tasklist.toString();
	}

	/*
	 * End point : /process-request/{{requestID}} Body : { requestID : 1332,
	 * taskType : review status : approve / reject comments : This is fooggg. }
	 * 
	 * Using the requestID find the user task which is pending. Response should give
	 * the task ID and then complete it based on the task id
	 * 
	 */

	@PostMapping(value = "/process-request/review")
	public String reviewTask(@RequestBody Map<String, Object> parameters) {
		processService.completeReview(parameters);
		return "task completed";
	}

	@PostMapping(value = "/process-request/approve")
	public String approveTask(@RequestBody Map<String, Object> parameters) {
		processService.completeApproval(parameters);
		return "task completed";
	}

	@GetMapping(value = "/process-request/completed/{assignee}")
	public List<CompletedTaskResponseBody> completedTaskByAssignee(@PathVariable(name = "assignee") String assignee) {
		return processService.completedTaskByAssignee(assignee);
	}
	
}