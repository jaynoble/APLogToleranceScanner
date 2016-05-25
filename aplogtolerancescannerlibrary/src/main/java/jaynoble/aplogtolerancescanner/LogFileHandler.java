package jaynoble.aplogtolerancescanner;



import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * LogFileHandler class is responsible for access to a log file of whatever type
 */
public class LogFileHandler
{
    private CSVFileHandler m_csvFileHandler; // Don't want this static - then only 1 instance
    private ArrayList<String> m_monitorNames;

    // prefer static factory method over public constructor
    public static LogFileHandler newInstance(BufferedReader reader)
    {
        return new LogFileHandler(reader);
    }

    private LogFileHandler(BufferedReader reader)
    {
        // could make this a factory if/when other file types come into play
        m_csvFileHandler = CSVFileHandler.newInstance(reader);
    }



    // TODO: 3/31/2016 Needs to be robust so not dependent on being called only at the right time
    public String[] getMonitorNames()
    {
        // make sure to start at beginning of file...
        String[] monitorNames = null;   // TODO: Resolve duplicate array for ArrayList
        if (m_monitorNames == null)
        {
            monitorNames = m_csvFileHandler.scanCSVFileHeader();
            m_monitorNames = new ArrayList<>();    // don't yet know capacity - #names
            for (String name : monitorNames)
                m_monitorNames.add(name);
        }
        else
        {
            monitorNames = m_monitorNames.toArray(new String[0]);
        }
        return monitorNames;
    }

    // strip out "(units)" from "name (units)"
    public static String[] stripUnits(String[] monitorNamesWithUnits)
    {
        String[] namesNoUnits = new String[monitorNamesWithUnits.length];
        int i = 0;
        final String UNIT_REGEX = "\\s\\(.*\\)";
        for (String name : monitorNamesWithUnits)
        {
            namesNoUnits[i++] = name.replaceFirst(UNIT_REGEX, "");;
        }
        return namesNoUnits;
    }

    /*public void printMonitorNames()
    {
        System.out.print("Monitor names: ");
        if (m_monitorNames == null)
            getMonitorNames();
        for (String monitorName : m_monitorNames)
            System.out.print(monitorName + ", ");
    }*/

    public void scanLogFile(Monitor targetMonitor) throws IOException
    {
        // open the file?
        // read each line
        // close the file?

        // find any matching monitors TODO: store in hashSet/Map for faster lookup?
        String[] headerNames = getMonitorNames();
        boolean matchFound = false;
        for (int nameIndex = 0; !matchFound && (nameIndex < headerNames.length); ++nameIndex)
        {
            if (targetMonitor.name().equals(headerNames[nameIndex]))
            {
                matchFound = true;
                targetMonitor.setColumnNumber(nameIndex);   // TODO: specific to csv...
                System.out.println("Found match for " + targetMonitor.name());
            }
        }

        if (matchFound)
        {
            // scan the values of the matching monitor for violations
            System.out.println("Tolerance violations for " + targetMonitor.name() + ":");
            while (!m_csvFileHandler.eof())
            {
                double monitorValue = m_csvFileHandler.getValueAtColumn(targetMonitor.columnNumber());
                if (targetMonitor.outsideTolerance(monitorValue))
                {
                    // save row, value or maybe all violating values for a row?
                    // for now, just output them
                    System.out.println("Line: " + m_csvFileHandler.getRow() + ", Value: " + monitorValue);
                }
            }
        }
        else
        {
            System.out.println("No match found for " + targetMonitor.name());
        }
    }
}
