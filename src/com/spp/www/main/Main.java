package com.spp.www.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.spp.www.constants.CoreConstants;
import com.spp.www.items.TaskItem;
import com.spp.www.items.response.BaseResponseItem;
import com.spp.www.items.response.ProjectScheduleResponseItem;
import com.spp.www.items.response.TaskListResponseItem;
import com.spp.www.services.TaskService;

public class Main {

	public static void main(String[] args) {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {

				System.out.println("###################");
				System.out.println("# Welcome to SPP! #");
				System.out.println("###################");

				System.out.println("");
				System.out.println("Options (enter letter):");
				System.out.println("c - Create Task");
				System.out.println("r - Retrieve Task");
				System.out.println("u - Update Task");
				System.out.println("d - Delete Task");
				System.out.println("g - Generate Project Schedule");
				System.out.println("e - Exit SPP");
				System.out.println("");
				System.out.print("Choice: ");
				String input = bufferedReader.readLine();
				System.out.println("###################");

				if ("c".equals(input)) {
					// Gather Data
					System.out.print("Enter task name (required): ");
					String taskName = bufferedReader.readLine();
					System.out.print("Enter task details (optional): ");
					String taskDetails = bufferedReader.readLine();
					System.out.print("Enter task duration (number > 0 ): ");
					String durationInput = bufferedReader.readLine();
					int duration = 0;
					try {
						duration = new Integer(durationInput);
					} catch (NumberFormatException e) {
						System.out.println("Error processing input for duration.");
						System.out.print("Press any key to continue.");
						bufferedReader.readLine();
						continue;
					}
					ArrayList<TaskItem> taskDependencyList = new ArrayList<TaskItem>();
					System.out.print("Enter IDs of task dependency (comma separated): ");
					String taskDepencencies = bufferedReader.readLine();
					if (!taskDepencencies.isEmpty()) {
						String taskDepencenArray[] = taskDepencencies.split(",");
						for (String taskDependency : taskDepencenArray) {
							int id = 0;
							try {
								id = new Integer(taskDependency);
								TaskItem taskDependencyItem = new TaskItem();
								taskDependencyItem.setId(id);
								taskDependencyList.add(taskDependencyItem);
							} catch (NumberFormatException e) {
								System.out.println("Error processing input for dependency.");
								System.out.print("Press any key to continue.");
								bufferedReader.readLine();
								continue;
							}
						}
					}

					// Translate Input
					TaskItem taskItem = new TaskItem();
					taskItem.setName(taskName);
					taskItem.setDetails(taskDetails);
					taskItem.setDuration(duration);
					taskItem.setTaskDependencyList(taskDependencyList);

					// Call
					TaskService taskService = new TaskService();
					BaseResponseItem baseResponseItem = taskService.createTask(taskItem);
					System.out.println(baseResponseItem.getResult().getMessage());
				} else if ("r".equals(input)) {
					TaskService taskService = new TaskService();
					TaskListResponseItem taskListResponseItem = taskService.retrieveTaskList();
					if (!taskListResponseItem.getResult().getStatus().isEmpty()
							&& taskListResponseItem.getResult().getStatus().equalsIgnoreCase(CoreConstants.SUCCESS)) {
						System.out.print("Task list");
						if (taskListResponseItem.getTaskList().size() > 0) {
							for (TaskItem taskItem : taskListResponseItem.getTaskList()) {
								System.out.println("");
								System.out.println("----------");
								System.out.println("id: " + taskItem.getId());
								System.out.println("name: " + taskItem.getName());
								System.out.println("details: " + taskItem.getDetails());
								System.out.println("duration: " + taskItem.getDuration());
								if (taskItem.getTaskDependencyList().size() > 0) {
									System.out.print("dependencies: ");
									for (TaskItem taskDependencyItem : taskItem.getTaskDependencyList()) {
										System.out.print("taskId=" + taskDependencyItem.getId() + " ");
									}
									System.out.println();
								}
								System.out.println("----------");
							}
						} else {
							System.out.println(" is currently empty.");
						}
					} else {
						System.out.println(taskListResponseItem.getResult().getMessage());
					}
				} else if ("u".equals(input)) {
					// Gather Data
					System.out.print("Enter task id to edit (required): ");
					String taskId = bufferedReader.readLine();
					int id = 0;
					try {
						id = new Integer(taskId);
					} catch (NumberFormatException e) {
						System.out.println("Error processing input for taskId.");
						System.out.print("Press any key to continue.");
						bufferedReader.readLine();
						continue;
					}
					System.out.print("Enter task name (required): ");
					String taskName = bufferedReader.readLine();
					System.out.print("Enter task details (optional): ");
					String taskDetails = bufferedReader.readLine();
					System.out.print("Enter task duration (number > 0 ): ");
					String durationInput = bufferedReader.readLine();
					int duration = 0;
					try {
						duration = new Integer(durationInput);
					} catch (NumberFormatException e) {
						System.out.println("Error processing input for duration.");
						System.out.print("Press any key to continue.");
						bufferedReader.readLine();
						continue;
					}
					ArrayList<TaskItem> taskDependencyList = new ArrayList<TaskItem>();
					System.out.print("Enter IDs of task dependency (comma separated): ");
					String taskDepencencies = bufferedReader.readLine();
					if (!taskDepencencies.isEmpty()) {
						String taskDepencenArray[] = taskDepencencies.split(",");
						for (String taskDependency : taskDepencenArray) {
							int taskDependencyId = 0;
							try {
								taskDependencyId = new Integer(taskDependency);
								TaskItem taskDependencyItem = new TaskItem();
								taskDependencyItem.setId(taskDependencyId);
								taskDependencyList.add(taskDependencyItem);
							} catch (NumberFormatException e) {
								System.out.println("Error processing input for dependency.");
								System.out.print("Press any key to continue.");
								bufferedReader.readLine();
								continue;
							}
						}
					}

					// Translate Input
					TaskItem taskItem = new TaskItem();
					taskItem.setId(id);
					taskItem.setName(taskName);
					taskItem.setDetails(taskDetails);
					taskItem.setDuration(duration);
					taskItem.setTaskDependencyList(taskDependencyList);

					// Call
					TaskService taskService = new TaskService();
					BaseResponseItem baseResponseItem = taskService.updateTask(taskItem);
					System.out.println(baseResponseItem.getResult().getMessage());
				} else if ("d".equals(input)) {
					// Gather Data
					System.out.print("Enter task id to edit (required): ");
					String taskId = bufferedReader.readLine();
					int id = 0;
					try {
						id = new Integer(taskId);
					} catch (NumberFormatException e) {
						System.out.println("Error processing input for taskId.");
						System.out.print("Press any key to continue.");
						bufferedReader.readLine();
						continue;
					}

					// Translate Input
					TaskItem taskItem = new TaskItem();
					taskItem.setId(id);

					// Call
					TaskService taskService = new TaskService();
					BaseResponseItem baseResponseItem = taskService.deleteTask(taskItem);
					System.out.println(baseResponseItem.getResult().getMessage());
				} else if ("g".equals(input)) {
					// Call
					TaskService taskService = new TaskService();
					ProjectScheduleResponseItem projectScheduleResponseItem = taskService.retrieveProjectSchedule();
					if (!projectScheduleResponseItem.getResult().getStatus().isEmpty() && projectScheduleResponseItem
							.getResult().getStatus().equalsIgnoreCase(CoreConstants.SUCCESS)) {
						System.out.print("Project Schedule");
						if (projectScheduleResponseItem.getProjectScheduleList().size() > 0) {
							int period = 1;
							for (ArrayList<TaskItem> periodTaskList : projectScheduleResponseItem
									.getProjectScheduleList()) {
								System.out.println("");
								System.out.println("----------");
								System.out.println("Tasks in period " + period);
								for (TaskItem taskItem : periodTaskList) {
									System.out.print(taskItem.getId() + ":" + taskItem.getName() + " ");
								}
								System.out.println("");
								System.out.println("----------");
								period++;
							}
						} else {
							System.out.println(" is currently empty.");
						}
					} else {
						System.out.println(projectScheduleResponseItem.getResult().getMessage());
					}
				} else if ("e".equals(input)) {
					System.out.println("");
					System.out.println("Thank you for using SPP!");
					System.exit(0);
				}
				System.out.print("Press any key to continue.");
				bufferedReader.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
