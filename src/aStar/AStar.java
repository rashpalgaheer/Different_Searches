package aStar;
import java.lang.management.PlatformLoggingMXBean;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.text.StyledEditorKit.ItalicAction;


public class AStar {
	Boolean flag = false;
	int visited = 0;
	int exploring;
	public static char[][] currentState = null;
	
	public void search()
	{
		while(!Play.goalFound)
		{
			int checkGoal = 0;
			
			exploring = getBoard(visited);
			checkGoal = calculateFofN(Play.stateMapping.get(exploring));
			if(checkGoal == 0)
			Play.goalFound = true;
			
			if(!Play.goalFound)
			{
				
				System.out.println("--------EXPLORING STATE--------"+exploring);
				System.out.println("Parent of the current state---->"+visited);
				
				System.out.println("Open List---->"+showOpenList());
				Play.openList.remove(exploring);
				show(Play.stateMapping.get(exploring));
		
				String cordinates = checkBlank(Play.stateMapping.get(exploring));
				Play.path.put(exploring, cordinates);
				if(Play.inversePath.keySet().contains(cordinates))
				{
					Play.inversePath.get(cordinates).add(exploring);
				}
				else
				{
					String ar[] = cordinates.split(";");
					String path= ar[0]+ar[1];
					ArrayList arr = new ArrayList();
					if(Play.inversePath.get(path)!=null)
					{
					arr = Play.inversePath.get(path);
					}
					arr.add(exploring);
					Play.inversePath.put(path, arr);
				}
				System.out.println(cordinates);
				String arr[] = cordinates.split(";");
				int i = Integer.parseInt(arr[0]);
				int j = Integer.parseInt(arr[1]);
				
				generateStates(i,j, cordinates);
			}
		}
		System.out.println("FOUND ON STATE----> "+ exploring);
		show(Play.stateMapping.get(exploring));
	}
	private int getBoard(int visit) 
	{
		int noOfTiles = 10;
		int state = 0;
		Iterator iterator;
		iterator = Play.visitedExplore.get(visited).iterator();
		while (iterator.hasNext())
		{
			int x = Play.stateTilesScore.get(iterator.next());
			if(noOfTiles > x)
			{
				noOfTiles = x;
			}
		}
		iterator = Play.stateTilesScore.keySet().iterator();
		while (iterator.hasNext())
		{
			int tiles = 0;
			state = (int) iterator.next();
			tiles = Play.stateTilesScore.get(state);
			if(noOfTiles == tiles)
			{
				break;
			}
		}
		Play.stateTilesScore.remove(state);
		return state;
	}
	public String checkBlank(char[][] cs)
	{
		String path=null;
		char ch;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				ch = cs[i][j]; 
				if(ch == 'B')
				{
					path=Integer.toString(i) + ";" + Integer.toString(j);
				}
			}
		}
		return path;
	}
	private void generateStates(int i, int j, String path) 
	{
		Play.levelCounter++;
		char[][] tempState = null;
		char temp;
		if((i - 1) >= 0)
		{
			swap(i-1,j,i,j);
		}
		if((i + 1) <= 2)
		{
			swap(i+1,j,i,j);
		}
		
		
		if((j - 1) >= 0)
		{
			swap(i,j-1,i,j);
		}
		if((j + 1) <= 2)
		{
			swap(i,j+1,i,j);
		}
		System.out.println("::Open List:: "+ showOpenList());
	visited = exploring;
		
	}
	private void swap(int iTargetPos, int jTargerPos, int iPos, int jPos) 
	{

		int gn = 0;
		char[][] tempState = null;
		char temp;
		String check = Integer.toString(iTargetPos)+Integer.toString(jTargerPos);
		
		if(!Play.inversePath.keySet().contains(check) || !Play.inversePath.get(check).contains(Play.childParent.get(exploring)))
		{
		currentState = new char[3][3];
		tempState = Play.stateMapping.get(exploring);
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				currentState[x][y]=tempState[x][y];
			}
		}
		temp = currentState[iPos][jPos];
		currentState[iPos][jPos] = currentState[iTargetPos][jTargerPos];
		currentState[iTargetPos][jTargerPos] = temp;
		Play.stateCounter++;
		Play.openList.add(Play.stateCounter);
		System.out.println("State here--"+Play.stateCounter);
		gn = calculateFofN(currentState)+Play.levelCounter;
		System.out.println("G(n) here--> "+ Play.levelCounter);
		System.out.println("H(n) here--> "+ calculateFofN(currentState));
		
		System.out.println("F(n) here--> "+ gn);
		
		Play.childParent.put(Play.stateCounter, exploring);
		Play.stateTilesScore.put(Play.stateCounter, calculateFofN(currentState));
		Play.stateMapping.put(Play.stateCounter, currentState);
		if(Play.visitedExplore.keySet().contains(exploring))
		{
			Play.visitedExplore.get(exploring).add(Play.stateCounter);
		}
		else
		{
			TreeSet arr = new TreeSet();
			if(Play.visitedExplore.get(exploring)!=null)
			{
			arr = Play.visitedExplore.get(exploring);
			}
			arr.add(Play.stateCounter);
			Play.visitedExplore.put(exploring, arr);
		}
		show(currentState);
		currentState = null;
		}
	}

	private String showOpenList() 
	{
		Iterator i = Play.openList.iterator();
		String openList ="[ ";
		while(i.hasNext())
		{
			openList += i.next()+",";
		}
		openList+=" ]";
		return openList;
	}
	public static int calculateFofN(char[][] cs) 
	{
		int counter = 0;
		for (int i = 0; i < 3; i++) 
		{
			for (int j = 0; j < 3; j++) 
			{
				if (cs[i][j] !='B' && cs[i][j] != Play.goalState[i][j]) 
				{
					counter++;
				}
			}
		}
		
		
		return counter ;
	}
	
	public static void show(char[][] cs) 
	{
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				System.out.print(cs[x][y]);
			}
			System.out.println();
		}
	}
}
