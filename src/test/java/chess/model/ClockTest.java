package chess.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClockTest {

    @Test
    public void testCountdownToZero() {
        Clock clock = new Clock(0, 0, 1);
        clock.decr();
        assertTrue(clock.outOfTime());
    }

    @Test
    public void testStringFormat() {
        Clock clock = new Clock(1, 2, 3);
        assertEquals("01:02:03", clock.getTime());
    }

    @Test
    public void testFullMinuteDecrement() {
        Clock clock = new Clock(0, 1, 0);
        clock.decr();
        assertEquals("00:00:59", clock.getTime());
    }

    @Test
    public void testFullHourDecrement() {
        Clock clock = new Clock(1, 0, 0);
        clock.decr();
        assertEquals("00:59:59", clock.getTime());
    }
}
