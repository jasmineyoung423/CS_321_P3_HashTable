import java.util.Random;

public class PrimeFinder
{
	public int prime(int start, int end)
	{
		Random r = new Random();
		for(int i = start; i < end; i+=2)
		{
			int a = r.nextInt(1000)+2;
			int sum = modularExponentiation(a,i) % i;
			if(sum == 1)
			{
				int secondSum = modularExponentiation(a, i+2) % i;
				if(secondSum == 1)
				{
					a = r.nextInt(1000)+2;
					sum = modularExponentiation(a,i) % i;
					if(sum == 1)
					{
						secondSum = modularExponentiation(a, i+2) % i;
						if(secondSum == 1)
						{
							return i;
						}
					}
				}
			}
		}
		return -1;
	}
	
	private int modularExponentiation(int a, int p)
	{
		String binary = Integer.toBinaryString(p-1);
		int data = a % p;
		for(int i = 1; i < binary.length(); i++)
		{
			data = (int) ((Math.pow(data, 2)) % p);
			if(binary.substring(i, i+1).equals("1"))
			{
				data = (data * (a % p));
			}
		}
		return data;
	}
}
