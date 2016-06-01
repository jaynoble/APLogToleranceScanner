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
    public String[] getLogFileNames() throws IOException
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

        String[] fileNames = {fileName};
        return fileNames;
    }

    private int getMonitorChoice(Scanner monitorScanner, int monitorListCount) throws IOException
    {
        System.out.print("Enter the number of a monitor to scan for: ");

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

    private void getMonitorTolerance(Scanner toleranceScanner, float min, float max) throws IOException
    {
        // get the tolerance min/max
        System.out.println("Enter the minimum allowed tolerance: ");
        min = Float.parseFloat(m_inputParser.readLine());
        System.out.println("Enter the maximum allowed tolerance: ");
        max = Float.parseFloat(m_inputParser.readLine());
    }

    /*
    private Monitor getMonitorFromUser(Scanner monitorScanner, int monitorListCount) throws IOException
    {
        String monitorName = null;
        float min = 0;
        float max = 0;

        System.out.print("Enter the number of a monitor to scan for: ");

        String userInputString = m_inputParser.readLine();
        int monitorNumber = Integer.parseInt(userInputString);

        if (1 <= monitorNumber && monitorNumber <= monitorListCount)
            monitorName = monitorNames[monitorNumber - 1];
        else
            System.out.print("Invalid choice. Please choose again: ");
        if (monitorName != null)
        {
            // get the tolerance min/max
            System.out.println("Enter the minimum allowed tolerance: ");
            min = Float.parseFloat(m_inputParser.readLine());
            System.out.println("Enter the maximum allowed tolerance: ");
            max = Float.parseFloat(m_inputParser.readLine());
        }

        return Monitor.newInstance(monitorName, min, max);
    }
*/
    @Override
    public Monitor getMonitors(String[] monitorNames)
    {
        // list monitors
        System.out.println("Monitor names are: ");
        for (int i = 1; i < monitorNames.length; ++i)
            System.out.println(i + ": " + monitorNames[i-1]);
        System.out.print("Enter the number of the monitor to scan for (0 when done): ");

        Scanner monitorScanner = new Scanner(System.in);
        String name = null;
        float min = 0;
        float max = 0;
        boolean done = false;
        while (!done)
        {
            // get a monitor and range
            try
            {
                int nameIndex = getMonitorChoice(monitorScanner, monitorNames.length);
                name = monitorNames[nameIndex];
                getMonitorTolerance(monitorScanner, min, max);
            }
            catch (IOException e)
            {
            }

            //Monitor = getMonitorFromUser(monitorScanner);
            done = true;
        }
        return Monitor.newInstance(name, min, max);
    }
}
