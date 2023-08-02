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
	public int mx=-100;
	public int my=-100;
	public int curri,currj;
	Component selectedComp=null;
	public String component;
	BufferedImage componentImg=null;
     public WorkspaceGUI()
     { this.setTitle("DC-Circuit Analysis");
    	 this.setSize(916,714);
    	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 this.setVisible(true);
    	 this.setResizable(false);
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
    	 
    	 
     }
     
     public class BreadBoard extends JPanel
     {
    	 public void paintComponent(Graphics g)
    	 {
    		 Graphics2D g2d=(Graphics2D)g;
    		 g2d.setColor(Color.decode("#161b22"));
    		 g2d.fillRect(0, 0,900, 675);
    		 //
    		 
    		 for(int i=0; i<27;i++)
    		 {
    			 for(int j=0; j<36;j++)
        		 {
    				
    				 if(i!=0 && j !=0)
    				 {
	    					 if(comp[i][j]!=null)
	    					 {
	    						 
	    							 drawComponent(comp[i][j].getType());
	    							 g.drawImage(componentImg, j*25-23 , i*25-50/2+1,50,50,null);
	    							 if(comp[i][j].editing()==true)
	    						     {
	    						    	 g.setColor(Color.WHITE);
	    						    	 g.drawRect(j*25-75/2, i*25-75/2, 75, 75);
	    						     }
	    					 }
    						 if(mx>=j*25-25/2+3 && mx<=j*25+25/2+3 && my>= i*25-25/2+3 && my<i*25+25/2+3)
            				 {
    							 currj=j;
    	          				 curri=i;
    							if(component!=null)
    							{
    							 drawComponent(component);
           						 g.drawImage(componentImg, j*25-23 , i*25-50/2+1,50,50,null);
    							
    							}
    							
            				 }else
            				 { 
            					 g2d.setColor(Color.decode("#5a5b5d"));
            					 g2d.fillRect(j*25 , i*25,2,2);
            					 
            				 }
    					 
    					 
    					
    				 }
        		 }
    		 }
    		
    	 }
     }
     
     public void drawComponent(String comp)
     {
    	 try {
				componentImg=ImageIO.read(getClass().getResourceAsStream(comp+".png"));
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
				}else
				{
					if(comp[curri][currj]!=null)
					{
						selectedComp=comp[curri][currj];
						comp[curri][currj].edit(true);
					}
				}
			}else
			{
				selectedComp.edit(false);
				selectedComp=null;
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

	public void createComponent(int i,int j)
	{
		
			if(comp[i][j-1]==null && comp[i][j+1]==null)
			{
				Component c=new Component(component);
				comp[i][j]=c;
				c.edit(true);
				selectedComp=c;
				component=null;
			}
		
	}
	public class ResistorClick implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			component="resistor";
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
			component="voltage";
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
}
