package jaynoble.aplogtolerancescanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * CSVFileHandler is responsible for interfacing with a csv file.
 */
public class CSVFileHandler
{
    static final int INVALID_ROW = 0;
    private BufferedReader m_fileReader;
    private boolean m_canResetFile;
    private Scanner m_fileScanner;
    private int m_row;  // 1-based row in the file

    // prefer static factory method over public constructor
    public static CSVFileHandler newInstance(BufferedReader reader)
    {
        return new CSVFileHandler(reader);
    }

    private CSVFileHandler(BufferedReader reader)
    {
        m_fileReader = reader;
        m_fileScanner = new Scanner(m_fileReader);
        m_fileScanner.useDelimiter(",\\s"); // delimiter = ',' and all whitespace characters(?) TODO: evaluate whitespace parsing
        m_row = INVALID_ROW;

        // mark the file, assuming that this object was created with a reader that's at the beginning of the file
        if (m_fileReader.markSupported())
        {
            try
            {
                m_fileReader.mark(10000);
                m_canResetFile = true;
            }
            catch (IOException e)
            {
                m_canResetFile = false;
            }
        }
        else
            m_canResetFile = false;
    }

    public String[] scanCSVFileHeader()
    {
        // make sure at beginning of file if possible...
        if (m_canResetFile)
        {
            try
            {
                m_fileReader.reset();
                m_row = INVALID_ROW;
            } catch (IOException e)
            {
                System.out.println("Error: Can't reset file: " + e.getMessage());
            }
        }
        String headerLine = readRow();

        String[] headerNames = headerLine.split(",");
        // TODO: strip out units "(any symbol/char)"
        /*System.out.print("Found header names:");
        for (String name : headerNames)
            System.out.print(" " + name);*/
        return headerNames;
    }

    public double getValueAtColumn(int column)
    {
        String line = readRow();
        String[] lineValues = line.split(",");
        // REQUIRE(0 <= column && column < lineValues.length)
        return new Scanner(lineValues[column]).nextDouble();
    }

    public int getRow()
    {
        return m_row;
    }

    private String readRow()
    {
        ++m_row;
        return m_fileScanner.nextLine();
    }

    public boolean eof()
    {
        return !m_fileScanner.hasNextLine();
    }
}
