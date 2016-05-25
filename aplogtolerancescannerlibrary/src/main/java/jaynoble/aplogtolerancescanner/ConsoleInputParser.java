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
    private String logFileName;

    @Override
    public String[] getLogFileNames()
    {
        System.out.print("Enter the file path that you want to scan: ");
        String fileName = null;
        // wrap System.in in InputStreamReader to get character stream features
        try (BufferedReader inputParser = new BufferedReader(new InputStreamReader(System.in)))
        {
            fileName = inputParser.readLine();
            inputParser.close();
        }
        catch (IOException | NullPointerException e)
        {
            System.out.println("Input error");
        }

        String[] logFileNames = {fileName};
        return logFileNames;
    }

    private Monitor getMonitorFromUser(Scanner monitorScanner, int monitorListCount) throws IOException
    {
        String monitorName = null;
        float min = 0;
        float max = 0;

        //try (BufferedReader userMonitorReader = new BufferedReader(new InputStreamReader(System.in)))
        //{
            //String userInputString = userMonitorReader.readLine();
            // int monitorNumber = Integer.parseInt(userInputString);


            // validate user selection
            if (monitorScanner.hasNextInt())
            {
                int monitorNumber = monitorScanner.nextInt();   // 1-based value

                if (1 <= monitorNumber && monitorNumber <= monitorNames.length)
                    monitorName = monitorNames[monitorNumber - 1];
                else if (0 == monitorNumber)
                    done = true;
                else
                    System.out.print("Invalid choice. Please choose again: ");
            }
            else
            {
                // loop back around
                System.out.print("Invalid choice. Please choose again: ");
            }
            if (monitorName != null)
            {
                // get the tolerance min/max

                System.out.print("Enter the minimum allowed tolerance: ");
                if (monitorScanner.hasNextFloat())
                    min = monitorScanner.nextFloat();
                System.out.print("Enter the maximum allowed tolerance: ");
                if (monitorScanner.hasNextFloat())
                    max = monitorScanner.nextFloat();
            }
         /*   userMonitorReader.close();
        }
        catch (IOException|NullPointerException e)
        {
            System.out.println("Input error");
        }*/

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
