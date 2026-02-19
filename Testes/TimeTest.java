import org.junit.jupiter.api.Test;

import data.Time;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTest {
    
    @Test
    public void testConstructorMilliseconds() {
        Time time = new Time(1234567);
        assertEquals(1234567, time.getMilliseconds());
    }
    
    @Test
    public void testConstructorString() {
        Time time = new Time("20:34:567");
        assertEquals(1234567, time.getMilliseconds());
    }
    
    @Test
    public void testAdd() {
        Time time = new Time(1000);
        time.add(2000); 
        assertEquals(3000, time.getMilliseconds());
    }
    
    @Test
    public void testFormatTime() {
        Time time = new Time(1234567);
        assertEquals("20:34:567", time.formatTime());
    }
    
    @Test
    public void testToMillis() {
        Time time = new Time("20:34:567");
        assertEquals(1234567, time.getMilliseconds());
    }
}
