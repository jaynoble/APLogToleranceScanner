package jaynoble.aplogtolerancescanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * ConsoleInputParser is responsible for console i/o interactions with the user.
 */
public class ConsoleInputParser implements InputParserInterface
{
    BufferedReader m_inputParser = null;

    public ConsoleIOManager(BufferedReader inputParser)
    {
        m_inputParser = inputParser;
    }

    String getFileNameFromUserStreamReader()
    private String logFileName;

    @Override
    public String[] getLogFileNames()
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

    private Monitor getMonitorFromUser(Scanner monitorScanner, int monitorListCount) throws IOException
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

    @Override
    public Monitor getMonitors()
    {
        // list monitors
        System.out.println("Monitor names are: ");
        for (int i = 1; i < monitorNames.length; ++i)
            System.out.println(i + ": " + monitorNames[i-1]);
        System.out.print("Enter the number of the monitor to scan for (0 when done): ");

        Scanner monitorScanner = new Scanner(System.in);
        boolean done = false;
        while (!done)
        {
            // get a monitor and range
            Monitor = getMonitorFromUser(monitorScanner);
            done = true;
        }
        return Monitor;
    }
}
