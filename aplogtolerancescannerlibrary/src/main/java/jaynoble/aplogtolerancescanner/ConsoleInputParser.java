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

    public ConsoleInputParser() throws IOException
    {
        // Wrap System.in in InputStreamReader to get character stream features
        // The BufferedReader will auto-close when ConsoleInputParser is cleaned up.
        m_inputParser = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String getLogFileName() throws IOException
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
            throw new IOException("Log file input error: ", e);
        }

        return fileName;
    }

    // return 1-based choice (0 to cancel)
    private int getMonitorChoice(Scanner monitorScanner, int monitorListCount) throws IOException
    {
        System.out.print("Enter the number of the monitor to scan for (0 to quit): ");

        int choice = 0;
        boolean validChoice = false;
        while (!validChoice)
        {
            String userInputString = m_inputParser.readLine();
            choice = Integer.parseInt(userInputString);

            if (0 <= choice && choice <= monitorListCount)
                validChoice = true;
            else
                System.out.print("Invalid choice. Please choose again: ");
        }

        return choice;
    }

    private MinMax getMonitorTolerance() throws IOException
    {
        // get the tolerance min/max
        System.out.print("Enter the minimum allowed tolerance: ");
        float min = Float.parseFloat(m_inputParser.readLine());
        System.out.print("Enter the maximum allowed tolerance: ");
        float max = Float.parseFloat(m_inputParser.readLine());

        return new MinMax(min, max);
    }

    @Override
    public Monitor getMonitor(String[] monitorNames) throws IOException
    {
        // list monitors
        System.out.println("Monitor names are: ");
        for (int i = 1; i < monitorNames.length; ++i)
            System.out.println(i + ": " + monitorNames[i-1]);

        Scanner monitorScanner = new Scanner(System.in);
        String name = null;
        MinMax minMax = null;

        // get a monitor and range
        try
        {
            int nameIndex = getMonitorChoice(monitorScanner, monitorNames.length);
            if ((nameIndex > 0) && (nameIndex < monitorNames.length))
            {
                name = monitorNames[nameIndex-1];
                minMax = getMonitorTolerance();
            }
            else if (nameIndex == 0)    // cancel
                throw new IOException("Cancelled operation.");
            else
                throw new IOException("Invalid monitor choice.");
        }
        catch (IOException | IllegalArgumentException e)
        {
            throw new IOException("Invalid monitor name or min/max value. " + e.getMessage());
        }

        try
        {
            return Monitor.newInstance(name, minMax.getMin(), minMax.getMax());
        }
        catch (IllegalArgumentException e)
        {
            throw new IOException("Invalid monitor arguments: " + e.getMessage());
        }
    }

    // helper class to simply store min/max
    private class MinMax
    {
        private final float m_min;
        private final float m_max;

        public MinMax(float min, float max) throws IllegalArgumentException
        {
            if (Float.compare(min, max) > 0)
                throw new IllegalArgumentException("min must be <= max");
            m_min = min;
            m_max = max;
        }

        public float getMin()
        {
            return m_min;
        }

        public float getMax()
        {
            return m_max;
        }
    }
}

