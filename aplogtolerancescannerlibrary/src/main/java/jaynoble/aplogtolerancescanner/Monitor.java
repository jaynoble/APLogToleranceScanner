package jaynoble.aplogtolerancescanner;


/**
 * Monitor is responsible for containing information for the AP monitor that is being tracked
 * and any violations to the tolerance that is set.
 */
public class Monitor
{
    private final String m_name;    // this member will not change outside of construction
    private final float m_min;      // this member will not change outside of construction
    private final float m_max;      // this member will not change outside of construction
    private int m_dataOffset;

    // Trying out Effective Java advice to use static factory method over public constructor
    public static Monitor newInstance(String name, float min, float max) throws IllegalArgumentException
    {
        if (Float.compare(min, max) > 0)
            throw new IllegalArgumentException("min must be <= max");
        return new Monitor(name, min, max);
    }

    private Monitor(String name, float min, float max)
    {
        m_name = name;
        m_min = min;
        m_max = max;
        m_dataOffset = -1; // invalid offset
    }

    public String name()
    {
        return m_name;
    }

    // Set the location where monitor data can be found
    public void setDataOffset(int dataOffset) throws IllegalArgumentException
    {
        if (dataOffset < 0)
            throw new IllegalArgumentException("data offset must be >= 0");
        m_dataOffset = dataOffset;
    }

    public int dataOffset()
    {
        return m_dataOffset;
    }

    public boolean outsideTolerance(float value)
    {
        // Effective Java advises to use Float.compare to handle Nan and -0
        return (Float.compare(value, m_min) < 0) || (Float.compare(value, m_max) > 0);
    }
}
