package com.spp.www.items.response;

import com.spp.www.items.TaskItem;

/**
 * Model to hold response for a task
 *
 */
public class TaskResponseItem extends BaseResponseItem {

	private TaskItem task;

	public TaskItem getTask() {
		return task;
	}

	public void setTask(TaskItem task) {
		this.task = task;
	}

}
