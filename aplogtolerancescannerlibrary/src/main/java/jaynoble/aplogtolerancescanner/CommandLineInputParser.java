package jaynoble.aplogtolerancescanner;

import static jaynoble.aplogtolerancescanner.Monitor.*;

/**
 * Created by Jay on 5/24/2016.
 * CommandLineInputParser is responsible for getting command line arguments
 */
public class CommandLineInputParser implements InputParserInterface
{
    private String logFileName;
    private String monitorFileName;

    // public constructor
    public CommandLineInputParser(String[] args)
    {
        assert(args.length == 2);
        if (args.length != 2)
            requestInput();
        else
        {
            logFileName = args[0];
            monitorFileName = args[1];
        }
    }

    private void requestInput()
    {
        System.out.println("Invalid command-line arguments.  Expected command: APLogToleranceScanner log_file_directory monitor_file");
    }

    @Override
    public String[] getLogFileNames()
    {
        // get file names from directory argument
        // TODO: 5/25/2016 Modify to get files from directory 
        String[] logFileNames = {logFileName};
        return logFileNames;
    }

    @Override
    public Monitor getMonitors()
    {
        // get Monitor(s) from the monitor file
        // open monitor file
        // scan monitors
        // return monitors
        return newInstance("test", 0, 1);
    }
}
