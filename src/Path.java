
public class Path {
	 private Component wire;
	 private int x;
	 private int y;
	 private Path prev;
	 private int angle;
	 private boolean junction;
     public Path(int x, int y)
     {
    	 this.x=x;
    	 this.y=y;
     }
     public void setAngle(int angle)
     {
    	 this.angle=angle;
     }
     int getAngle()
     {
    	 return this.angle;
     }
      public void setWire(Component wire)
      {
    	  this.wire=wire;
      }
      public void setJuction(boolean j)
      {
    	  this.junction=j;
      }
      boolean isJuction()
      {
    	  return this.junction;
      }
      Component getWire()
      {
    	  return this.wire;
      }
      int getX()
     {
    	 return this.x;
     }
      int getY()
     {
    	 return this.y;
     }
      Path getPrev()
     {
    	 return this.prev;
     }
    public void  setPrev(Path prev){
    	 this.prev=prev;
     }
}
