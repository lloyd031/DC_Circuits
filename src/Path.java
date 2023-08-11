
public class Path {
	 private Component wire;
	 private int x;
	 private int y;
	 private Path prev;
     public Path(int x, int y)
     {
    	 this.x=x;
    	 this.y=y;
     }
      public void setWire(Component wire)
      {
    	  this.wire=wire;
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
