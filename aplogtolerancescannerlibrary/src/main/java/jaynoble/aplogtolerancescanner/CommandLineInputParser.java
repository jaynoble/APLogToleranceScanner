package jaynoble.aplogtolerancescanner;

import java.io.IOException;

import static jaynoble.aplogtolerancescanner.Monitor.*;

/**
 * Created by Jay on 5/24/2016.
 * CommandLineInputParser is responsible for getting command line arguments
 */
public class CommandLineInputParser implements InputParserInterface
{
    private String m_logFileName;
    private String m_monitorName;
    private float m_monitorMin;
    private float m_monitorMax;

    // public constructor
    public CommandLineInputParser(String[] args) throws IOException
    {
        final int REQUIRED_ARG_NUM = 4;
        // arguments should be: logfilename monitorname monitormin monitormax
        if (args.length != REQUIRED_ARG_NUM)
            throw new IOException("Invalid command-line arguments.");
        else
        {
            m_logFileName = args[0];
            m_monitorName = args[1];
            try
            {
                m_monitorMin = Float.parseFloat(args[2]);
                m_monitorMax = Float.parseFloat(args[3]);
            }
            catch (NumberFormatException e)
            {
                throw new IOException("Invalid number arguments.");
            }
        }
    }

    @Override
    public String[] getLogFileNames() throws IOException
    {
        // TODO: 5/25/2016 Modify to get files from directory 
        String[] logFileNames = {m_logFileName};
        return logFileNames;
    }

    @Override
    public Monitor getMonitors(String[] monitorNames)
    {
        // return Monitor created from command line arguments
        return Monitor.newInstance(m_monitorName, m_monitorMin, m_monitorMax);
    }
}
