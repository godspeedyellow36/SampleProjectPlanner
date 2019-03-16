package com.spp.www.items.response;

import java.util.ArrayList;

import com.spp.www.items.TaskItem;

public class ProjectScheduleResponseItem extends BaseResponseItem {
	
	private ArrayList<ArrayList<TaskItem>> projectScheduleList;

	public ArrayList<ArrayList<TaskItem>> getProjectScheduleList() {
		if (null==projectScheduleList) {
			projectScheduleList = new ArrayList<ArrayList<TaskItem>>();
		}
		return projectScheduleList;
	}

	public void setProjectScheduleList(ArrayList<ArrayList<TaskItem>> projectScheduleList) {
		this.projectScheduleList = projectScheduleList;
	}

}
