package jaynoble.aplogtolerancescanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class APLogToleranceScannerClass
{
    public static void main(String[] args)
    {
        // Welcome message
        System.out.println("Welcome to the Cobb AccessPort Data Log Tolerance Scanner!");

        // Get user input
        String fileName = getFileNameFromUserStreamReader();
        if (!fileName.isEmpty())
        {
            // open file
            // scan file
            // close file
            // report results
            System.out.println("Scan results for " + fileName + ":");

            LogFileHandler logFileHandler = null;
            try
            {
                Monitor targetMonitor = new Monitor("MAF (g/s)", 12.0, 12.8);
                logFileHandler = new LogFileHandler(fileName);
                logFileHandler.scanLogFile(targetMonitor);
                System.out.println("Scan Successful");
            }
            catch (IOException e)
            {
                System.out.println("Scan failed: " + e.getMessage());
            }
        }
    }

    static String getFileNameFromUserScanner()
    {
        System.out.print("Enter the file path that you want to scan: ");
        Scanner inputParser = new Scanner(System.in);   // won't return until non-blank input added...

        return inputParser.next();
    }
    static String getFileNameFromUserStreamReader()
    {
        System.out.print("Enter the file path that you want to scan: ");
        String fileName = null;
        // wrap System.in in InputStreamReader to get character stream features
        try (BufferedReader inputParser = new BufferedReader(new InputStreamReader(System.in)))
        {
            fileName = inputParser.readLine();
        }
        catch (IOException|NullPointerException e)
        {
            System.out.println("Input error");
        }

        return fileName;
    }
}
