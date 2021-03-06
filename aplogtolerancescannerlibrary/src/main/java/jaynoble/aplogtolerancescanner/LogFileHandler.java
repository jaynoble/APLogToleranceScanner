package jaynoble.aplogtolerancescanner;



import java.io.BufferedReader;
import java.io.IOException;

/**
 * LogFileHandler class is responsible for access to a log file of whatever type
 */
public class LogFileHandler implements LogFileHandlerInterface
{
    private CSVFileHandler m_csvFileHandler = null;
    private String[] m_monitorNames = null;

    // Trying out Effective Java advice to use static factory method over public constructor
    public static LogFileHandler newInstance(BufferedReader reader)
    {
        return new LogFileHandler(reader);
    }

    private LogFileHandler(BufferedReader reader)
    {
        // could make this a factory if/when other file types come into play
        m_csvFileHandler = CSVFileHandler.newInstance(reader);
    }

    public String[] getMonitorNames()
    {
        if (m_monitorNames == null)
            m_monitorNames = m_csvFileHandler.scanCSVFileHeader();

        return m_monitorNames;
    }


    public void scanLogFile(Monitor targetMonitor) throws IOException
    {
        // find any matching monitors TODO: store in hashSet/Map for faster lookup?
        boolean matchFound = false;
        for (int nameIndex = 0; !matchFound && (nameIndex < m_monitorNames.length); ++nameIndex)
        {
            if (targetMonitor.name().equals(m_monitorNames[nameIndex]))
            {
                matchFound = true;
                targetMonitor.setDataOffset(nameIndex);
                System.out.println("Found match for " + targetMonitor.name());
            }
        }

        if (matchFound)
        {
            // scan the values of the matching monitor for violations
            System.out.println("Tolerance violations for " + targetMonitor.name() + ":");
            while (!m_csvFileHandler.eof())
            {
                float monitorValue = m_csvFileHandler.getValueAtColumn(targetMonitor.dataOffset());
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
