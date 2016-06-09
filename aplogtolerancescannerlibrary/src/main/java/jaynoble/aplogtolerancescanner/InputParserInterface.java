package jaynoble.aplogtolerancescanner;

import java.io.IOException;

/**
 * Created by Jay on 5/24/2016.
 */
public interface InputParserInterface
{
    // get the log file names
    String getLogFileName() throws IOException;

    // get the requested monitor names and ranges
    Monitor getMonitor(String[] monitorNames) throws IOException;
}
