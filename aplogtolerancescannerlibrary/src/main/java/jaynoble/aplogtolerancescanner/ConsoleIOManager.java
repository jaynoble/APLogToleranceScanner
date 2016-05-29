package jaynoble.aplogtolerancescanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * ConsoleIOManager is responsible for console i/o interactions with the user.
 */
public class ConsoleIOManager
{
    BufferedReader m_inputParser = null;

    public ConsoleIOManager(BufferedReader inputParser)
    {
        m_inputParser = inputParser;
    }

    String getFileNameFromUserStreamReader()
    {
        System.out.print("Enter the file path that you want to scan: ");
        String fileName = null;
        try
        {
            fileName = m_inputParser.readLine();
        }
        catch (IOException | NullPointerException e)
        {
            System.out.println("Input error");
        }

        return fileName;
    }

    Monitor getMonitorFromUser(String[] monitorNames) throws IOException
    {
        String monitorName = null;
        float min = 0;
        float max = 0;

        System.out.println("Monitor names are: ");
        for (int i = 1; i < monitorNames.length; ++i)
            System.out.println(i + ": " + monitorNames[i-1]);

        System.out.print("Enter the number of the monitor to scan for: ");

        String userInputString = m_inputParser.readLine();
        int monitorNumber = Integer.parseInt(userInputString);

        if (1 <= monitorNumber && monitorNumber <= monitorNames.length)
            monitorName = monitorNames[monitorNumber - 1];
        else
            System.out.print("Invalid choice. Please choose again: ");
        if (monitorName != null)
        {
            // get the tolerance min/max
            System.out.print("Enter the minimum allowed tolerance: ");
            min = Float.parseFloat(m_inputParser.readLine());
            System.out.print("Enter the maximum allowed tolerance: ");
            max = Float.parseFloat(m_inputParser.readLine());
        }

        return Monitor.newInstance(monitorName, min, max);
    }
}
