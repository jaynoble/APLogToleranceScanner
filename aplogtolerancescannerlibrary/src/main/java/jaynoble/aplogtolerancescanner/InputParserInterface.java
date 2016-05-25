package jaynoble.aplogtolerancescanner;

/**
 * Created by Jay on 5/24/2016.
 */
public interface InputParserInterface
{
    // get the log file names
    String[] getLogFileNames();

    // get the requested monitor names and ranges
    Monitor getMonitors();
}
