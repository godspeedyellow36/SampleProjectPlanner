package com.spp.www.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.spp.www.constants.CoreConstants;
import com.spp.www.constants.ErrorMessageConstants;
import com.spp.www.data.ProjectPlanData;
import com.spp.www.items.TaskItem;
import com.spp.www.items.response.BaseResponseItem;
import com.spp.www.items.response.ProjectScheduleResponseItem;
import com.spp.www.items.response.TaskListResponseItem;
import com.spp.www.items.response.TaskResponseItem;

public class TaskService extends BaseService {

	/**
	 * Mock auto increment generation of ID.
	 * 
	 */
	private int generateTaskId() {
		int taskId = 0;
		if (ProjectPlanData.getTaskLiskedHashMap().size() > 0) {
			int currentLinkedHashMapLastIndex = ProjectPlanData.getTaskLiskedHashMap().size() - 1;
			int currentLinkedHashMapLastKey = (Integer) ProjectPlanData.getTaskLiskedHashMap().keySet()
					.toArray()[currentLinkedHashMapLastIndex];
			taskId = currentLinkedHashMapLastKey + 1;
		}
		return taskId;
	}

	/**
	 * Validation for creating tasks
	 * 
	 * Name is required
	 * 
	 * Duration must be greater than 0
	 * 
	 */
	private boolean validateTaskCreation(TaskItem taskItem) {
		boolean validTask = true;
		if (null == taskItem) {
			validTask = false;
			generateErrorResult(CoreConstants.ERROR, ErrorMessageConstants.TASK_IS_REQUIRED);
		} else if (taskItem.getName().isEmpty()) {
			validTask = false;
			generateErrorResult(CoreConstants.ERROR, ErrorMessageConstants.TASK_NAME_EMPTY);
		} else if (taskItem.getDuration() == 0) {
			validTask = false;
			generateErrorResult(CoreConstants.ERROR, ErrorMessageConstants.TASK_DURATION_INVALID);
		} else if (null != taskItem.getTaskDependencyList()) {
			for (TaskItem taskDependencyItem : taskItem.getTaskDependencyList()) {
				if (!ProjectPlanData.getTaskLiskedHashMap().containsKey(taskDependencyItem.getId())) {
					validTask = false;
					generateErrorResult(CoreConstants.ERROR, ErrorMessageConstants.TASK_DOES_NOT_EXISTS);
					break;
				}
			}

		}
		return validTask;
	}

	/**
	 * Task creation
	 * 
	 */
	public BaseResponseItem createTask(TaskItem taskItem) {
		if (validateTaskCreation(taskItem)) {
			int taskId = generateTaskId();
			taskItem.setId(taskId);
			ProjectPlanData.getTaskLiskedHashMap().put(taskId, taskItem);
		}
		return getBaseResponseItem();
	}

	/**
	 * Validation for updating tasks
	 * 
	 */
	private boolean validateTaskUpdate(TaskItem taskItem) {
		boolean validTask = true;
		if (validateTaskCreation(taskItem)) {
			if (!ProjectPlanData.getTaskLiskedHashMap().containsKey(taskItem.getId())) {
				validTask = false;
				generateErrorResult(CoreConstants.ERROR, ErrorMessageConstants.TASK_DOES_NOT_EXISTS);
			}
		}
		return validTask;
	}

	/**
	 * Task update
	 * 
	 */
	public BaseResponseItem updateTask(TaskItem taskItem) {
		if (validateTaskUpdate(taskItem)) {
			ProjectPlanData.getTaskLiskedHashMap().put(taskItem.getId(), taskItem);
		}
		return getBaseResponseItem();
	}

	/**
	 * Validation for deleting tasks
	 * 
	 */
	private boolean validateTaskDelete(TaskItem taskItem) {
		boolean validTask = true;
		if (!ProjectPlanData.getTaskLiskedHashMap().containsKey(taskItem.getId())) {
			validTask = false;
			generateErrorResult(CoreConstants.ERROR, ErrorMessageConstants.TASK_DOES_NOT_EXISTS);
		}
		return validTask;
	}

	/**
	 * Task update
	 * 
	 */
	public BaseResponseItem deleteTask(TaskItem taskItem) {
		if (validateTaskDelete(taskItem)) {
			ProjectPlanData.getTaskLiskedHashMap().remove(taskItem.getId());
		}
		return getBaseResponseItem();
	}

	/**
	 * Validation for retrieving a task
	 * 
	 */
	private boolean validateTaskRetrieval(TaskItem taskItem) {
		boolean validTask = true;
		if (null == taskItem) {
			validTask = false;
			generateErrorResult(CoreConstants.ERROR, ErrorMessageConstants.TASK_IS_REQUIRED);
		} else if (!ProjectPlanData.getTaskLiskedHashMap().containsKey(taskItem.getId())) {
			validTask = false;
			generateErrorResult(CoreConstants.ERROR, ErrorMessageConstants.TASK_DOES_NOT_EXISTS);
		}
		return validTask;

	}

	public TaskResponseItem retrieveTask(TaskItem requestedTask) {
		TaskResponseItem taskResponseItem = new TaskResponseItem();
		if (validateTaskRetrieval(requestedTask)) {
			TaskItem retrievedTask = ProjectPlanData.getTaskLiskedHashMap().get(requestedTask.getId());
			taskResponseItem.setTask(retrievedTask);
		}
		taskResponseItem.setResult(getBaseResponseItem().getResult());
		return taskResponseItem;
	}

	/**
	 * Retrieval of all the tasks
	 * 
	 */
	public TaskListResponseItem retrieveTaskList() {
		TaskListResponseItem taskListResponseItem = new TaskListResponseItem();
		ArrayList<TaskItem> taskList = new ArrayList<TaskItem>();
		for (int key : ProjectPlanData.getTaskLiskedHashMap().keySet()) {
			taskList.add(ProjectPlanData.getTaskLiskedHashMap().get(key));
		}
		taskListResponseItem.setTaskList(taskList);
		taskListResponseItem.setResult(getBaseResponseItem().getResult());
		return taskListResponseItem;
	}

	public void generateProjectSchedule() {
		HashMap<Integer, TaskItem> independentTaskMap = new HashMap<Integer, TaskItem>();
		ArrayList<TaskItem> dependentTaskList = new ArrayList<TaskItem>();
		// Save all task without dependency and set duration up to period in map
		// Save all task with dependency for later processing
		for (int key : ProjectPlanData.getTaskLiskedHashMap().keySet()) {
			TaskItem taskItem = ProjectPlanData.getTaskLiskedHashMap().get(key);
			if (taskItem.getTaskDependencyList().size() == 0) {
				independentTaskMap.put(taskItem.getId(), taskItem);
				for (int index = 1; index < taskItem.getDuration() + 1; index++) {
					HashMap<Integer, TaskItem> periodTaskMap = new HashMap<Integer, TaskItem>();
					if (ProjectPlanData.getProjectScheduleMap().containsKey(index)) {
						if (null == ProjectPlanData.getProjectScheduleMap().get(index)) {
							periodTaskMap.put(taskItem.getId(), taskItem);
							ProjectPlanData.getProjectScheduleMap().put(index, periodTaskMap);
						} else {
							ProjectPlanData.getProjectScheduleMap().get(index).put(taskItem.getId(), taskItem);
						}
					} else {
						periodTaskMap.put(taskItem.getId(), taskItem);
						ProjectPlanData.getProjectScheduleMap().put(index, periodTaskMap);
					}
				}
			} else {
				dependentTaskList.add(taskItem);
			}
		}

		// Process all task with dependency
		HashMap<Integer, TaskItem> processedTaskMap = new HashMap<Integer, TaskItem>();
		processedTaskMap.putAll(independentTaskMap);
		int processedDependentTasks = 0;
		do {
			for (TaskItem taskItem : dependentTaskList) {
				boolean taskCanBeStarted = true;
				for (TaskItem taskDependency : taskItem.getTaskDependencyList()) {
					if (!processedTaskMap.containsKey(taskDependency.getId())) {
						taskCanBeStarted = false;
						break;
					}
				}
				if (taskCanBeStarted) {
					int periodToAdd = 0;
					for (int index = ProjectPlanData.getProjectScheduleMap().size(); index > -1; index--) {
						for (TaskItem taskDependency : taskItem.getTaskDependencyList()) {
							if (ProjectPlanData.getProjectScheduleMap().get(index)
									.containsKey(taskDependency.getId())) {
								periodToAdd = index;
								break;
							}
						}
						if (periodToAdd != 0) {
							break;
						}
					}
					for (int index = periodToAdd + 1; index < periodToAdd + taskItem.getDuration() + 1; index++) {
						if (ProjectPlanData.getProjectScheduleMap().containsKey(index)) {
							ProjectPlanData.getProjectScheduleMap().get(index).put(taskItem.getId(), taskItem);
						} else {
							HashMap<Integer, TaskItem> periodTaskMap = new HashMap<Integer, TaskItem>();
							periodTaskMap.put(taskItem.getId(), taskItem);
							ProjectPlanData.getProjectScheduleMap().put(index, periodTaskMap);
						}
					}
					processedTaskMap.put(taskItem.getId(), taskItem);
					processedDependentTasks = processedDependentTasks + 1;
				}
			}
		} while (processedDependentTasks != dependentTaskList.size());
	}

	public ProjectScheduleResponseItem retrieveProjectSchedule() {
		ProjectScheduleResponseItem projectScheduleResponseItem = new ProjectScheduleResponseItem();
		// if (ProjectPlanData.getProjectScheduleMap().size() == 0) {
		generateProjectSchedule();
		// }
		ArrayList<ArrayList<TaskItem>> projectScheduleList = new ArrayList<ArrayList<TaskItem>>();
		for (int period : ProjectPlanData.getProjectScheduleMap().keySet()) {
			ArrayList<TaskItem> periodTaskList = new ArrayList<TaskItem>();
			for (int taskId : ProjectPlanData.getProjectScheduleMap().get(period).keySet()) {
				periodTaskList.add(ProjectPlanData.getProjectScheduleMap().get(period).get(taskId));
			}
			projectScheduleList.add(periodTaskList);
		}
		projectScheduleResponseItem.setProjectScheduleList(projectScheduleList);
		projectScheduleResponseItem.setResult(getBaseResponseItem().getResult());
		return projectScheduleResponseItem;
	}

}
