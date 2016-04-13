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
    private String m_fileName;
    private BufferedReader m_fileReader;
    private Scanner m_fileScanner;
    private int m_row;  // 1-based row in the file

    // prefer static factory method over public constructor
    public static CSVFileHandler newInstance(String fileName) throws IOException
    {
        return new CSVFileHandler(fileName);
    }

    private CSVFileHandler(String fileName) throws IOException
    {
        m_fileName = fileName;
        try
        {
            m_fileReader = new BufferedReader(new FileReader(m_fileName));
            System.out.println("Opening file " + m_fileName + "...");
            m_fileScanner = new Scanner(m_fileReader);
            m_fileScanner.useDelimiter(",\\s"); // delimiter = ',' and all whitespace characters(?) TODO: evaluate whitespace parsing
            m_row = 0;
        }
        catch (IOException e)   // TODO: tighten exception catch/throw
        {
            System.out.println("Failed to open file " + m_fileName + "...");
            throw e;    // pass it on to the client
        }
    }

    public String[] scanCSVFileHeader()
    {
        // assumes at beginning of file...
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


