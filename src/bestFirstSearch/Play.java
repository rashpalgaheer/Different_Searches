package bestFirstSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;


public class Play {
	public static char[][] initialState=new char[3][3];
	public char[][] currentState=new char[3][3];
	public static char[][] goalState=new char[3][3];
	public static int stateCounter = 1;
	public static TreeSet<Integer> openList = new  TreeSet<Integer>();
	public static HashMap<Integer, String> path = new HashMap<Integer, String>();
	public static HashMap<String, ArrayList> inversePath = new HashMap<String, ArrayList>();
	public static HashMap<Integer, char[][]> stateMapping = new HashMap<Integer, char[][]>();
	public static boolean goalFound = false;
	public static HashMap<Integer, Integer> childParent = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> stateTilesScore = new HashMap<Integer, Integer>();
	public static HashMap<Integer, TreeSet> visitedExplore = new HashMap<Integer, TreeSet>();

	void inputFile() throws IOException
	{
		String input;
		String goal="1238B4765";
		FileReader fr = new FileReader("C:\\Users\\Rashpal Singh\\Desktop\\text.txt");
	    BufferedReader br = new BufferedReader(fr);
		
		int i = 0;
		input = br.readLine();
		
		char[] in = input.toCharArray();
		char[] gl = goal.toCharArray();
		toArray(in, initialState);
		stateMapping.put(stateCounter, initialState);
		childParent.put(1, 0);
		openList.add(1);
		
		
		TreeSet arr = new TreeSet();
		arr.add(1);
		Play.visitedExplore.put(0, arr);
		
		toArray(gl, goalState);
		Play.stateTilesScore.put(1, BestFirst.countTiles(initialState));
//		System.out.println("Initial state");
		
//		BestFirst.show(initialState);
		
		System.out.println("Goal state");
		BestFirst.show(goalState);
				
	}
	
	private void toArray(char[] ar, char[][] state) 
	{
		int i = 0;
		while(i < ar.length)
		{
			for(int x = 0; x < 3; x++)
			{
				for(int y = 0; y < 3; y++)
				{
					state[x][y]=ar[i];
					i++;
				}
			}
		}
		
	}
	
	public static void main(String args[])
	{
		Play p = new Play();
		BestFirst d= new BestFirst();
		try {
			p.inputFile();
			d.search();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
