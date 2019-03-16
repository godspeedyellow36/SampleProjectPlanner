package com.spp.www.items.response;

import java.util.ArrayList;

import com.spp.www.items.TaskItem;

/**
 * Model to hold response for a list of tasks
 *
 */
public class TaskListResponseItem extends BaseResponseItem {

	private ArrayList<TaskItem> taskList;

	public ArrayList<TaskItem> getTaskList() {
		if (null == taskList) {
			taskList = new ArrayList<TaskItem>();
		}
		return taskList;
	}

	public void setTaskList(ArrayList<TaskItem> taskList) {
		this.taskList = taskList;
	}

}
