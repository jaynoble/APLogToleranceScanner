package jaynoble.aplogtolerancescanner;

import java.io.IOException;

/**
 * Created by Jay on 5/17/2016.
 */
public interface LogFileHandlerInterface
{
    // return the array of monitor names
    String[] getMonitorNames();

    // scan the file for the given monitor
    void scanLogFile(Monitor targetMonitor) throws IOException;
}
