package bill;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void parse_unknownCommand_throwsException() {
        Ui ui = new Ui();
        TaskList tasks = new TaskList(new java.util.ArrayList<>());

        BillException exception = assertThrows(BillException.class, () -> {
            Parser.parse("this is not a valid command", ui, tasks);
        });

        assertEquals("Sorry, I don't understand that command.", exception.getMessage());
    }
}