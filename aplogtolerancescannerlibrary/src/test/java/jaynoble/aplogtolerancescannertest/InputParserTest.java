package jaynoble.aplogtolerancescannertest;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import jaynoble.aplogtolerancescanner.CommandLineInputParser;

import static org.junit.Assert.*;


public class InputParserTest
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    // input from command line args
    @Test
    public void testIncompleteCmdArgsGiveError() throws Exception
    {
        String[] incompleteArgs = {"somefilename", "somemonitorname"};
        exception.expect(IOException.class);
        exception.expectMessage("Invalid command-line arguments.");
        CommandLineInputParser cmdLineParserIncompleteArgs = new CommandLineInputParser(incompleteArgs);
    }

    @Test
    public void testTooManyCmdArgsGiveError() throws Exception
    {
        String[] incompleteArgs = {"somefilename", "somemonitorname", "1.1", "2.2", "extra arg"};
        exception.expect(IOException.class);
        exception.expectMessage("Invalid command-line arguments.");
        CommandLineInputParser cmdLineParserIncompleteArgs = new CommandLineInputParser(incompleteArgs);
    }

    @Test
    public void testMalformattedCmdArgsGiveError() throws Exception
    {
        String[] invalidNumericArgs = {"somefilename", "somemonitorname", "one", "two.two"};
        exception.expect(IOException.class);
        exception.expectMessage("Invalid number arguments.");
        CommandLineInputParser cmdLineParserNumericArgs = new CommandLineInputParser(invalidNumericArgs);
    }

    @Test
    public void testValidCmdArgsSucceeds() throws Exception
    {
        String[] validArguments = {"somefilename", "somemonitorname", "1.1", "2.2"};
        CommandLineInputParser cmdLineParserValid = new CommandLineInputParser(validArguments);
        assertTrue(true);   // no exceptions thrown on validly formatted arguments
    }

    @Test
    public void testGetLogFileName() throws Exception
    {
        final String expectedLogFileName = "logfile.csv";
        String[] validArguments = {expectedLogFileName, "somemonitorname", "1.1", "2.2"};
        CommandLineInputParser cmdLineParserValid = new CommandLineInputParser(validArguments);
        assertEquals(expectedLogFileName, cmdLineParserValid.getLogFileName());
    }
}
