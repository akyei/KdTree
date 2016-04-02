import java.awt.Point;
import java.awt.geom.Point2D;
/*My own Point Class that represents the point stored in each Node. It used
 * the built in Point class in order to interact properly with the Rectangle
 * class' .contains method.
 * 
 */
public class PointAYK extends Point2D {
	private int xLoc; //Corresponds to XLOC
	private int yLoc; //Corresponds to YLOC
	private int time; //Corresponds to Time
	private long key; //Corresponds to MID
	private Point point = null; //Corresponds to the point on the XY-plane
	
	public PointAYK(long key, int x, int y, int time){
		this.key = key;
		this.xLoc = x;
		this.yLoc = y;
		this.time = time;
		point = new Point(x, y);
	}
	
	
	public Point getPoint(){
		return point;
	}
	public int getXLoc(){
		return xLoc;
	}
	
	public long getKey(){
		return key;
	}
	public int getYLoc(){
		return yLoc;
	}
	
	public int getTime(){
		return time;
	}
	@Override
	public double getX() {
		
		// TODO Auto-generated method stub
		return xLoc;
	}
	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return yLoc;
	}
	@Override
	public void setLocation(double x, double y) {
		// TODO Auto-generated method stub
		this.yLoc = (int) y;
		this.xLoc = (int) x;
		
		
	}
	public String toString(){
		return "ID: "+this.getKey() + "(" + getXLoc() +"," + getYLoc() + "," + getTime() + ")";
	}
	
}
