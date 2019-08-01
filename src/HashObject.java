// THE HASH OBJECT IN THE TABLE KEY IS OBJECT
public class HashObject
{
	private int duplicateCount;
	private int probeCount;
	private Object key;
	private int hashCode;
	
	public HashObject(Object key)
	{
		this.key = key;
		this.duplicateCount = 0;
	}
	
	public Object getKey()
	{
		return this.key;
	}
	
	public void setHashCode(int hashCode)
	{
		this.hashCode = hashCode;
	}
	
	@Override
	public int hashCode()
	{
		return this.hashCode;
	}
	
	public int getProbeCount()
	{
		return this.probeCount;
	}
	
	public void setProbeCount(int probeCount)
	{
		this.probeCount = probeCount;
	}
	
	public int getDuplicateCount()
	{
		return this.duplicateCount;
	}
	
	public void increaseDuplicateCount()
	{
		this.duplicateCount++;
	}
	
	public boolean equals(Object o)
	{
		return key.equals(o);
	}
	
	@Override
	public String toString()
	{
		return (key.toString() + " " + duplicateCount + " " + probeCount);
	}

}
