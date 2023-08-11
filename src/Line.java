
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Line extends JPanel{
	public LinkedList<LinkedList<Path>> linelist = new LinkedList<LinkedList<Path>>();
   public Line(Graphics g, LinkedList<LinkedList<Path>> linelist)
   {
	   this.linelist=linelist;
	   g.setColor(Color.decode("#7ce38b"));
	 //draw wires
		 for(int i=0; i<this.linelist.size(); i++)
		 {
			 for(int j=0; j<this.linelist.get(i).size(); j++)
  		 {
				 if(!this.linelist.get(i).get(j).equals(this.linelist.get(i).getLast()))
					{	
					 if(this.linelist.get(i).get(j+1).getY()==this.linelist.get(i).get(j).getY())
					 {
						 if(this.linelist.get(i).get(j+1).getX()<this.linelist.get(i).get(j).getX())
							{
							 g.fillRect(this.linelist.get(i).get(j).getX()*25-25, this.linelist.get(i).get(j).getY()*25, 25, 2);
							}else if(this.linelist.get(i).get(j+1).getX()>this.linelist.get(i).get(j).getX())
							{
								 g.fillRect(this.linelist.get(i).get(j).getX()*25, this.linelist.get(i).get(j).getY()*25, 25, 2);
							}
					 }else if(this.linelist.get(i).get(j+1).getX()==this.linelist.get(i).get(j).getX())
					 {
						 if(this.linelist.get(i).get(j+1).getY()<this.linelist.get(i).get(j).getY())
							{
							 g.fillRect(this.linelist.get(i).get(j).getX()*25, this.linelist.get(i).get(j).getY()*25-25, 2, 25);
							}else if(this.linelist.get(i).get(j+1).getY()>this.linelist.get(i).get(j).getY())
							{
								 g.fillRect(this.linelist.get(i).get(j).getX()*25, this.linelist.get(i).get(j).getY()*25, 2, 25);
							}
					 }
					}
  		 }
			 
		 }
   }
}

