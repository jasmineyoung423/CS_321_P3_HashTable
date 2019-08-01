// ARRAY OF HASH OBJECTS 
// 95789   95791 
// alpha: 0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 0.98, 0.9
// java HashTest <input type> <load factor> [<debug level>]
public class HashTable
{
	private HashObject[] table;
	private double alpha;
	private final int M;
	private int probeCount;
	private boolean doubleHash;
	private int currentCapacity;
	
	public HashTable(int dataType, double alpha, boolean doubleHash)
	{
		PrimeFinder prime = new PrimeFinder();
		M = prime.prime(95501, 95997) + 2; // PrimeFinder finds the first prime, add 2 to get table size
		this.alpha = alpha;
		table = new HashObject[M];
		this.doubleHash = doubleHash;
	}
	
	public void insertObject(Object key)
	{
		int primaryHash;
		int secondaryHash = 0;
		int i = 1;
		primaryHash = mod(key.hashCode(),M);
		if(doubleHash)
		{
			secondaryHash = 1 + mod(key.hashCode(),M-2);
		}
		
		int j = primaryHash;	
		j = mod(j,M);
		
		boolean placed = false;

		if(!isEmpty())
		{
			while(i != M && !placed)
			{
				HashObject o = new HashObject(key);
				
				if(table[j] == null)
				{
					o.setProbeCount(i);
					table[j] = o;
					this.probeCount += i;
					placed = true;
					currentCapacity++;
				}
				else if(o.equals(table[j].getKey()))
				{
					table[j].increaseDuplicateCount();
					placed = true;
				}
				else
				{
					if(doubleHash)
					{
						j += secondaryHash;
						if(j >= M)
						{
							j = mod(j,M);
						}
					}
					else
					{
						j++;
						if(j >= M)
						{
							j = mod(j,M);
						}
					}
					i++;
				}
			}
		}
		else
		{
			HashObject o = new HashObject(key);
			o.setProbeCount(i);
			table[j] = o;
			this.probeCount += i;
			currentCapacity++;
		}
	}
	
	private boolean isEmpty()
	{
		return (currentCapacity == 0);
	}
	
	public boolean isFull()
	{
		return (((double) currentCapacity/M) >= alpha);
	}
	
	private int mod(int key, int size)
	{
		int value = key % size;
		if(value < 0)
		{
			value += size;
		}
		return value;
	}
	
	public int getProbeCount()
	{
		return this.probeCount;
	}
	
	public double getAvgProbes()
	{
		return (((double) this.probeCount) / this.currentCapacity); 
	}
	
	public HashObject[] getArray()
	{
		return this.table;
	}
	
	public int getDuplicateCount()
	{
		int dupes = 0;
		for(int i=0; i < M; i++)
		{
			if(table[i] != null)
			{
				dupes += table[i].getDuplicateCount(); 
			}
		}
		return dupes;
	}
	
	public int getCurrentCapacity()
	{
		return this.currentCapacity;
	}
	
	public int getTableSize()
	{
		return this.M;
	}
}
