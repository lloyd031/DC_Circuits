import java.util.LinkedList;

public class NodalAnalysis {
	static Component currNode;
	static double[][] matrix;
	static LinkedList<Component> branchlist=new LinkedList<Component>();
	static LinkedList<Component> node = new LinkedList<Component>();
	static LinkedList<Component> comp = new LinkedList<Component>();
	public NodalAnalysis(LinkedList<Component> comp) {
		// TODO Auto-generated method stub
		//create components
		this.comp=comp;
		//setting ground/reference node
		
		//adding components to comp array
		
		
		
		
		//determining real nodes(nodes with 3 branches and not a reference node(ground)
		
		for(Component i:comp)
		{
			if( i.getConnection().size()>2 && i.getReference()==false)
			{
				node.add(i);
			}
		}
		
		
		//creating the branches
		for(Component i:node)
		{
			currNode=i;
			for(Component j:i.getConnection())
			{
				Component branch=new Component("Branch");
				branch.setConnection(i);
			    createBranch(j,i,branch);
			    
			}
			
		}
		
		//solving for total resistance and voltages for each branch
		for(Component i: branchlist)
				{
			solveVR(i.getConnection().getLast(),null,i);	
		}
		
		//if a branch has only voltage source and is connected to ground, the node voltage will be equal to the branch voltage
		//starting with branches directly connected to the ground node
		for(Component i:branchlist)
		{
			if(i.getVoltage()!=0 && i.getCurrent()==0 && i.getResistance()==0 &&  i.getConnection().getLast().getReference()==true)
			{
					
					i.getConnection().getFirst().setVoltage(i.getVoltage());
					node.remove(i.getConnection().getFirst());
				
			}
		}
		//now to branches not directly connected to ground
		for(Component i:branchlist)
		{
			if(i.getVoltage()!=0 && i.getCurrent()==0 && i.getResistance()==0 &&  i.getConnection().getLast().getReference()==false)
			{
				if(i.getConnection().getLast().getVoltage()!=0 && i.getConnection().getFirst().getVoltage()==0)
				{
					i.getConnection().getFirst().setVoltage(i.getVoltage());
					i.getConnection().getFirst().setVoltage(i.getConnection().getFirst().getVoltage()+i.getConnection().getLast().getVoltage());
					node.remove(i.getConnection().getFirst());
				}else if(i.getConnection().getFirst().getVoltage()!=0 && i.getConnection().getLast().getVoltage()==0)
				{
					i.getConnection().getLast().setVoltage(i.getVoltage()*-1);
					i.getConnection().getLast().setVoltage(i.getConnection().getLast().getVoltage()+ i.getConnection().getFirst().getVoltage());
					node.remove(i.getConnection().getLast());
				}else if(i.getConnection().getFirst().getVoltage()==0 && i.getConnection().getLast().getVoltage()==0)
				{
					System.out.println("error");
				}
					
			}
		}
		
		for(Component i:comp)
		{
			if(i.getVoltage()!=0 && i.getType()=="wire")
			{
				System.out.println(i.getVoltage());
			}
		}
		//for printing component attributes purposes
		/*for(Component i:comp)
		{
			if(i.getConnection().size()<3)
			{
				System.out.println(i.getType()+": "+ i.getBranch().getConnection().getFirst().getType()+" , "+ i.getBranch().getConnection().getLast().getType()+" , Voltage :"+i.getBranch().getVoltage()+" , Resistance :"+i.getBranch().getResistance());
				
			}
		}*/
		//setting the length of array that represents the equation of each node
		for(Component i:node)
		{
			node.get(node.indexOf(i)).setIndex(node.indexOf(i));
			i.setkvllength(node.size());
		}
		System.out.println("circuit has "+node.size()+" nodes");
		
		//performing kcl for each nodes
		for(Component i:node)
		{
			i.kcl();
			//System.out.println(i.getkvleqn());
		}
		//creating the matrix
		matrix=new double[node.size()][node.size()];
		for(int i=0; i<node.size();i++)
		{
			for(int j=0; j<node.size(); j++)
			{
				matrix[i][j]=0;
			}
		}
		
		//getting all the kcl result from each node
		for(int i=0; i<node.size(); i++)
		{
			for(int j=0; j< node.size(); j++)
			{
				matrix[i][j]=node.get(i).getkvleqn(j);
			}
		}
		
		//printing the matrix
		for(int i=0; i<node.size();i++)
		{
			System.out.print("");
			for(int j=0; j<node.size(); j++)
			{
				System.out.print(matrix[i][j]+" ");
			}
			
			System.out.println("= "+ node.get(i).getConstant());
		}
		
		//solving for voltages
		if(node.size()==1 && branchlist.size()>1)
			{
				System.out.println("= "+node.getFirst().getConstant()/matrix[0][0]);
			}
	}
	//method for creating branches
	static void createBranch(Component element,Component prev,Component branch)
	{
		
		if(element.getBranch()==null)
		{
			//System.out.println(element.getType());
			if(element.getConnection().size()<3)
			{
				branch.setConnection(element);
				element.setBranch(branch);
				for(Component i:element.getConnection())
				{
					if(i!=prev)
					{
						createBranch(i,element,branch);
					}
				}
				
			}else
			{   
				if(branch.getHead()==null && branch.getTail()==null)
				{
					
					branch.setConnection(element);
				}
				currNode.setEqn(branch);
				element.setEqn(branch);
                branchlist.add(branch);
			}
			
				
		}
		
		
	}
	
	// method for solving the total resistance and voltages for each branch
	static void solveVR(Component element, Component prev, Component branch)
	{
		
		if(element.getType().equals("Resistor"))
		{
			branch.setResistance(branch.getResistance()+element.getResistance());
			
		}else if(element.getType().equals("V-source"))
		{
			if(element.getTail().equals(prev))
			{
				branch.setVoltage(branch.getVoltage()+element.getVoltage());
			}else
			{
				branch.setVoltage(branch.getVoltage()-element.getVoltage());
			}
		}
		if(element!=branch.getConnection().getFirst())
		{
			for(Component i:element.getConnection())
			{
				
				if(i!=prev && branch.getConnection().contains(i))
				{
					solveVR(i,element,branch);
				}
			}
		}
		
		
	}
	
	
	
}
