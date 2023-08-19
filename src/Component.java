import java.util.LinkedList;

public class Component {
   private String type;
   private boolean reference=false;
   private double resistance,voltage,current;
   private LinkedList<Component> element=new LinkedList<Component>();
   private LinkedList<Component> eqn=new LinkedList<Component>();
   private Component head,tail,branch;
   private double[] kvleqn;
   private double constant;
   private int index;
   private String name;
   private boolean modify=false;
   private int angle;
   Component(String type)
   {
	   this.type=type;
   }
   
   void  setResistance( double resistance)
   {
	  this.resistance=resistance;
   }
  double getResistance()
  {
	  return this.resistance;
  }
  void setAngle(int angle)
  {
	  this.angle=angle;
  }
  int getAngle()
  {
	  return this.angle;
  }
  void  setName( String name)
  {
	  this.name=name;
  }
 String getName()
 {
	 if(this.getType()=="Voltage")
	 {
		 if(this.name==null)
		 {
			return "V"; 
		 }else 
		 {
			 return this.name;
		 }
	 } else if(this.getType()=="Resistor")
	 {
		 if(this.name==null)
		 {
			return "R"; 
		 }else 
		 {
			 return this.name;
		 }
	 }else
	 {
		 return null;
	 }
	  
 }
  
  void  setVoltage(double voltage)
  {
	  this.voltage=voltage;
  }
  double getVoltage()
  {
	  return this.voltage;
  }
  void setCurrent(double current)
  {
	  this.current=current;
  }
  double getCurrent()
  {
	  return this.current;
  }
  void setConnection(Component element)
  {
	  this.element.add(element);
	  if(!this.getType().equals("Branch"))
	  {
		  
			  element.element.add(this);
		  
	  }
  }
  LinkedList<Component> getConnection()
  {
	  return this.element;
  }
  String getType()
  {
	  return this.type;
  }
  boolean getReference()
  {
	  return this.reference;
  }
  void setReference()
  {
	  this.reference=true;
  }
  void setTail(Component element)
  {
	  this.tail=element;
  }
  Component getTail()
  {
	  return this.tail;
  }
  void setHead(Component element)
  {
	  this.head=element;
  }
  Component getHead()
  {
	  return this.head;
  }
  
  void setBranch(Component element)
  {
	  this.branch=element;
  }
  Component getBranch()
  {
	  return this.branch;  
  }
  void setEqn(Component element)
  {
	  eqn.add(element);
  }
  void setIndex(int i)
  {
	  this.index=i;
  }
  int getIndex()
  {
	  return this.index;
  }
  void setkvllength(int i)
  {
	  this.kvleqn=new double[i];
  }
  int getkvllength()
  {
	  return this.kvleqn.length;
  }
  double getkvleqn(int j)
  {
	  return this.kvleqn[j];
  }
  void setConstant(double voltage,double resistance)
  {
	  this.constant+=(voltage/resistance);
  }
  double getConstant()
  {
	  return this.constant;
  }
  /*void setIn(Component element)
  {
	  this.inElement.add(element);
  }
  LinkedList<Component> getIn()
  {
	  return this.inElement;  
  }
  void setOut(Component element)
  {
	  this.outElement.add(element);
  }
  LinkedList<Component> getOut()
  {
	  return this.outElement;  
  }*/
  void kcl()
  {
	  for(Component i:eqn)
	  {
		  if(i.getConnection().getFirst()==this)
		  {
			 // System.out.println(i.getConnection().get(1).getType() +" entering "+ i.getResistance() + " "+ this.getIndex());
			   this.kvleqn[this.getIndex()]+=(1/i.getResistance()*-1);
			   if(i.getConnection().getLast().getReference()==false)
			   {
				 if(i.getConnection().getLast().getVoltage()==0)
				 {
					 this.kvleqn[i.getConnection().getLast().getIndex()]+=1/i.getResistance();
				 }else 
				 {
					 this.setConstant(i.getConnection().getLast().getVoltage()*-1,i.getResistance());
				 }
			   }
			   if(i.getVoltage()!=0 && i.getResistance()!=0)
				  {
					 //System.out.println("Sdfsdf "+i.getVoltage());
					 this.setConstant(i.getVoltage()*-1,i.getResistance());
				  }
			
		  }else  
		  {	  
			  //System.out.println(i.getConnection().get(1).getType() +" leaving "+ i.getResistance()+ " "+ this.getIndex());
			  this.kvleqn[this.getIndex()]+=(1/i.getResistance()*-1);
			  if(i.getConnection().getFirst().getVoltage()==0)
			  {
				  this.kvleqn[i.getConnection().getFirst().getIndex()]+=1/i.getResistance();
			  }else
			  {
				  this.setConstant(i.getConnection().getFirst().getVoltage()*-1,i.getResistance());
				 
			  }
			  
			  if(i.getVoltage()!=0 && i.getResistance()!=0)
			  {
				// System.out.println("Sdfsdf "+i.getVoltage());
				 this.setConstant(i.getVoltage(),i.getResistance());
			  }
		  }
		  
		  
		 
	  }
	  
  }
  
}
