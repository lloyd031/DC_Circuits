import java.util.LinkedList;

public class NodalAnalysis {
	static Component currNode;
	static double[][] matrix;
	static LinkedList<Component> branchlist=new LinkedList<Component>();
	private LinkedList<Component> node = new LinkedList<Component>();
	static LinkedList<Component> comp = new LinkedList<Component>();
	private String errmsg;
	public NodalAnalysis() {
		// TODO Auto-generated method stub
		
		//for printing component attributes purposes
		/*for(Component i:comp)
		{
			if(i.getConnection().size()<3)
			{
				System.out.println(i.getType()+": "+ i.getBranch().getConnection().getFirst().getType()+" , "+ i.getBranch().getConnection().getLast().getType()+" , Voltage :"+i.getBranch().getVoltage()+" , Resistance :"+i.getBranch().getResistance());
				
			}
		}*/
		
		
	}
	
	void setComp(LinkedList<Component> comp)
	{
		this.comp=comp;
		
	}
	//determining real nodes(nodes with 3 branches and not a reference node(ground)
	void determineRealNode()
	{
		
		for(Component i:comp)
			{
			if( i.getConnection().size()>2 && i.getReference()==false)
				{
						node.add(i);
				}
			}
	}
	LinkedList<Component> getNode()
	{
		return this.node;
	}
	//creating the branches
	void creatingBranches()
	{
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
	}
	//solving for total resistance and voltages for each branch
	void solvingForTotalVR()
	{
				for(Component i: branchlist)
						{
					solveVR(i.getConnection().getLast(),null,i);	
				}
	}
	//if a branch has only voltage source and is connected to ground, the node voltage will be equal to the branch voltage
	void assignVoltagesToNodes()
	{
		
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
						}else if(i.getConnection().getFirst().getVoltage()!=0 && i.getConnection().getLast().getVoltage()!=0)
						{
							this.errmsg="Error: Voltage loop detected";
						}else if(i.getConnection().getFirst().getVoltage()==0 && i.getConnection().getLast().getVoltage()==0)
						{
							this.errmsg="Error: Cannot solve supernode, we'll try to fix this";
						}
							
					}
				}
				//just to print voltages of each nodes, no direct affect to the codes
				for(Component i:comp)
				{
					if(i.getVoltage()!=0 && i.getType()=="wire")
					{
						System.out.println(i.getVoltage());
					}
				}
	}
	String getErr()
	{
		return this.errmsg;
	}
	//setting the length of array that represents the equation of each node
	void setKVLLength()
	{
				for(Component i:node)
				{
					node.get(node.indexOf(i)).setIndex(node.indexOf(i));
					i.setkvllength(node.size());
				}
				System.out.println("circuit has "+node.size()+" nodes");
				
	}
	//performing KCL for each nodes
	void runKCL()
	{
				for(Component i:node)
				{
					i.kcl();
					//System.out.println(i.getkvleqn());
				}
	}
	//getting all the KCL result from each node
	void getKCL()
	{
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
				
				//solving for voltages try
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
	//creating the matrix
	void creatingMatrix()
	{
				matrix=new double[node.size()][node.size()];
				for(int i=0; i<node.size();i++)
				{
					for(int j=0; j<node.size(); j++)
					{
						matrix[i][j]=0;
					}
				}
	}
	// method for solving the total resistance and voltages for each branch
	 void solveVR(Component element, Component prev, Component branch)
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
		}else if(element.getType().equals("Current-source"))
		{
			if(branch.getCurrent()==0)
			{
				if(element.getTail().equals(prev))
				{
					branch.setCurrent(branch.getCurrent()+element.getCurrent());
				}else
				{
					branch.setCurrent(branch.getCurrent()-element.getCurrent());
				}
			}else
			{
				errmsg="Error: Series current source detected";
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
