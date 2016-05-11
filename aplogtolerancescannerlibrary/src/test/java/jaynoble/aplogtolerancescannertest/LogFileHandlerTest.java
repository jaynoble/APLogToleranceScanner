package jaynoble.aplogtolerancescannertest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.StringReader;

import jaynoble.aplogtolerancescanner.LogFileHandler;

import static org.junit.Assert.*;

/**
 * Created by Jay on 4/13/2016.
 */
public class LogFileHandlerTest
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @org.junit.Before
    public void setUp() throws Exception
    {

    }

    @org.junit.After
    public void tearDown() throws Exception
    {

    }

    @org.junit.Test
   /* public void testNewInstanceThrowsOnBadFileName() throws Exception
    {
        // should throw if file not found
        String badFileName = "badFile.ext";
        exception.expect(FileNotFoundException.class);
        LogFileHandler logFileHandler = LogFileHandler.newInstance(badFileName);
    }*/
    public void testNewInstance() throws Exception
    {
        LogFileHandler logFileHandler = LogFileHandler.newInstance(new BufferedReader(new StringReader("")));
    }

    @org.junit.Test
    public void testGetMonitorNames() throws Exception
    {
        // expect monitor names "one", "two", "three (units)"
    }

    @Test
    public void testStripUnits() throws Exception
    {
        String[] monitorNamesWithUnits = {"one (unit 1)", "two (2)", "three", "four (units)"};
        String[] monitorNamesNoUnits = {"one", "two", "three", "four"};

        String[] monitorNames = LogFileHandler.stripUnits(monitorNamesWithUnits);
        assertArrayEquals(monitorNamesNoUnits, monitorNames);
    }

    @org.junit.Test
    public void testScanLogFile() throws Exception
    {

    }
}
