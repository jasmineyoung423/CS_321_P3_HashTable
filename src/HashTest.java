import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HashTest
{
	public static void main(String[] args)
	{
		HashTest tester = new HashTest(args);
	}
	
	public HashTest(String[] args)
	{
		if(args.length < 2 || args.length > 3)
		{
			printUsage();
		}
		else
		{
			try
			{
				int inputType = Integer.parseInt(args[0]);
				double loadFactor = Double.parseDouble(args[1]);
				int debugLevel = 0;
				if(args.length == 3)
				{
					debugLevel = Integer.parseInt(args[2]);
				}
				if(inputType == 1 || inputType == 2 || inputType == 3)
				{
					HashTable hashTableLinear = new HashTable(inputType, loadFactor, false);
					HashTable hashTableDouble = new HashTable(inputType, loadFactor, true);
					System.out.println("A good table size is found: " + hashTableLinear.getTableSize());
					if(inputType == 1)
					{
						System.out.println("Data source type: random number");
						Random r = new Random();
						int key = r.nextInt();
						while(!hashTableLinear.isFull() && !hashTableDouble.isFull())
						{
							hashTableLinear.insertObject(key);
							hashTableDouble.insertObject(key);
							key = r.nextInt();
						}
					}
					else if(inputType == 2)
					{
						System.out.println("Data source type: current time");
						long key = System.currentTimeMillis();
						while(!hashTableLinear.isFull() && !hashTableDouble.isFull())
						{
							
							hashTableLinear.insertObject(key);
							hashTableDouble.insertObject(key);
							key = System.currentTimeMillis();
						}
					}
					else if(inputType == 3)
					{
						File file = new File("word-list");
						System.out.println("Data source type: word-list\n");
						Scanner scan = new Scanner(file);
						while(!hashTableLinear.isFull() && !hashTableDouble.isFull() && scan.hasNextLine())
						{
							StringTokenizer strtkn = new StringTokenizer(scan.nextLine());
							String key = "";
							if(strtkn.hasMoreTokens())
							{
								key = strtkn.nextToken();
							}
							hashTableLinear.insertObject(key);
							hashTableDouble.insertObject(key);
						}
						scan.close();
					}
					
					double avgProbesLinear = hashTableLinear.getAvgProbes();
					double avgProbesDouble = hashTableDouble.getAvgProbes();
					int dupeCountLinear = hashTableLinear.getDuplicateCount();
					int dupeCountDouble = hashTableDouble.getDuplicateCount();
					int capacityLinear = hashTableLinear.getCurrentCapacity();
					int capacityDouble = hashTableDouble.getCurrentCapacity();
					
					System.out.println("\nUsing Linear Hashing....\nInput " + (capacityLinear+dupeCountLinear) + " elements, of which " + dupeCountLinear + " duplicates\n"
										+ "load factor = " + loadFactor + ", Avg. no. of probes " + avgProbesLinear + "\n");
					System.out.println("Using Double Hashing....\nInput " + (capacityDouble+dupeCountDouble) + " elements, of which " + dupeCountDouble + " duplicates\n"
							+ "load factor = " + loadFactor + ", Avg. no. of probes " + avgProbesDouble + "\n");
						
					if(debugLevel == 1)
					{
						FileWriter filewr = new FileWriter(new File("linear-dump"));
						HashObject[] tableLinear = hashTableLinear.getArray();
						for(int i=0; i < hashTableLinear.getTableSize(); i++)
						{
							if(tableLinear[i] != null)
							{
								filewr.write("table[" + i + "]: " + tableLinear[i].toString() + "\n");
							}
						}
						filewr.close();
						
						filewr = new FileWriter(new File("double-dump"));
						HashObject[] tableDouble = hashTableDouble.getArray();
						for(int i=0; i < hashTableDouble.getTableSize(); i++)
						{
							if(tableDouble[i] != null)
							{
								filewr.write("table[" + i + "]: " + tableDouble[i].toString() + "\n");
							}
						}
						filewr.close();
					}
					else if(debugLevel != 0)
					{
						System.out.println("Debug level must be either 0 or 1.");
						printUsage();
					}
				}
				else
				{
					printUsage();
				}
			}
			catch(NumberFormatException nfe)
			{
				System.err.println("Number Format Exception: " + nfe.getMessage());
				printUsage();
			}
			catch(FileNotFoundException fnfe)
			{
				System.err.println("File Not Found Exception: " + fnfe.getMessage());
				printUsage();
			}
			catch(IOException ioe)
			{
				System.err.println("IO Exception: " + ioe.getMessage());
				printUsage();
			}
		}
	}
	
	private void printUsage()
	{
		System.out.println("Please follow the usage: java HashTest <input type> <load factor> [<debug level>]"
				+ "\n Where input type is either 1, 2, or 3, for integer, long, or strings respectively"
				+ "\n load factor is a 2 digit decimal that is greater than 0 but less than 1"
				+ "\n and debug level is either 0 or 1");
	}

}
