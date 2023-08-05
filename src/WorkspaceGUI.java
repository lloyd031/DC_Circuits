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
	static Component comp[][]= new Component[27][36];
	static LinkedList<Component> complist=new LinkedList<Component>();
	public int mx=-100;
	public int my=-100;
	public int curri,currj;
	public int currAngle=0;
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
	public WorkspaceGUI()
     { this.setTitle("DC-Circuit Analysis");
    	 this.setSize(1056,714);
    	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 this.setVisible(true);
    	 this.setResizable(false);
    	 this.setLocationRelativeTo(null);
    	 BreadBoard board=new BreadBoard();
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
    	 JButton currBtn=new JButton("current source");
    	 currBtn.setLocation(130,650);
    	 currBtn.setSize(130, 25);
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
    	 rotate=new JLabel("R");
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
    		 CircuitsDiagramPanel cp =new CircuitsDiagramPanel(g);
    		 PropertyPanel vp=new PropertyPanel(g);
    		 
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
           						 g.drawImage(componentImg, j*25-23 , i*25-50/2+1,50,50,null);
    							
    							}
    							
            				 }else
            				 { 
            					 g2d.setColor(Color.decode("#5a5b5d"));
            					 g2d.fillRect(j*25 , i*25,2,2);
            					 
            				 }
    					 
    						 if(comp[i][j]!=null)
	    					 {
	    						 
    							 if(comp[i][j]==selectedComp)
		    						    {
	 								 		currAngle=comp[i][j].getAngle();
	 								 		rotate.setLocation(j*25+75/2+5, i*25+20/2+6);
	 								 		g.setColor(Color.gray);
	 								 		g.drawRect(j*25-25, i*25-25, 52, 52);
		    						    	 
		    						    }
	    							 drawComponent(comp[i][j].getType(),comp[i][j].getAngle());
	    							 g.drawImage(componentImg, j*25-23 , i*25-50/2+1,50,50,null);
	    							 String val=(comp[i][j].getType()=="Resistor")?String.valueOf(comp[i][j].getResistance()+" Ω"):String.valueOf(comp[i][j].getVoltage()+" V");
	    							 g.setColor(Color.decode("#77bdfb"));
	    							 Font stringFont = new Font( "SansSerif", Font.PLAIN, 12 );
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
    		 
    		 for(int i=0; i<complist.size(); i++)
    		 {
    			 g.setColor(Color.white);
    			 if(complist.get(i)==selectedComp)
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
    			 g.drawString(complist.get(i).getType(), 940, i*20+60);
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
			System.out.println(currj +" "+ curri);
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
			} else if(selectedComp.getType()=="V-source")
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
					createComponent(curri,currj);
					clearVal(null);
					setValue();
				}else if(component==null)
				{
					if(comp[curri][currj]!=null)
					{
						selectedComp=comp[curri][currj];
						clearVal((selectedComp.getType()=="Resistor")?vallist.getLast():vallist.get(4));
						setValue();
					}else if(comp[curri][currj-1]!=null)
					{
						if((comp[curri][currj-1].getAngle()==0 && comp[curri][currj-1].getHead()==null)  || (comp[curri][currj-1].getAngle()==180 && comp[curri][currj-1].getTail()==null))
						{
							if(connComp[0]==null)
							{
								connComp[0]=comp[curri][currj-1];
								pol[0]=(comp[curri][currj-1].getAngle()==0)?"head":"tail";
								System.out.println(pol[0]);
							}else
							{
								connComp[1]=comp[curri][currj-1];
								pol[1]=(comp[curri][currj-1].getAngle()==0)?"head":"tail";
								System.out.println(pol[1]);
								connectComponent(connComp[0],connComp[1]);
							}
						}
					} else if(comp[curri][currj+1]!=null)
					{
						if((comp[curri][currj+1].getAngle()==0 && comp[curri][currj+1].getTail()==null)  || (comp[curri][currj+1].getAngle()==180 && comp[curri][currj+1].getHead()==null))
						{
							if(connComp[0]==null)
							{
								connComp[0]=comp[curri][currj+1];
								pol[0]=(comp[curri][currj+1].getAngle()==0)?"tail":"head";
								System.out.println(pol[0]);
							}else
							{
								connComp[1]=comp[curri][currj+1];
								pol[1]=(comp[curri][currj+1].getAngle()==0)?"tail":"head";
								System.out.println(pol[1]);
								connectComponent(connComp[0],connComp[1]);
							}
						}
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
    public void connectComponent(Component a, Component b)
    {
    	Component wire=new Component("wire");
    	a.setConnection(wire);
    	wire.setConnection(b);
    	complist.add(wire);
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
    	connComp[0]=null;
    	connComp[1]=null;
    	pol[0]=null;
    	pol[1]=null;
    }
	public void createComponent(int i,int j)
	{
		
			if(comp[i][j-1]==null && comp[i][j+1]==null && comp[i][j]==null)
			{
				Component c=new Component(component);
				comp[i][j]=c;
				complist.add(c);
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
	public class VoltageClick implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			component="V-source";
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
			 }else if(selectedComp.getType()!="V-source")
			 {
				 vallist.get(5).disable();;
				
			 }
			 
			 if(selectedComp.getType()=="Resistor")
			 {
				 vallist.getLast().enable(); 
				 vallist.getLast().setText(" "+String.valueOf(selectedComp.getResistance()));
			 }else if(selectedComp.getType()=="V-source")
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
