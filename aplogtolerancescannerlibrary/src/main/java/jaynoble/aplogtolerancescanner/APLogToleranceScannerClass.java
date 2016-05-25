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
        ConsoleIOManager ioManager = new ConsoleIOManager();
        String fileName = "";
        if (args.length > 0)
            fileName = args[0];
        if (fileName.isEmpty())
            fileName = ioManager.getFileNameFromUserStreamReader();

        if (!fileName.isEmpty())
        {
            // open file
            // scan file
            // close file
            // report results
            System.out.println("Scan results for " + fileName + ":");

            BufferedReader reader = null;
            try
            {
                reader = new BufferedReader(new FileReader(fileName));
                LogFileHandler logFileHandler = LogFileHandler.newInstance(reader);

                // Get monitor names from header and ask user which to scan for
                // and what tolerances for each.
                /*String targetMonitorName = */Monitor targetMonitor = ioManager.getMonitorFromUser(logFileHandler.getMonitorNames());

                logFileHandler.scanLogFile(targetMonitor);
                System.out.println("Scan Successful");
            }
            catch (IOException e)
            {
                System.out.println("Scan failed: " + e.getMessage());
            }
            finally
            {
                if (reader != null)
                    reader.close();
            }
        }
        else
        {
            System.out.println("No files to scan.");
        }
    }
}
