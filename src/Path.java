
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
