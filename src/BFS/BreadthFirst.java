package BFS;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class BreadthFirst {

	Boolean flag = false;
	int counter = 1;
	public static char[][] currentState = null;
	
	public void search()
	{
		while(!checkGoalState(Play.stateMapping.get(counter)))
		{
			System.out.println("--------CURRENT STATE--------"+counter);
			System.out.println("Parent of the current state---->"+Play.childParent.get(counter));
			show(Play.stateMapping.get(counter));
			String cordinates = checkBlank(Play.stateMapping.get(counter));
			Play.path.put(counter, cordinates);
//			Play.states.add(counter);
			if(Play.inversePath.keySet().contains(cordinates))
			{
				Play.inversePath.get(cordinates).add(counter);
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
				arr.add(counter);
				Play.inversePath.put(path, arr);
			}
			System.out.println(cordinates);
			String arr[] = cordinates.split(";");
			int i = Integer.parseInt(arr[0]);
			int j = Integer.parseInt(arr[1]);
			
			generateStates(i,j, cordinates);
			counter++;
		}
		System.out.println("FOUND ON STATE----> "+ counter);
		show(Play.stateMapping.get(counter));
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

//		System.out.println(i+ " "+j);
		
		if((i - 1) >= 0)
		{
			String check = Integer.toString(i-1)+Integer.toString(j);
			
			if(!Play.inversePath.keySet().contains(check) || !Play.inversePath.get(check).contains(Play.childParent.get(counter)))
			{
			currentState = new char[3][3];
			tempState = Play.stateMapping.get(counter);
//			convertToArray(tempState);
			for(int x = 0; x < 3; x++)
			{
				for(int y = 0; y < 3; y++)
				{
					currentState[x][y]=tempState[x][y];
				}
			}
			temp = currentState[i][j];
			currentState[i][j] = currentState[i-1][j];
			currentState[i-1][j] = temp;
			Play.stateCounter++;
			System.out.println("State here--"+Play.stateCounter);
			Play.childParent.put(Play.stateCounter, counter);
			Play.stateMapping.put(Play.stateCounter, currentState);
			show(currentState);
			currentState = null;
			}
		}
		if((i + 1) <= 2)
		{
			String check = Integer.toString(i+1)+Integer.toString(j);
			
			if(!Play.inversePath.keySet().contains(check) || !Play.inversePath.get(check).contains(Play.childParent.get(counter)))
			{	
				currentState = new char[3][3];
				tempState = Play.stateMapping.get(counter);
				for(int x = 0; x < 3; x++)
				{
					for(int y = 0; y < 3; y++)
					{
						currentState[x][y]=tempState[x][y];
					}
				}
				temp = currentState[i][j];
				currentState[i][j] = currentState[i+1][j];
				currentState[i+1][j] = temp;
				Play.stateCounter++;
				System.out.println("State here--"+Play.stateCounter);
				Play.childParent.put(Play.stateCounter, counter);
				Play.stateMapping.put(Play.stateCounter, currentState);
				show(currentState);
				currentState = null;
			}
		}
		
		
		if((j - 1) >= 0)
		{
			String check = Integer.toString(i)+Integer.toString(j-1);
		
		if(!Play.inversePath.keySet().contains(check) || !Play.inversePath.get(check).contains(Play.childParent.get(counter)))
		{
			currentState = new char[3][3];
			tempState = Play.stateMapping.get(counter);
//			convertToArray(tempState);
			for(int x = 0; x < 3; x++)
			{
				for(int y = 0; y < 3; y++)
				{
					currentState[x][y]=tempState[x][y];
				}
			}
			temp = currentState[i][j];
			currentState[i][j] = currentState[i][j-1];
			currentState[i][j-1] = temp;
			Play.stateCounter++;
			System.out.println("State here--"+Play.stateCounter);
			Play.childParent.put(Play.stateCounter, counter);
			Play.stateMapping.put(Play.stateCounter, currentState);
			show(currentState);
			currentState = null;
		}
		}
		if((j + 1) <= 2)
		{
			String check = Integer.toString(i)+Integer.toString(j+1);
			
			if(!Play.inversePath.keySet().contains(check) || !Play.inversePath.get(check).contains(Play.childParent.get(counter)))
			{
			currentState = new char[3][3];
			tempState = Play.stateMapping.get(counter);
//			convertToArray(tempState);
			for(int x = 0; x < 3; x++)
			{
				for(int y = 0; y < 3; y++)
				{
					currentState[x][y]=tempState[x][y];
				}
			}
			temp = currentState[i][j];
			currentState[i][j] = currentState[i][j+1];
			currentState[i][j+1] = temp;
			Play.stateCounter++;
			System.out.println("State here--"+Play.stateCounter);
			Play.childParent.put(Play.stateCounter, counter);
			Play.stateMapping.put(Play.stateCounter, currentState);
			show(currentState);
			currentState = null;
			}
		}
	}
	private void convertToArray(char[][] tempState) 
	{
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				currentState[x][y]=tempState[x][y];
			}
		}		
	}
	public Boolean checkGoalState(char[][] cs) 
	{
		for (int i = 0; i < 3; i++) 
		{
				if (!Arrays.equals(Play.goalState[i], cs[i])) 
				{
				    return false;
				}
		}
		return true;
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
