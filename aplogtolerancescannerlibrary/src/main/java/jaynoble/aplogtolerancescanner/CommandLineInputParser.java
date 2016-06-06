package jaynoble.aplogtolerancescanner;

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
    public CommandLineInputParser(String[] args)
    {
        final int REQUIRED_ARG_NUM = 4;
        // arguments should be: logfilename monitorname monitormin monitormax
        assert(args.length == REQUIRED_ARG_NUM);
        if (args.length != REQUIRED_ARG_NUM)
            requestInput();
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
                System.out.println("Invalid number arguments.");
                requestInput();
            }
        }
    }

    // TODO: 6/3/2016 replace this with fail notification to parent 
    private void requestInput()
    {
        System.out.println("Invalid command-line arguments.  Expected command: APLogToleranceScanner log_file_name monitor_name monitor_min monitor_max");
    }

    @Override
    public String[] getLogFileNames()
    {
        // get file names from directory argument
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
