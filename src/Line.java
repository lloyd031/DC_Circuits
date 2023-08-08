import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Line extends JPanel{
	public LinkedList<LinkedList<int[]>> linelist = new LinkedList<LinkedList<int[]>>();
   public Line(Graphics g, LinkedList<LinkedList<int[]>> linelist)
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
					 if(this.linelist.get(i).get(j+1)[1]==this.linelist.get(i).get(j)[1])
					 {
						 if(this.linelist.get(i).get(j+1)[0]<this.linelist.get(i).get(j)[0])
							{
							 g.fillRect(this.linelist.get(i).get(j)[0]*25-25, this.linelist.get(i).get(j)[1]*25, 25, 2);
							}else if(this.linelist.get(i).get(j+1)[0]>this.linelist.get(i).get(j)[0])
							{
								 g.fillRect(this.linelist.get(i).get(j)[0]*25, this.linelist.get(i).get(j)[1]*25, 25, 2);
							}
					 }else if(this.linelist.get(i).get(j+1)[0]==this.linelist.get(i).get(j)[0])
					 {
						 if(this.linelist.get(i).get(j+1)[1]<this.linelist.get(i).get(j)[1])
							{
							 g.fillRect(this.linelist.get(i).get(j)[0]*25, this.linelist.get(i).get(j)[1]*25-25, 2, 25);
							}else if(this.linelist.get(i).get(j+1)[1]>this.linelist.get(i).get(j)[1])
							{
								 g.fillRect(this.linelist.get(i).get(j)[0]*25, this.linelist.get(i).get(j)[1]*25, 2, 25);
							}
					 }
					}
  		 }
			 
		 }
   }
}
