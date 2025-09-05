package bill;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void addTask_addOneTodo_sizeIsOne() {
        TaskList tasks = new TaskList(new ArrayList<>());
        tasks.addTask(new Todo("read book"));
        assertEquals(1, tasks.getSize());
    }
}