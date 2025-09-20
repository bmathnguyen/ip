# Bill: Your Personal Task Assistant

Welcome to **Bill**! Bill is a friendly desktop chatbot designed to help you manage your daily tasks with ease. You can add to-dos, set deadlines, schedule events, and now even snooze or reschedule them, all through a simple and intuitive command interface.

## Getting Started

To start using Bill, please follow these steps:

1.  Ensure you have **Java 11** (or a more recent version) installed on your system.
2.  Place the `bill.jar` file in a dedicated folder.
3.  Open your terminal or command prompt and navigate to the folder containing `bill.jar`.
4.  Run the following command to launch the application:
    ```bash
    java -jar bill.jar
    ```
5.  The Bill chatbot window will open. You can now start typing commands!

## Features & Usage

Bill understands a variety of commands to help you stay organized.

> [!IMPORTANT]
> All date and time inputs must follow the `yyyy-MM-dd HHmm` format (e.g., `2025-10-26 1800`).

### Viewing Your Tasks
To see all the tasks you have saved, simply use the `list` command. This will show each task with its number, status ([T], [D], [E]), and description.

* **Command**: `list`
* **Example**:
    ```
    list
    ```

### Adding a To-Do
For simple tasks without a specific date or time.

* **Command**: `todo <description>`
* **Example**:
    ```
    todo read chapter 4 of my book
    ```

### Adding a Deadline
For tasks that must be completed by a specific date and time.

* **Command**: `deadline <description> /by yyyy-MM-dd HHmm`
* **Example**:
    ```
    deadline submit project report /by 2025-09-27 2359
    ```

### Adding an Event
For tasks that have a specific start and end time.

* **Command**: `event <description> /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm`
* **Example**:
    ```
    event team sync meeting /from 2025-09-25 1000 /to 2025-09-25 1130
    ```

### Marking & Unmarking Tasks
Update the status of your tasks as you complete them.

* **Mark as Done**: `mark <task number>`
* **Mark as Not Done**: `unmark <task number>`
* **Example**:
    ```
    mark 2
    ```

### Deleting a Task
To permanently remove a task from your list.

* **Command**: `delete <task number>`
* **Example**:
    ```
    delete 3
    ```

### Finding Tasks
Search for tasks using a keyword. Bill will show you all tasks whose descriptions contain that keyword.

* **Command**: `find <keyword>`
* **Example**:
    ```
    find report
    ```

### Snoozing & Rescheduling Tasks
You can easily postpone deadlines or reschedule events. This command only works for **Deadlines** and **Events**.

* **To Snooze a Deadline**: `snooze <task number> /by <new date and time>`
* **Example**:
    ```
    snooze 1 /by 2025-09-28 1800
    ```

* **To Reschedule an Event**: `snooze <task number> /from <new start time> /to <new end time>`
* **Example**:
    ```
    snooze 3 /from 2025-09-26 1400 /to 2025-09-26 1530
    ```

### Exiting Bill
To end your session, use the `bye` command.

* **Command**: `bye`
* **Usage**: This displays a goodbye message and disables the input field. You can then safely close the application window.

## Command Summary

Here is a quick reference table for all available commands:

| Command | Format & Example | Description |
| :--- | :--- | :--- |
| **`list`** | `list` | Displays all tasks in the list. |
| **`todo`** | `todo <description>` <br> _e.g., `todo buy groceries`_ | Adds a simple to-do task. |
| **`deadline`** | `deadline <desc> /by yyyy-MM-dd HHmm` <br> _e.g., `deadline hw /by 2025-12-01 2359`_ | Adds a task with a deadline. |
| **`event`** | `event <desc> /from <time> /to <time>` <br> _e.g., `event party /from 2025-11-15 1900 /to 2025-11-15 2200`_ | Adds an event with a start and end time. |
| **`mark`** | `mark <task number>` <br> _e.g., `mark 1`_ | Marks a specific task as complete. |
| **`unmark`**| `unmark <task number>` <br> _e.g., `unmark 1`_ | Marks a specific task as incomplete. |
| **`delete`**| `delete <task number>` <br> _e.g., `delete 1`_ | Deletes a specific task from the list. |
| **`find`** | `find <keyword>` <br> _e.g., `find meeting`_ | Finds tasks containing a specific keyword. |
| **`snooze`**| `snooze <index> /by <new time>` <br> `snooze <index> /from <start> /to <end>` | Postpones a deadline or reschedules an event. |
| **`bye`** | `bye` | Disables input to exit the chatbot. |