import java.util.LinkedList;
import java.util.Queue;

public class LineWire {
	private int[] target=new int[2];
	private int[] origin=new int[2];
	private Queue<int[]> queue=new LinkedList<int[]>();
	private Queue<int[]> visited=new LinkedList<int[]>();
	private LinkedList<int[]> prev=new LinkedList<int[]>();
	public LineWire(int[] origin,int[] target)
	{
		this.origin=origin;
		this.target=target;
		System.out.println(origin[0]+" "+origin[1]);
		System.out.println(target[0]+" "+target[1]);
		BFS(origin);
	}
	public void BFS(int[] axis)
	{
		visited.add(axis);
		getNeighbor(axis);
		
		    if(!queue.isEmpty())
		    {
		    	if(axis[0]==this.target[0] && axis[1]==this.target[1])
				{
					queue.clear();
				}else
				{   
					
					BFS(queue.poll());
				}
				
		    }
		    
	}
	
	public void getNeighbor(int n[])
	{
		for(int i=0;i<4; i++)
		{
			int[] curr=new int[2];
			if(i==0)
			{
				curr[0]=n[0]-1;
				curr[1]=n[1];
			}else if(i==1)
			{
				curr[0]=n[0]+1;
				curr[1]=n[1];
			}else if(i==2)
			{
				curr[0]=n[0];
				curr[1]=n[1]-1;
			}if(i==3)
			{
				curr[0]=n[0];
				curr[1]=n[1]+1;
			}
			if(!queue.contains(curr) && !visited.contains(curr))
			{
				queue.add(curr);
				
			}
			
		}
		
		
	}
	
	LinkedList<int[]> getConn()
	{
		System.out.println("fdgfd "+this.prev.size());
		return this.prev;
	}
}