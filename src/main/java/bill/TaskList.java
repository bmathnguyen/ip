package bill;

import java.util.ArrayList;

/**
 * Represents the list of tasks and provides operations to manipulate it.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks An ArrayList of tasks to initialize the list with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return The ArrayList containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Gets the current number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Retrieves a task from the list by its index.
     *
     * @param index The 0-based index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list by its index.
     *
     * @param taskIndex The 0-based index of the task to delete.
     * @return The task that was removed from the list.
     */
    public Task deleteTask(int taskIndex) {
        return tasks.remove(taskIndex);
    }

    /**
     * Marks a task as done.
     *
     * @param taskIndex The 0-based index of the task to mark as done.
     */
    public void markTask(int taskIndex) {
        tasks.get(taskIndex).mark();
    }

    /**
     * Marks a task as not done.
     *
     * @param taskIndex The 0-based index of the task to mark as not done.
     */
    public void unmarkTask(int taskIndex) {
        tasks.get(taskIndex).unmark();
    }

    /**
     * Finds and returns a list of tasks that contain the given keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return An ArrayList of matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}