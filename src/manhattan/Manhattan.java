package manhattan;
import java.lang.management.PlatformLoggingMXBean;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class Manhattan {

	Boolean flag = false;
	int visited = 0;
	int exploring;
	public static char[][] currentState = null;
	
	public void search()
	{
		while(!Play.goalFound)
		{
			int checkDistance = 0;
			exploring = getBoard(visited);
			checkDistance = calculateDistance(Play.stateMapping.get(exploring));
			if(checkDistance == 0)
			Play.goalFound = true;
			
			if(!Play.goalFound)
			{
				System.out.println("--------CURRENT STATE--------"+exploring);
				System.out.println("Parent of the current state---->"+visited);
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
		int distance = 10;
		int state = 0;
		Iterator iterator;
		iterator = Play.visitedExplore.get(visited).iterator();
		while (iterator.hasNext())
		{
			int x = Play.manhattanDistance.get(iterator.next());
			if(distance > x)
			{
				distance = x;
			}
		}
		iterator = Play.manhattanDistance.keySet().iterator();
		while (iterator.hasNext())
		{
			int dis = 0;
			state = (int) iterator.next();
			dis = Play.manhattanDistance.get(state);
			if(dis == distance)
			{
				break;
			}
		}
		Play.manhattanDistance.clear();
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
	visited = exploring;
		
	}
	private void swap(int iTargetPos, int jTargerPos, int iPos, int jPos) 
	{

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
		System.out.println("State here--"+Play.stateCounter);
		Play.childParent.put(Play.stateCounter, exploring);
		System.out.println("Manhattan distance of State "+Play.stateCounter+"-> "+calculateDistance(currentState) );
		Play.manhattanDistance.put(Play.stateCounter, calculateDistance(currentState));
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
	public static int calculateDistance(char[][] cs) 
	{
		int manhattan = 0;
		for (int i = 0; i < 3; i++) 
		{
			for (int j = 0; j < 3; j++) 
			{
				if (cs[i][j] != 'B' && cs[i][j] != Play.goalState[i][j]) 
				{
					manhattan = manhattan + getDistance(cs, i, j);
				}
			}
		}
		
		return manhattan;
	}
	private static int getDistance(char[][] cs, int x, int y) 
	{
		int sumX = 0, sumY = 0, dis = 0;
		
		for (int i = 0; i < 3; i++) 
		{
			for (int j = 0; j < 3; j++) 
			{
				if (cs[x][y] == Play.goalState[i][j]) 
				{
					sumX = Math.abs(x - i);
					sumY = Math.abs(y - j);
					dis = sumX + sumY;
					break;
				}
			}
		}
		return dis;
	}
}
