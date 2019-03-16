package com.spp.www.items;

import java.util.ArrayList;

/**
 * Model to hold each task
 *
 */
public class TaskItem extends BaseItem {

	private int id;
	private String name;
	private String details;
	// private String startDate;
	// private String endDate;
	private int duration;
	private ArrayList<TaskItem> taskDependencyList;
	// private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	// public String getStartDate() {
	// return startDate;
	// }
	//
	// public void setStartDate(String startDate) {
	// this.startDate = startDate;
	// }
	//
	// public String getEndDate() {
	// return endDate;
	// }
	//
	// public void setEndDate(String endDate) {
	// this.endDate = endDate;
	// }

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public ArrayList<TaskItem> getTaskDependencyList() {
		if (null == taskDependencyList) {
			taskDependencyList = new ArrayList<TaskItem>();
		}
		return taskDependencyList;
	}

	public void setTaskDependencyList(ArrayList<TaskItem> taskDependencyList) {
		this.taskDependencyList = taskDependencyList;
	}

	// public int getStatus() {
	// return status;
	// }
	//
	// public void setStatus(int status) {
	// this.status = status;
	// }

}
