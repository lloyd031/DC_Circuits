import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class WorkspaceGUI extends JFrame {
	public Component comp[][]= new Component[27][36];
	public Path path[][]=new Path[27][36];
	public Path targetpath;
	public LinkedList<Component> complist=new LinkedList<Component>();
	public LinkedList<Component> complistlayer=new LinkedList<Component>();
	public int mx=-100;
	public int my=-100;
	public boolean running;
	public int curri,currj;
	public int currAngle=0;
	public char originarm,targetarm;  
	public Path origin;
	public Path target;
	public boolean hasground;
	public LinkedList<String> errormsg=new LinkedList<String>();
	public LinkedList<Path> line=new LinkedList<Path>();
	public LinkedList<LinkedList<Path>> linelist = new LinkedList<LinkedList<Path>>();
	Component selectedComp=null;
	public String component;
	BufferedImage componentImg=null;
	BufferedImage properiesImg=null;
	JLabel componentlbl ;
	Component connComp[]=new Component[2];
	String pol[]=new String[2];
	String[] propertyname= {"Type","name","Angle","branch", "current","voltage","Resis..."};
	LinkedList<JTextField> vallist=new LinkedList<JTextField>();
	JLabel rotate;
	NodalAnalysis nodalAnalysis=new NodalAnalysis();
	public WorkspaceGUI()
     { 
		 this.setTitle("DC-Circuit Analysis");
    	 this.setSize(1056,714);
    	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 this.setResizable(true);
    	 this.setLocationRelativeTo(null);
    	 this.setVisible(true);
    	 BreadBoard board=new BreadBoard();
    	 board.setLayout(null);
    	 this.setContentPane(board);
    	 Move move = new Move();
    	 board.addMouseMotionListener(move);
    	 Click click = new Click();
    	 board.addMouseListener(click);
    	 JButton voltBtn=new JButton("voltage source");
    	 voltBtn.setLocation(0,650);
    	 voltBtn.setSize(130, 25);
    	 this.add(voltBtn);
    	 VoltageClick voltageclick=new VoltageClick();
    	 voltBtn.addMouseListener(voltageclick);
    	 JButton currBtn=new JButton("start");
    	 currBtn.setLocation(130,650);
    	 currBtn.setSize(130, 25);
    	 CurrentClick currClick=new CurrentClick();
    	 currBtn.addMouseListener(currClick);
    	 this.add(currBtn);
    	 JButton resBtn=new JButton("resistor");
    	 resBtn.setLocation(260,650);
    	 resBtn.setSize(130, 25);
    	 this.add(resBtn);
    	 ResistorClick resistorClick=new ResistorClick();
    	 resBtn.addMouseListener(resistorClick);
    	 JButton gBtn=new JButton("ground");
    	 gBtn.setLocation(360,650);
    	 gBtn.setSize(130, 25);
    	 GroundClick groundClick=new GroundClick();
    	 gBtn.addMouseListener(groundClick);
    	 this.add(gBtn);
    	 JLabel panel = new JLabel("Circuits Diagram");
    	 panel.setForeground(Color.WHITE);
    	 panel.setSize(130, 25);
    	 panel.setLocation(925,1+1/5);
    	 this.add(panel);
    	 JLabel properties = new JLabel("Properties");
    	 properties.setForeground(Color.WHITE);
    	 properties.setSize(100, 25);
    	 properties.setLocation(925,430);
    	 this.add(properties);
    	 for(int i=0; i<propertyname.length; i++)
    	 {
    		 JTextField val =new JTextField();
			 val.setSize(55,20);
			 val.setLocation(975, i*30+465);
			 val.setBorder(null);
			 vallist.add(val);
			 this.add(val);
    	 }
    	 vallist.get(2).setEditable(false);
    	 vallist.get(3).disable();
    	 rotate=new JLabel("");
    	 rotate.setForeground(Color.white);
    	 ImageIcon rotateIcon = new ImageIcon(getClass().getResource("rotate.png"));
    	 rotate.setIcon(rotateIcon);
    	 rotate.setSize(24,24);
    	 rotate.setLocation(-100,-100);
    	 rotateClick rclick= new rotateClick();
    	 rotate.addMouseListener(rclick);
    	 this.add(rotate);
    	 
     }
     
     public class BreadBoard extends JPanel
     {
    	 
    	 public void paintComponent(Graphics g)
    	 {
    		 Graphics2D g2d=(Graphics2D)g;
    		 g2d.setColor(Color.decode("#1f1f1f"));
    		 g2d.fillRect(0, 0,900, 675);
    		 for(int i=0; i<27;i++)
    		 {
    			 for(int j=0; j<36;j++)
        		 {
    				 
    				
    				 if(i!=0 && j !=0)
    				 {
    						 if(mx>=j*25-25/2+3 && mx<=j*25+25/2+3 && my>= i*25-25/2+3 && my<i*25+25/2+3)
            				 {
    							 currj=j;
    	          				 curri=i;
    							if(component!=null)
    							{
    							 drawComponent(component,currAngle);
           						 if(component!="ground")
           						 {
           							g.drawImage(componentImg, j*25-23 , i*25-50/2+1,50,50,null);
           						 }else
           						 {
           							g.drawImage(componentImg, j*25 , i*25,24,24,null);
           						 }
    							
    							}
    							
            				 }else
            				 { 
            					 g2d.setColor(Color.decode("#5a5b5d"));
            					 g2d.fillRect(j*25 , i*25,2,2);
            					 
            				 }
    					 
    						 if(comp[i][j]!=null)
	    					 {
	    						 
    							 if(comp[i][j]==selectedComp && selectedComp.getType()!="ground")
		    						    {
	 								 		currAngle=comp[i][j].getAngle();
	 								 		rotate.setLocation(j*25+75/2+5, i*25+20/2+6);
	 								 		g.setColor(Color.gray);
	 								 		g.drawRect(j*25-25, i*25-25, 52, 52);
		    						    	 
		    						    }
	    							 
	    								 drawComponent(comp[i][j].getType(),comp[i][j].getAngle());
	    							 
    						 if(comp[i][j].getType()!="ground")
							 {
    							 g.drawImage(componentImg, j*25-24 , i*25-50/2+1,50,50,null);
							 }else
       						 {
     							g.drawImage(componentImg, j*25 , i*25,24,24,null);
     					     }
	    							 String val=(comp[i][j].getType()=="Resistor")?String.valueOf(comp[i][j].getResistance()+" Ω"):String.valueOf(comp[i][j].getVoltage()+" V");
	    							 
	    							 g.setColor(Color.decode("#77bdfb"));
	    							 Font stringFont = new Font( "SansSerif", Font.PLAIN, 14 );
	    							 g.setFont(stringFont);
	    							 if(comp[i][j].getType()!="ground")
	    							 {
	    								 if(comp[i][j].getAngle()==0 || comp[i][j].getAngle()==180)
		    							 {
		    								 g.drawString(comp[i][j].getName()+" = "+ val, j*25-25, i*25-28);
		    							 }else
		    							 {
		    								 g.drawString(comp[i][j].getName()+" = "+ val, j*25+35, i*25+25/2-10);
		    							 }
	    							 }
	    							 
	    							 
	    							 
	    					 }
    						
    					
    				 }
        		 }
    		 }
    		 Line line=new Line(g,linelist,path); 
    		 CircuitsDiagramPanel cp =new CircuitsDiagramPanel(g);
    		 PropertyPanel vp=new PropertyPanel(g);
    	 }
    	
     }
     public class PropertyPanel extends JPanel
     {
    	 public PropertyPanel(Graphics g)
    	 {
    		 
    		 Font stringFont = new Font( "SansSerif", Font.BOLD, 12 );
    		 g.setFont(stringFont);
    		 g.setColor(Color.decode("#2f2f2f"));
    		 g.fillRect(900, 455,150, 220);
    		 g.setColor(Color.decode("#404346"));
    		 g.fillRect(900, 425,150, 30);
    		 g.setColor(Color.decode("#21262d"));
    		 g.fillRect(900, 430,100, 25);
    		 //draw property icon
    		 drawComponent("properties",0 );
    		 g.setColor(Color.decode("#89929b"));
    		 g.drawRect(905, 460, 130, 210);
    		 g.fillRect(965, 460, 1, 210);
    		 g.drawImage(componentImg, 905 , 435,14,14,null);
    		 for(int i=0; i<propertyname.length; i++)
        	 {
    			 g.setColor(Color.white);
    			 g.drawString(propertyname[i], 915, i*30+480);
    			 g.setColor(Color.decode("#89929b"));
    			 g.fillRect(905, i*30+490, 130, 1);
    			 
        	 }
    		 
    	 }
     }
     public class CircuitsDiagramPanel extends JPanel
     {
    	 public CircuitsDiagramPanel(Graphics g)
    	 {
    		 g.setColor(Color.decode("#2f2f2f"));
    		 g.fillRect(900, 0,150, 565);
    		 g.setColor(Color.decode("#21262d"));
    		 g.fillRect(900, 0,150, 25);
    		 
    		 //draw icon
    		 drawComponent("energy",0);
    		 g.drawImage(componentImg, 905 , 5,14,14,null);
    		 Font stringFont = new Font( "SansSerif", Font.PLAIN, 14 );
    		 
    		 for(int i=0; i<complistlayer.size(); i++)
    		 {
    			 g.setColor(Color.white);
    			 if(complistlayer.get(i)==selectedComp)
    			 {
    				g.drawRect(932, i*20+45, 100, 20); 
    			 }
    			 if(i==0)
    			 {
    				g.fillRect(913, i*20+35, 4, 4);
    			 }
    			 
    			 
    			 g.setFont(stringFont);
    			 g.fillRect(915, i*20+35, 1+1/2, 20);
    			 g.fillRect(915, i*20+55, 10 , 1+1/2);
    			 g.fillRect(925, i*20+53, 4, 4);
    			 g.drawString(complistlayer.get(i).getType()+"("+complistlayer.get(i).getName()+")", 940, i*20+60);
    		 }
    	 }
     }
     public void drawComponent(String comp,int compAngle)
     {
    	 try {
				if(comp!="Resistor")
				{
					componentImg=ImageIO.read(getClass().getResourceAsStream(comp+compAngle+".png"));
				}else
				{
					if(compAngle==0 || compAngle==180)
					{
						componentImg=ImageIO.read(getClass().getResourceAsStream(comp+"0.png"));
					}else 
					{
						componentImg=ImageIO.read(getClass().getResourceAsStream(comp+"90.png"));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }
     public class Move implements MouseMotionListener
     {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			mx=e.getX();
			my=e.getY();
			
		}
    	 
     }
     public void saveVal()
     {
    	 String name="R";
    	 if(selectedComp.getType()=="Resistor")
			{
    		 name="R";
				if(vallist.getLast().getText()!=null)
				{
					try
					{
						selectedComp.setResistance(Double.parseDouble(vallist.getLast().getText()));
					}catch(Exception err) {
						selectedComp.setResistance(0);
					}
				}else
				{
					selectedComp.setResistance(0);
				}
			} else if(selectedComp.getType()=="Voltage")
			{
				name="V";
				if(vallist.get(5).getText()!=null)
				{
					try
					{
						selectedComp.setVoltage(Double.parseDouble(vallist.get(5).getText()));
					}catch(Exception err) {
						selectedComp.setVoltage(0);
					}
				}else
				{
					selectedComp.setVoltage(0);
				}
			}
    	 
    	 if(!vallist.get(1).getText().equals(""))
    	 {
    		 selectedComp.setName(vallist.get(1).getText());
    	 }else
    	 {
    		 selectedComp.setName(selectedComp.getName());
    	 }
    	 selectedComp.setAngle(currAngle);
    	 
     }
     public class Click implements MouseListener
     {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(selectedComp==null)
			{
				if(component!=null)
				{
					if(component!="ground")
					{
						createComponent(curri,currj);
						clearVal(null);
						setValue();
					}else
					{
						if(path[curri][currj]!=null)
						{
							path[curri][currj].getWire().setReference();
							createComponent(curri,currj);
						}
					}
				}else if(component==null)
				{
					if(comp[curri][currj]!=null)
					{
						selectedComp=comp[curri][currj];
						clearVal((selectedComp.getType()=="Resistor")?vallist.getLast():vallist.get(4));
						setValue();
					}else if(comp[curri][currj-1]!=null && path[curri][currj]==null)
					{
						if((comp[curri][currj-1].getAngle()==0 && comp[curri][currj-1].getHead()==null)  || (comp[curri][currj-1].getAngle()==180 && comp[curri][currj-1].getTail()==null))
						{
							if(connComp[0]==null)
							{
								connComp[0]=comp[curri][currj-1];
								pol[0]=(comp[curri][currj-1].getAngle()==0)?"head":"tail";
								originarm='r';
								setOrigin(currj,curri);
							}else
							{
								if(connComp[0]!=comp[curri][currj-1])
								{
									connComp[1]=comp[curri][currj-1];
									pol[1]=(comp[curri][currj-1].getAngle()==0)?"head":"tail";
									
									setTarget(currj,curri);
									connectComponent(connComp[0],connComp[1]);
								}
								
							}
						}
					} else if(comp[curri][currj+1]!=null && path[curri][currj]==null)
					{
						if((comp[curri][currj+1].getAngle()==0 && comp[curri][currj+1].getTail()==null)  || (comp[curri][currj+1].getAngle()==180 && comp[curri][currj+1].getHead()==null))
						{
							if(connComp[0]==null)
							{
								connComp[0]=comp[curri][currj+1];
								pol[0]=(comp[curri][currj+1].getAngle()==0)?"tail":"head";
								originarm='l';
								setOrigin(currj,curri);
							}else
							{
								if(connComp[0]!=comp[curri][currj+1])
								{
									connComp[1]=comp[curri][currj+1];
									pol[1]=(comp[curri][currj+1].getAngle()==0)?"tail":"head";
									
									setTarget(currj,curri);
									connectComponent(connComp[0],connComp[1]);
								}
							}
						}
					}else if(comp[curri+1][currj]!=null && path[curri][currj]==null)
					{
						if((comp[curri+1][currj].getAngle()==90 && comp[curri+1][currj].getHead()==null)  || (comp[curri+1][currj].getAngle()==270 && comp[curri+1][currj].getTail()==null))
						{
							if(connComp[0]==null)
							{
								connComp[0]=comp[curri+1][currj];
								pol[0]=(comp[curri+1][currj].getAngle()==90)?"head":"tail";
								originarm='t';
								setOrigin(currj,curri);
							}else
							{
								if(connComp[0]!=comp[curri+1][currj])
								{
									connComp[1]=comp[curri+1][currj];
									pol[1]=(comp[curri+1][currj].getAngle()==90)?"head":"tail";
									setTarget(currj,curri);
									connectComponent(connComp[0],connComp[1]);
								}
							}
						}
					}else if(comp[curri-1][currj]!=null && path[curri][currj]==null)
					{
						if((comp[curri-1][currj].getAngle()==90 && comp[curri-1][currj].getTail()==null)  || (comp[curri-1][currj].getAngle()==270 && comp[curri-1][currj].getHead()==null))
						{
							if(connComp[0]==null)
							{
								connComp[0]=comp[curri-1][currj];
								pol[0]=(comp[curri-1][currj].getAngle()==90)?"tail":"head";
								originarm='b';
								setOrigin(currj,curri);
							}else
							{
								if(connComp[0]!=comp[curri-1][currj])
								{
									connComp[1]=comp[curri-1][currj];
									pol[1]=(comp[curri-1][currj].getAngle()==90)?"tail":"head";
									setTarget(currj,curri);
									connectComponent(connComp[0],connComp[1]);
								}  
								
							}
						}
					}else if(connComp[0]!=null && path[curri][currj]!=null)
					{
						targetpath=path[curri][currj];
						connComp[1]=path[curri][currj].getWire();
						setTarget(currj,curri);
						path[curri][currj].setJuction(true);
						connectComponent(connComp[0],connComp[1]);
						
					}
				}
			}else
			{
				if(comp[curri][currj]==null)
				{
					saveVal();
					selectedComp=null;
					clearVal(null);
				}else if(comp[curri][currj]!=selectedComp)
				{
					saveVal();
					selectedComp=comp[curri][currj];
					clearVal(null);					setValue();
				}else 
				{
					component=selectedComp.getType();
					complist.remove(comp[curri][currj]);
					complistlayer.remove(comp[curri][currj]);
					comp[curri][currj]=null;
					
					selectedComp=null;
				}
				rotate.setLocation(-100,-100);
			}
			 
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
    	 
     }
     public void setOrigin(int x, int y)
     {
    	 origin=new Path(x,y);
     }
     public void setTarget(int x, int y)
     {
    	 target=new Path(x,y);
     }
    public void connectComponent(Component a, Component b)
    {
    	
    	Component wire=(connComp[1].getType().equals("wire"))?b:new Component("wire");
        if(wire!=b)
        {
        	complist.add(wire);
        }
    	a.setConnection(wire);
        if(!b.getType().equals("wire"))
        {
        	wire.setConnection(b);
        }
    	if(pol[0]=="head")
    	{
    		a.setHead(wire);
    	}else
    	{
    		a.setTail(wire);
    	}
    	if(pol[1]=="head")
    	{
    		b.setHead(wire);
    	}else
    	{
    		b.setTail(wire);
    	}
    	if(path[curri][currj]==null)
    	{
    		if(origin.getX()<target.getX())
        	{
    			if(origin.getY()>target.getY())
    			{
    				if(originarm=='t' || originarm=='l')
    				{
    					Path temp;
        	    		temp=origin;
        	    		origin=target;
        	    		target=temp;
    				}
    			}else
    			{
    				if(originarm=='b' || originarm=='l')
    				{
    					Path temp;
        	    		temp=origin;
        	    		origin=target;
        	    		target=temp;
    				}
    			}
        	
        	}else
        	{
        		if(origin.getY()<target.getY())
        		{
        			if(originarm=='b' || originarm=='r')
        			{
        				Path temp;
        	    		temp=origin;
        	    		origin=target;
        	    		target=temp;
        			}
        		}else if(origin.getY()>target.getY())
        		{
        			if(originarm=='t')
        			{
        				Path temp;
        	    		temp=origin;
        	    		origin=target;
        	    		target=temp;
        			}
        		}
        	}
    	}else
    	{
    		if(origin.getX()>target.getX())
    		{
    			if((origin.getY()>target.getY() && (originarm=='t'||originarm=='l'))||(origin.getY()<target.getY() && (originarm=='b'||originarm=='r')))
    			{
    				Path temp;
    	    		temp=origin;
    	    		origin=target;
    	    		target=temp;
    			}
    		}else
    		{
    			if((origin.getY()>target.getY() && (originarm=='t'||originarm=='r'))||(origin.getY()<target.getY() && (originarm=='b'||originarm=='l')))
    			{
    				Path temp;
    	    		temp=origin;
    	    		origin=target;
    	    		target=temp;
    			}
    		}
    	}
    	/*if(origin.getX()<target.getX())
    	{
    		if(origin.getY()>target.getY())
    		{
    			if(comp[target.getY()][target.getX()+1]!=null &&(comp[target.getY()][target.getX()+1].getAngle()==0 || comp[target.getY()][target.getX()+1].getAngle()==180))
    			{
    				Path temp;
    	    		temp=origin;
    	    		origin=target;
    	    		target=temp;
    			}
    		}else if(origin.getY()<target.getY())
    		{
    			if(comp[target.getY()][target.getX()+1]!=null &&(comp[target.getY()][target.getX()+1].getAngle()==0 || comp[target.getY()][target.getX()+1].getAngle()==180))
    			{
    				Path temp;
    	    		temp=origin;
    	    		origin=target;
    	    		target=temp;
    			}
    		}
    			
    	}else if(origin.getX()>target.getX())
    	{
    		if(origin.getY()>target.getY())
    		{
    			if(comp[target.getY()][target.getX()-1]!=null &&(comp[target.getY()][target.getX()-1].getAngle()==0 || comp[target.getY()][target.getX()-1].getAngle()==180))
    			{
    				Path temp;
    	    		temp=origin;
    	    		origin=target;
    	    		target=temp;
    			}
    		}else if(origin.getY()<target.getY())
    		{
    			if((comp[target.getY()][target.getX()-1]!=null &&(comp[target.getY()][target.getX()-1].getAngle()==0 || comp[target.getY()][target.getX()-1].getAngle()==180)))
    			{
    				Path temp;
    	    		temp=origin;
    	    		origin=target;
    	    		target=temp;
    			}
    		}
    			
    	}*/
    	
    	LineWire drawline= new LineWire(origin,target,wire,(b.getType().equals("wire"))?true:false,comp);
    	line=drawline.getPath();
    	linelist.add(line);
    	connComp[0]=null;
    	connComp[1]=null;
    	pol[0]=null;
    	pol[1]=null;
    	origin=null;
    	target=null;
    }
	public void createComponent(int i,int j)
	{
		
			if(comp[i][j-1]==null && comp[i][j+1]==null && comp[i][j]==null)
			{
				Component c=new Component(component);
				comp[i][j]=c;
				
				if(component!="ground")
				{
					complist.add(c);
					complistlayer.add(c);
				}
				selectedComp=c;
				saveVal();
				component=null;
			}
		
	}
	public class ResistorClick implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			component="Resistor";
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		
		
	}
	public class CurrentClick implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			//checking for errors
			if(!complist.isEmpty())
			{
				for(Component i: complist) 
				{
					if(i.getReference()==true)
					{
						hasground=true;
					}
					
					if((i.getType()=="Voltage" && i.getVoltage()==0) || i.getType()=="Resistor" && i.getResistance()==0)
					{
						errormsg.add( i.getType()+"("+i.getName()+") "+"has 0 value ");
					}
					if(i.getConnection().size()<2)
					{
						errormsg.add("connection incomplete "+ i.getType()+"("+i.getName()+") ");
					}
				}
				   if(hasground==true && errormsg.isEmpty())
				   {
					   nodalAnalysis.setComp(complist);
					   nodalAnalysis.determineRealNode();
					   if( nodalAnalysis.getNode().size()==0)
					   {
						  System.out.println("0 node");
					   }else
					   {
						   nodalAnalysis.creatingBranches();
						   nodalAnalysis.solvingForTotalVR();
						   if(nodalAnalysis.getErr()==null)
						   {
							   nodalAnalysis.assignVoltagesToNodes();
							   if(nodalAnalysis.getErr()!=null)
							   {
								   System.out.println(nodalAnalysis.getErr());
							   }else
							   {
								   nodalAnalysis.setKVLLength();
								   nodalAnalysis.runKCL();
								   nodalAnalysis.getKCL();
							   }
						   }else
						   {
							   System.out.println(nodalAnalysis.getErr());
						   }
						   
					   }
				   }else
				   {
					   if(hasground==false)
					   {
						   System.out.println("reference node: Null");
					   }
					   for(String i:errormsg)
					   {
						   System.out.println(i);
					   }
				   }
			}else
			{
				System.out.println("error: Blank circuit");
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public class VoltageClick implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			component="Voltage";
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}public class GroundClick implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			component="ground";
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public class rotateClick implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(selectedComp!=null)
			{
				if(selectedComp.getAngle()<270)
				{
					selectedComp.setAngle(selectedComp.getAngle()+90);
					currAngle=selectedComp.getAngle();
				}else
				{
					selectedComp.setAngle(0);
					currAngle=0;
				}
				setValue();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public void setValue()
	 {
		 if(selectedComp!=null)
		 {
			 vallist.getFirst().setText(" "+selectedComp.getType());
			 if(selectedComp.getType()!="Resistor")
			 {
				 vallist.getLast().disable();
			 }else if(selectedComp.getType()!="Voltage")
			 {
				 vallist.get(5).disable();;
				
			 }
			 
			 if(selectedComp.getType()=="Resistor")
			 {
				 vallist.getLast().enable(); 
				 vallist.getLast().setText(" "+String.valueOf(selectedComp.getResistance()));
			 }else if(selectedComp.getType()=="Voltage")
			 {
				 vallist.get(5).enable();
				 vallist.get(5).setText(" "+String.valueOf(selectedComp.getVoltage()));
			 }
			 
			 vallist.get(1).setText(selectedComp.getName());
			 vallist.get(2).setText(String.valueOf(" "+selectedComp.getAngle()+" \u00B0"));
		 }
	 }
	
	public void clearVal(JTextField j)
	{
		for(JTextField i:vallist)
		{
			if(i!=j)
			{
				i.setText("");
			}
		}
		rotate.setLocation(-100,-100);
		currAngle=0;
	}
}
