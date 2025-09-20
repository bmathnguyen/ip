package bill;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    @Test
    public void parse_unknownCommand_throwsException() {
        // Test that an invalid command throws the expected exception
        TaskList tasks = new TaskList(new ArrayList<>());
        BillException exception = assertThrows(BillException.class, () -> {
            Parser.parse("this is not a valid command", tasks);
        });
        assertEquals("Sorry, I don't understand that command.", exception.getMessage());
    }

    @Test
    public void parse_addDeadline_success() throws BillException {
        // Test that a valid "deadline" command adds the task correctly
        TaskList tasks = new TaskList(new ArrayList<>());
        Parser.parse("deadline return book /by 2025-10-05 1800", tasks);

        // Check that one task was added
        assertEquals(1, tasks.getSize());

        // Check that the added task is an instance of Deadline
        assertTrue(tasks.getTask(0) instanceof Deadline);
    }
}