package bill;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public int getSize() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int taskIndex) {
        // The index is 0-based for the ArrayList
        return tasks.remove(taskIndex);
    }

    public void markTask(int taskIndex) {
        tasks.get(taskIndex).mark();
    }

    public void unmarkTask(int taskIndex) {
        tasks.get(taskIndex).unmark();
    }
}