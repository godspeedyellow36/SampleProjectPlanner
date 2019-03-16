package com.spp.www.data;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.spp.www.items.TaskItem;

/**
 * Mock for data storage
 *
 */
public class ProjectPlanData {

	/**
	 * Mock data storage for tasks.
	 * 
	 * Used LinkedHashMap to be able to retrieve tasks easily using ID,
	 * 
	 * And to ensure that the data will be in order for id generation.
	 * 
	 */
	private static LinkedHashMap<Integer, TaskItem> taskLiskedHashMap;

	/**
	 * Mock data storage for project schedule.
	 * 
	 * 1st key is period (length to time duration with occupy)
	 * 
	 * 2nd key is id
	 * 
	 */
	private static HashMap<Integer, HashMap<Integer, TaskItem>> projectScheduleMap;

	public static LinkedHashMap<Integer, TaskItem> getTaskLiskedHashMap() {
		if (null == taskLiskedHashMap) {
			taskLiskedHashMap = new LinkedHashMap<Integer, TaskItem>();
		}
		return taskLiskedHashMap;
	}

	public static void setTaskLiskedHashMap(LinkedHashMap<Integer, TaskItem> taskLiskedHashMap) {
		ProjectPlanData.taskLiskedHashMap = taskLiskedHashMap;
	}

	public static HashMap<Integer, HashMap<Integer, TaskItem>> getProjectScheduleMap() {
		if (null == projectScheduleMap) {
			projectScheduleMap = new HashMap<Integer, HashMap<Integer, TaskItem>>();
		}
		return projectScheduleMap;
	}

	public static void setProjectScheduleMap(HashMap<Integer, HashMap<Integer, TaskItem>> projectScheduleMap) {
		ProjectPlanData.projectScheduleMap = projectScheduleMap;
	}

}
