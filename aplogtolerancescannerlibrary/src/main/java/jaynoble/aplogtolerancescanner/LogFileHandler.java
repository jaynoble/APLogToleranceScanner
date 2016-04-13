package jaynoble.aplogtolerancescanner;



import java.io.IOException;
import java.util.ArrayList;

/**
 * LogFileHandler class is responsible for access to a log file of whatever type
 */
public class LogFileHandler
{
    private String m_fileName;
    private CSVFileHandler m_csvFileHandler; // Don't want this static - then only 1 instance
    private ArrayList<String> m_monitorNames;

    // prefer static factory method over public constructor
    public static LogFileHandler newInstance(String fileName) throws IOException
    {
        return new LogFileHandler(fileName);
    }

    private LogFileHandler(String fileName) throws IOException
    {
        // save the file name
        m_fileName = fileName;

        // could make this a factory if/when other file types come into play
        m_csvFileHandler = CSVFileHandler.newInstance(m_fileName);  // allow throw to pass through here uncaught...
    }

    // call from client if exception thrown
    // Need a way to enforce this - try/catch/finally in each local method?
/*    public static void cleanup()
    {
        try
        {
            if (m_fileReader != null)
                m_fileReader.close();
        }
        catch (IOException e)
        {
        }
    }*/

    // TODO: 3/31/2016 Needs to be robust so not dependent on being called only at the right time
    public String[] getMonitorNames()
    {
        // make sure to start at beginning of file...
        String[] monitorNames = null;   // TODO: Resolve duplicate array for ArrayList
        if (m_monitorNames == null)
        {
            monitorNames = m_csvFileHandler.scanCSVFileHeader();
            m_monitorNames = new ArrayList<>();    // don't know capacity - #names
            for (String name : monitorNames)
                m_monitorNames.add(name);
        }
        else
        {
            monitorNames = m_monitorNames.toArray(new String[0]);
        }
        return monitorNames;
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
