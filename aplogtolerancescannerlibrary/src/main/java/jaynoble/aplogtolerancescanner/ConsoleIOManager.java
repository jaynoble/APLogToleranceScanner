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
    String getFileNameFromUserStreamReader()
    {
        System.out.print("Enter the file path that you want to scan: ");
        String fileName = null;
        // wrap System.in in InputStreamReader to get character stream features
        try (BufferedReader inputParser = new BufferedReader(new InputStreamReader(System.in)))
        {
            fileName = inputParser.readLine();
            inputParser.close();
        }
        catch (IOException |NullPointerException e)
        {
            System.out.println("Input error");
        }

        return fileName;
    }

    /*String*/Monitor getMonitorFromUser(String[] monitorNames) throws IOException
    {
        String monitorName = null;
        float min = 0;
        float max = 0;

        System.out.println("Monitor names are: ");
        for (int i = 1; i < monitorNames.length; ++i)
            System.out.println(i + ": " + monitorNames[i-1]);
        System.out.print("Enter the number of the monitor to scan for: ");  // TODO: 4/3/2016 0 to cancel?

        //try (BufferedReader userMonitorReader = new BufferedReader(new InputStreamReader(System.in)))
        //{
            //String userInputString = userMonitorReader.readLine();
            // int monitorNumber = Integer.parseInt(userInputString);
            Scanner monitorScanner = new Scanner(System.in);
            // validate user selection
            if (monitorScanner.hasNextInt())
            {
                int monitorNumber = monitorScanner.nextInt();   // 1-based value

                if (1 <= monitorNumber && monitorNumber <= monitorNames.length)
                    monitorName = monitorNames[monitorNumber - 1];
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

        return new Monitor(monitorName, min, max);
    }
}
