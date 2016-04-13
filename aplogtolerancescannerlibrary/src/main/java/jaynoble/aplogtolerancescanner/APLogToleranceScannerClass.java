package jaynoble.aplogtolerancescanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * APLogToleranceScannerClass is responsible for being the entry point and managing the program.
 */
public class APLogToleranceScannerClass
{
    public static void main(String[] args)
    {
        // Welcome message
        System.out.println("Welcome to the Cobb AccessPort Data Log Tolerance Scanner!");

        // Get user input
        ConsoleIOManager ioManager = new ConsoleIOManager();
        String fileName = args[0];
        if (fileName.isEmpty())
            ioManager.getFileNameFromUserStreamReader();

        if (!fileName.isEmpty())
        {
            // open file
            // scan file
            // close file
            // report results
            System.out.println("Scan results for " + fileName + ":");

            // TODO: 3/31/2016 get monitor names from header and ask user which to scan for
            // and what tolerances for each.

            try
            {
                LogFileHandler logFileHandler = LogFileHandler.newInstance(fileName);

                /*String targetMonitorName = */Monitor targetMonitor = ioManager.getMonitorFromUser(logFileHandler.getMonitorNames());
                //Monitor targetMonitor = new Monitor(targetMonitorName, 12.0, 12.8);
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
}
