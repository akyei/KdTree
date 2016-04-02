/* This object represents a TimeInterval that is closed on the left and open on the right.
 * It can determine if a point is within the interval, or if another TimeInterval Object
 * Intersects with it's own interval.
 */

public class TimeInterval {
	private int ep1;
	private int ep2;
	
	public TimeInterval(int e1, int e2){
		int tempe2 = e2;
		int tempe1 = e1;
		e1 = Integer.min(tempe1, tempe2);
		e2 = Integer.max(tempe1, tempe2);
		this.ep1 = e1;
		this.ep2 = e2;
	}
	public TimeInterval updateInterval(int e1, int e2){
		this.ep1 = e1;
		this.ep2 = e2;
		return this;
	}
	public boolean inBetween(int p1){
		if (ep1 <= p1 && ep2 >= p1){
			return true;
		} else {
			return false;
		}
	}
	public int endPoint(){
		return ep2;
	}
	public int startPoint(){
		return ep1;
	}
	public void setTimeInt(TimeInterval t1){
		this.ep1 = t1.ep1;
		this.ep2 = t1.ep2;
	}
	public String toString(){
		return "["+this.ep1+","+this.ep2+")";
	}
	public boolean intersects(TimeInterval t1){
		if (t1.ep1 <= this.ep2 && this.ep1 <= t1.ep2){
			
			return true;
		} else {
			
			return false;
		}
	}

}
