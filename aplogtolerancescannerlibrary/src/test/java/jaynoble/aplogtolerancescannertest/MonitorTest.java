package jaynoble.aplogtolerancescannertest;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import jaynoble.aplogtolerancescanner.Monitor;

import static org.junit.Assert.*;

/**
 * Created by Jay on 4/13/2016.
 */
public class MonitorTest
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    final String monitorName = "TestMonitor";
    final float min = 7.0f;
    final float max = 22.4f;

    @Test
    public void testNewInstanceThrowsOnInvalidMinMax() throws Exception
    {
        // Don't allow min > max
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("min must be <= max");
        Monitor.newInstance(monitorName, max, min);
    }

    @Test
    public void testNewInstanceAllowsValidMinMax()
    {
        // allow min == max
        Monitor testMonitorEqualMinMax = Monitor.newInstance(monitorName, min, min);
        assertTrue(true);   // exception not thrown
        // allow min < max
        Monitor testMonitorMinLTMax = Monitor.newInstance(monitorName, min, max);
        assertTrue(true);   // exception not thrown
    }

    @Test
    public void testSetDataOffset() throws Exception
    {
        Monitor testMonitor = Monitor.newInstance(monitorName, min, max);
        testMonitor.setDataOffset(0);
        assertTrue(true);   // no exception
        testMonitor.setDataOffset(100);
        assertTrue(true);   // no exception

        // don't allow data offsets < 0.  Throw invalidArg
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("data offset must be >= 0");
        testMonitor.setDataOffset(-1);
    }

    @Test
    public void testDataOffset() throws Exception
    {
        // invalid offset when never set
        Monitor testMonitor = Monitor.newInstance(monitorName, min, max);
        assertEquals(-1, testMonitor.dataOffset());

        // valid data offset
        final int offset = 11;
        testMonitor.setDataOffset(offset);
        assertEquals(offset, testMonitor.dataOffset());
    }

    @Test
    public void testOutsideTolerance() throws Exception
    {
        Monitor testMonitor = Monitor.newInstance(monitorName, min, max);
        Assert.assertTrue(testMonitor.outsideTolerance(min-1));
        Assert.assertFalse(testMonitor.outsideTolerance(min));
        Assert.assertFalse(testMonitor.outsideTolerance(((max-min)*.5f)+min));
        Assert.assertFalse(testMonitor.outsideTolerance(max));
        Assert.assertTrue(testMonitor.outsideTolerance(max + 3));
        Assert.assertTrue(testMonitor.outsideTolerance(Float.NaN));
    }
}
