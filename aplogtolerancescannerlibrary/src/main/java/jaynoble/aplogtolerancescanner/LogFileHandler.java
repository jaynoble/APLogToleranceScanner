package jaynoble.aplogtolerancescanner;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jay on 3/25/2016.
 */
public class LogFileHandler
{
    private String m_fileName;
    private CSVFileHandler m_csvFileHandler; // Don't want this static - then only 1 instance

    public LogFileHandler(String fileName) throws IOException
    {
        // save the file name
        m_fileName = fileName;

        // could make this a factory if/when other file types come into play
        m_csvFileHandler = new CSVFileHandler(m_fileName);  // allow throw to pass through here uncaught...
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

    public void scanLogFile(Monitor targetMonitor) throws IOException
    {
        // open the file?
        // read each line
        // close the file?

        // find any matching monitors TODO: store in hashSet/Map for faster lookup?
        String[] headerNames = m_csvFileHandler.scanCSVFileHeader();
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
