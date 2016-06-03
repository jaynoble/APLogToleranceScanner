package jaynoble.aplogtolerancescanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * APLogToleranceScannerClass is responsible for being the entry point and managing the program.
 */
public class APLogToleranceScannerClass
{
    static final String APPTITLE = "Cobb AccessPort Data Log Tolerance Scanner";

    public static void main(String[] args) throws IOException
    {
        // Welcome message
        System.out.println("Welcome to the " + APPTITLE + "!");

        // Get user input
        InputParserInterface inputParser;

        try
        {
            if (args.length > 0)
                inputParser = new CommandLineInputParser(args); // get input from command line arguments
            else
                inputParser = new ConsoleInputParser(); // get input from console
            String[] logFileNames = inputParser.getLogFileNames();
            String fileName = logFileNames[0];  // for now...

            if (!fileName.isEmpty())
            {
                // open file
                // Use try-with-resources so BufferedReader will auto-close when it goes out of scope
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
                {
                    LogFileHandler logFileHandler = LogFileHandler.newInstance(reader);
                    String[] monitorNames = logFileHandler.getMonitorNames();
                    Monitor monitorToScan = inputParser.getMonitors(monitorNames);
                    // scan file
                    logFileHandler.scanLogFile(monitorToScan);
                    // close file
                }
                catch (IOException e)
                {
                    System.out.println("Scan of " + fileName + " failed: " + e.getMessage());
                }
                // report results
                reportResults(fileName);
            }
        }
        catch (IOException e)
        {
            System.out.println("Input error: " + e.getMessage());
            System.out.println("Please try again.");
            printHelp();
        }
    }


    private static void printHelp()
    {
        final String appname = "aplogtolerancescanner";
        System.out.println(APPTITLE + " usage:");
        System.out.println("  1. " + appname + " logfile.csv OR");
        System.out.println("  2. " + appname + " and follow prompts");
    }

    private static void reportResults(String fileName)
    {
        System.out.println("Scan results for " + fileName + ":");
    }
}
