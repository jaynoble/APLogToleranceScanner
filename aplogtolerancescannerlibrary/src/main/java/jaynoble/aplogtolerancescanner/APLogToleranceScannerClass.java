package jaynoble.aplogtolerancescanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * APLogToleranceScannerClass is responsible for being the entry point and managing the program.
 */
public class APLogToleranceScannerClass
{
    public static void main(String[] args) throws IOException
    {
        // Welcome message
        System.out.println("Welcome to the Cobb AccessPort Data Log Tolerance Scanner!");

        // Get user input
        // wrap System.in in InputStreamReader to get character stream features
        // use a try-with-resources statement to declare the BufferedReader since it will auto-close
        // when it leaves scope so no need to close in a finally block
        try (BufferedReader inputParser = new BufferedReader(new InputStreamReader(System.in)))
        {
            ConsoleIOManager ioManager = new ConsoleIOManager(inputParser);
            String fileName = "";
            if (args.length > 0)
                fileName = args[0];
            if (fileName.isEmpty())
                fileName = ioManager.getFileNameFromUserStreamReader();
        InputParserInterface inputParser;

        if (args.length > 0)
            inputParser = new CommandLineInputParser(args); // get input from command line arguments
            //fileName = args[0];
        else
            inputParser = new ConsoleInputParser(); // get input from console
        String[] logFileNames = inputParser.getLogFileNames();
        String fileName = logFileNames[0];  // for now...
        /*if (fileName.isEmpty())
            fileName = inputParser.getFileNameFromUserStreamReader();*/

            if (!fileName.isEmpty())
            {
                // open file
                // scan file
                // close file
                // report results
                System.out.println("Scan results for " + fileName + ":");

                try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
                {
                    LogFileHandler logFileHandler = LogFileHandler.newInstance(reader);

                // Get monitor names from header and ask user which to scan for
                // and what tolerances for each.
                /*String targetMonitorName = */Monitor targetMonitor = inputParser.getMonitorFromUser(logFileHandler.getMonitorNames());

                    logFileHandler.scanLogFile(targetMonitor);
                    System.out.println("Scan Successful");
                }
                catch (IOException e)
                {
                    System.out.println("Scan failed: " + e.getMessage());
                }
            }
            else
            {
                System.out.println("No files to scan.");
            }

        }
        catch (IOException | NullPointerException e)
        {
            System.out.println("Input error");
            System.exit(1);
        }
    }
}
