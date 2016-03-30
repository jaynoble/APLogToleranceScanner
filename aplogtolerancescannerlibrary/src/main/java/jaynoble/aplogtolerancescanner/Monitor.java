package jaynoble.aplogtolerancescanner;

/**
 * Monitor is responsible for containing information for the AP monitor that is being tracked
 * and any violations to the tolerance that is set.
 */
public class Monitor
{
    private String m_name;
    private double m_min;
    private double m_max;
    private int m_columnNumber;

    public Monitor(String name, double min, double max)
    {
        this.m_name = name;
        this.m_min = min;
        this.m_max = max;
        this.m_columnNumber = -1; // invalid columnNumber
    }

    public String name()
    {
        return this.m_name;
    }

    public void setColumnNumber(int columnNumber)
    {
        this.m_columnNumber = columnNumber;
    }

    public int columnNumber()
    {
        return this.m_columnNumber;
    }

    public boolean outsideTolerance(double value)
    {
        return value < this.m_min || value > this.m_max;
    }
}
