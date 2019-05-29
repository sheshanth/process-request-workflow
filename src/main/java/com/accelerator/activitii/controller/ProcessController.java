package com.accelerator.activitii.controller;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.accelerator.activitii.service.ProcessService;

@RestController
public class ProcessController {

	@Autowired
	private ProcessService processService;

	/*
	 * Method will start the Activiti process engine and set employee to perform the
	 * task
	 */
	/*
	 * @RequestMapping(value = "/process") public String
	 * startProcessInstance(@RequestParam(name = "assignee") String
	 * assignee, @RequestParam(name = "process") String process) { return
	 * processService.startTheProcess(assignee, process); }
	 * 
	 * // Retrieve the tasks assigned to an employee
	 * 
	 * @RequestMapping(value = "/tasks") public String getTasks(@RequestParam String
	 * assignee) { List<Task> tasks = processService.getTasks(assignee); return
	 * tasks.toString(); }
	 * 
	 * // Complete the task by their ID
	 * 
	 * @RequestMapping(value = "/completetask") public String
	 * completeTask(@RequestParam String taskId) {
	 * processService.completeTask(taskId); return "Task with id " + taskId +
	 * " has been completed!"; }
	 * 
	 * @GetMapping(value = "/test") public String testApi(@RequestParam(name =
	 * "name") String name, @RequestParam(name = "age") int age) { return "name: "+
	 * name + "age: "+ age;
	 * 
	 * }
	 * 
	 * @GetMapping(value = "/all-task") public String allTasks(@RequestParam(name =
	 * "name") String name) { String assignee = name; return
	 * processService.allTask(assignee); }
	 * 
	 * 
	 * @GetMapping(value = "/start-vacation-process") public String
	 * startVacationProcess(@RequestParam(name = "processId") String
	 * processId, @RequestParam(name = "days") int days, @RequestParam(name =
	 * "employeeName") String employeeName) {
	 * 
	 * String tasks = processService.startVacationRequest(processId, employeeName,
	 * days);
	 * 
	 * return "Process started" + tasks;
	 * 
	 * }
	 * 
	 * 
	 * @GetMapping(value = "/get-vacation-tasks") public String
	 * getVacationTasks(@RequestParam(name="assignee") String assignee) { List<Task>
	 * taskList = processService.getVacationTasks(assignee); return
	 * taskList.toString(); }
	 * 
	 * 
	 * @GetMapping(value = "/complete-vacation-task") public String
	 * completeVacationTask(@RequestParam(name = "managerMotivation") String
	 * managerMotivation, @RequestParam(name = "vacationApproved") String
	 * vacationApproved, @RequestParam(name= "assignee") String assignee) {
	 * processService.completeVacationTask(managerMotivation, vacationApproved,
	 * assignee); return null; }
	 * 
	 * @PostMapping(value = "/test/req-body") public String
	 * testRequestBody(@Request) { return "Hi"; }
	 * 
	 */

	/*
	 * @PostMapping(value =
	 * "/flow-request/review/processId/{processId}/requestId/{requestId}/objectId/{objectId}/authorName/{authorName}/reviewerName/{reviewerName}/approverName/{approverName}")
	 * public String startObjectReviewProcess(@PathVariable(name = "processId")
	 * String processId,
	 * 
	 * @PathVariable(name = "requestId") long requestId, @PathVariable(name =
	 * "objectId") long objectId,
	 * 
	 * @PathVariable(name = "authorName") String authorName,
	 * 
	 * @PathVariable(name = "reviewerName") String reviewerName,
	 * 
	 * @PathVariable(name = "approverName") String approverName) {
	 * 
	 * processService.startTheProcess(requestId, objectId, authorName, reviewerName,
	 * approverName, processId);
	 * 
	 * return "Process " + processId + " started."; }
	 */

	
	// requestId - Auto generated from the caller
	// objectID - Object Type : ID eg: Lot - 2133, Campaign - 4445
	// objectType -
	// author - 
	// reviewer -
	// approver - 
	
	// Endpoint : /process-request method : POST ,..above is the body. 

	
	
	@PostMapping(value = "/process-request")
	public String startObjectReviewProcess(@RequestBody(required = true) Map<String, String> parameters) {

		String requestId = parameters.get("requestId");
		String objectId = parameters.get("objectId");
		String objectType = parameters.get("objectType");
		String author = parameters.get("author");
		String reviewer = parameters.get("reviewer");
		String approver = parameters.get("approver");
		
		processService.startTheProcess(requestId, objectId, objectType, author, reviewer, approver, "objApprovalProcess");

		return "Process with requestId " + requestId + " started.";
	}
	
	/*
	 * All takss that are waiting for this person
	/process-requests/<<person>>
	 
	 
	 Response : 
	 
	Using the requestID find the user task which is pending.
 	Task ID :
 	Task Name : <<Name of the task>> Review / Approve 
 	Request ID : 
 	Object ID : Lot -1333
 
    */
	
	@GetMapping(value = "/get-task/assignee/{assignee}")
	public String getTask(@PathVariable(name = "assignee") String assignee) {

		List<Task> taskList = processService.getTasks(assignee);

		return taskList.toString();
	}

	
	/*
	 End point : /process-request/{{requestID}}
	 	Body : {
	 	requestID : 1332,
	 	taskType : review
	 	status : approve / reject
	 	comments : This is fooggg.
	 	}
	 	
		Using the requestID find the user task which is pending. Response should give the task ID 
		and then complete it based on the task id
	 
	 */
	@PostMapping(value = "/complete-reviewer-task/taskId/{taskId}/reviewerApproved/{reviewerApproved}/objectReviewerReview/{objectReviewerReview}")
	public String completeReviewerTask(@PathVariable(name = "taskId") String taskId,
			@PathVariable(name = "reviewerApproved") String reviewerApproved,
			@PathVariable(name = "objectReviewerReview") String objectReviewerReview) {
		processService.completeReviewerTask(taskId, reviewerApproved, objectReviewerReview);
		return "task: " + taskId + " completed";
	}

	@PostMapping(value = "/complete-approver-task/taskId/{taskId}/approverApproved/{approverApproved}/objectApproverReview/{objectApproverReview}")
	public String completeApproverTask(@PathVariable(name = "taskId") String taskId,
			@PathVariable(name = "approverApproved") String approverApproved,
			@PathVariable(name = "objectApproverReview") String objectApproverReview) {
		processService.completeApproverTask(taskId, approverApproved, objectApproverReview);
		return "task: " + taskId + " completed";
	}

}