package jaynoble.aplogtolerancescanner;


/**
 * Monitor is responsible for containing information for the AP monitor that is being tracked
 * and any violations to the tolerance that is set.
 */
public class Monitor
{
    private final String m_name;
    private final float m_min;
    private final float m_max;
    private int m_columnNumber;

    // prefer static factory method over public constructor
    public static Monitor newInstance(String name, float min, float max) throws IllegalArgumentException
    {
        if (min > max)
            throw new IllegalArgumentException("min must be <= max");
        return new Monitor(name, min, max);
    }

    private Monitor(String name, float min, float max)
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

    public void setColumnNumber(int columnNumber) throws IllegalArgumentException
    {
        if (columnNumber < 0)
            throw new IllegalArgumentException("columnNumber must be >= 0");
        this.m_columnNumber = columnNumber;
    }

    public int columnNumber()
    {
        return this.m_columnNumber;
    }

    public boolean outsideTolerance(float value)
    {
        return value < this.m_min || value > this.m_max;
    }
}
