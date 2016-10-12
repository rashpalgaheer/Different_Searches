package BFS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import BFS.BreadthFirst;

public class Play {
	public char[][] initialState=new char[3][3];
	public char[][] currentState=new char[3][3];
	public static char[][] goalState=new char[3][3];
	
	public static String[][] initial = new String[3][3];
	
	public static ArrayList states = new ArrayList();
	public static int stateCounter = 1;
	
	public static HashMap<Integer, String> path = new HashMap<Integer, String>();
	public static HashMap<String, ArrayList> inversePath = new HashMap<String, ArrayList>();
	public static HashMap<Integer, char[][]> stateMapping = new HashMap<Integer, char[][]>();
	
	public static ConcurrentHashMap<Integer, ArrayList<String>> dump = new ConcurrentHashMap<Integer, ArrayList<String>>();

	public static HashMap<Integer, String[][]> STRMapping = new HashMap<Integer, String[][]>();
	public static HashMap<Integer, Integer> childParent = new HashMap<Integer, Integer>();

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
//		state.add(1);
		stateMapping.put(stateCounter, initialState);
		childParent.put(1, 0);
		toArray(gl, goalState);

		System.out.println("Initial state");
		
		BreadthFirst.show(initialState);
		
		System.out.println("Goal state");
		BreadthFirst.show(goalState);
				
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
		BreadthFirst b= new BreadthFirst();
		try {
			p.inputFile();
			b.search();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
