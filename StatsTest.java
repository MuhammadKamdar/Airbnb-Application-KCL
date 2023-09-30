

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class StatsTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StatsTest
{
    /**
     * Default constructor for test class StatsTest
     */
    public StatsTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testStats()
    {
        Stats stats1 = new Stats();
        assertEquals("Hackney : 95%", stats1.getHighestToiletPaperAvailability());
        assertEquals("Brent : 123 parks", stats1.getMostParksBorough());
        assertEquals("Westminster", stats1.getMostResturantBorough());
        assertEquals("Westminster : 32 stations", stats1.getMostUndergroundBorough());
        assertEquals("Westminster", stats1.getUnsafeBorough());
        assertEquals("Sutton", stats1.getMostAffordableBorough());
        assertEquals("Richmond upon Thames", stats1.getMostExpensiveBorough());
        assertEquals(12, stats1.getAverageReviews(), 0);
    }
}

