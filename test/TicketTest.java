import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Riley Muessig (rjm7793@rit.edu)
 * File: TicketTest.java
 */
public class TicketTest {

    private static final String validDateTime = "2021-11-27T02:00:26Z";
    private static final String invalidDateTime = "12345678901234567890";
    private static final String wrongLengthDateTime = "test";

    private static final String noTime = "N/A";

    /**
     * the component under test
     */
    private Ticket CuT;

    @BeforeEach
    public void setUp() {
        CuT = new Ticket(1, 1, 1, "test",
                "testDesc", validDateTime);
    }

    @Test
    public void testCreateTicket() {
        assertNotNull(CuT);
    }

    @Test
    public void testValidTime() {
        assertEquals("11/27/2021", CuT.getDate());
        assertEquals("02:00:26", CuT.getTime());
    }

    @Test
    public void testInvalidTime() {
        Ticket invalidTicket = new Ticket(1, 1, 1,"test", "test",
                invalidDateTime);
        assertEquals("N/A", invalidTicket.getDate());
        assertEquals("N/A", invalidTicket.getTime());
    }

    @Test
    public void testWrongLengthTime() {
        Ticket invalidTicket = new Ticket(1, 1, 1,"test", "test",
                wrongLengthDateTime);
        assertEquals("N/A", invalidTicket.getDate());
        assertEquals("N/A", invalidTicket.getTime());
    }
}
