import java.awt.Rectangle;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Set;

/*Node Class Represents each node of the K-d Tree
 * Each Node has a right child, left child, Point Data Structure
 * and also keeps track of the level
 */
public class Node implements Comparable<Node> {
	private PointAYK label = null;
	private Node left = null;
	private Node right = null;
	//Time interval which represents current node [x, y)
	private TimeInterval timeInt = null; 
	//Level of the Node in the Tree
	private int level = 0; 
	//Represents the XY-Plane
	private Rectangle region = null; 
	
	/* 
	 * Copy Constructor
	 */
	public Node(Node b){
		this.label = b.label;
		this.left = b.left;
		this.right = b.right;
		this.timeInt = b.timeInt;
		this.level = b.level;
		this.region = (Rectangle) b.getRegion().clone();
		
	}
	/*Constructor that initializes a Node with a Point,
	 * Assumes point to be at level 0 in the K-d tree.
	 */
	public Node(PointAYK label){
		this.label = label;
		region = new Rectangle(0, 0, 1024, 1024);
		this.timeInt = new TimeInterval(0, 1023);
	}
	/*Constructor that initializes a Node with a Point
	 * and a Level
	 */
	public Node(int depth, PointAYK label){
		this.level = depth;
		this.label = label;
		this.timeInt = new TimeInterval(0, 1024);
		//this.label.setLocation(label.getXLoc(), label.getYLoc());
	}
	/* 
	 * Used to test if intervals were closed properly and if they could take switched input
	 */
	public void testInterval(){
		TimeInterval myInte = new TimeInterval(1024, 0);
		TimeInterval myInte2 = new TimeInterval(5000, 1024);
		System.out.println(myInte.inBetween(0));
		System.out.println(myInte.intersects(myInte2));
		System.out.println(myInte +" " + myInte2 );
	}

	/*CompareTo function for Nodes, Determines the level of the existing node
	 * and compares the corresponding XLoc, YLoc or Time coordinate.
	 */
	private int compareTo(int level, PointAYK a, PointAYK b){
		int disc = level % 3;
		if (disc == 0 ){
			if (a.getXLoc() <= b.getXLoc()){
				return -1;
			} else {
				return 1;
			}
		} else if (disc == 1){
			if (a.getYLoc() <= b.getYLoc()){
				return -1;
			} else {
				return 1;
			}
		} else {
			if (a.getTime() <= b.getTime()){
				return -1;
			} else {
				return 1;
			}
		}
	}
	
	/*
	 * Calls the more complicated CompareTo
	 */
	public int compareTo(Node b){
		return compareTo(this.level, this.label, b.getLabel());
	}
	
	/* Range Query implemented for Part 2
	 * If the region on the current node intersects the triangle, we check to see
	 * if there the node's point is within the triangle and then proceed to
	 * call the function on both children.
	 * If the region does not intersect, We prune this part of the tree;
	 */
	public void triangle(Triangle t, int time, Set<Long> solution){
		
		if (this.getRegion().intersects(t.getBoundary()) && this.timeInt.inBetween(time)){
			if (t.intersects(this.label.getPoint()) && 
					this.getLabel().getTime() == time){	
						solution.add(this.getLabel().getKey());
					}
			
			
			if (this.left != null) this.left.triangle(t, time, solution);
			if (this.right != null) this.right.triangle(t, time, solution);
		//Prune
		} else {
			
		}
		
	}
	
	/*Main Function used to implement Task3
	 * Prunes if the query region (the rectangle and the time interval) do not
	 * intersect the current nodes XY-Region and Time Interval
	 */
	public void recQuery(Rectangle r, TimeInterval t, Set<Long> solution){
		if (this.getRegion().intersects(r) && this.timeInt.intersects(t)){
			//Query Region Intersects Node Region, Check if point is correct and within interval
			if (r.contains(this.getLabel().getPoint())
					&& t.inBetween(this.getLabel().getTime()) ){
				
				solution.add(this.getLabel().getKey());
			
			} else { //Point was not in the region or the correct time interval
				//Do nothing
			}
			
			//Check both children since the regions had a non empty intersection
			if (this.left != null) this.left.recQuery(r, t, solution);
			if (this.right != null) this.right.recQuery(r, t, solution);
		
		
		} else { //PRUNE because region did not intersect
			//System.out.println("Prune");
		}
	}
	/*Main Function used to implement Task 4
	 * Prunes if the query Region (The rectangle and the time interval) do not intersect
	 * For Points in the query region, insert the point into a HashMap with K = MID and 
	 * V = BitSet(n) where n is the length of the time interval. When a point is found within the
	 * time interval, the corresponding index in the BitSet is turned on. When the query finishes,
	 * we iterate through all values in the HashMap, if the BitSet is set to 1 everywhere, then
	 * the MID is part of a valid solution.
	 */
	
	public void recQueryWithTime(Rectangle r, TimeInterval t, HashMap<Long, BitSet> solution){
		//Query Region intersects Node Region
		if (this.getRegion().intersects(r) && this.timeInt.intersects(t)){
			//Point is within the query region
			if (r.contains(this.getLabel().getPoint()) && t.inBetween(this.getLabel().getTime())){
				long key = this.getLabel().getKey();
				
				if (solution.containsKey(key)) {
					int time = this.getLabel().getTime();
					//Set the Proper index on the BitSet of Hash<MID> (e.g timeInterval [10,15] and point is at time 11, index = 11-10 = 1.)
					solution.get(key).set(time-t.startPoint());
				} else {
					//If no key in the hashSet yet, create a new BitSet of length of the time interval. Add turn
					//the bit of the proper index ON.
					int time = this.getLabel().getTime();
					solution.put(key,  new BitSet(t.endPoint()-t.startPoint()+1));
					solution.get(key).set(time-t.startPoint());
					
				}
			} 
			//Recursively check both children
			if (this.left !=  null) this.left.recQueryWithTime(r, t, solution);
			if (this.right != null) this.right.recQueryWithTime(r, t, solution);
		} else {

				//Prune
		}
	}
	/*
	 *Insert a Node below this Node. Uses the compareTo method
	 *to determine whether to branch right or left. Method is recursive
	 *ends when the corresponding left/right child is null (empty).
	 *
	 * Branch Right if Node b is >= this.Node
	 * Branch left if Node b is < this.Node
	 * Creates a rectangular region corresponding to the Region on the x-y plane 
	 * represented by current node. (Fixed Region calculation as of Task 4)
	 * Time interval is updated as needed.
	 */
	public void insert(Node b){
		int disc = this.level % 3;
		//Node B is greater, branching right
		if (this.compareTo(b) < 0){
			if (this.right == null){
				b.level = this.level + 1;
				
				
				switch (disc) {
					case 0: 
						b.region = new Rectangle(this.getLabel().getXLoc(),
								(int)this.getRegion().getMinY(),
								(int)this.getRegion().getWidth()-this.getLabel().getXLoc(), (int)this.getRegion().getHeight());
						b.timeInt = this.timeInt;
						this.right = new Node(b);
						break;
					case 1:
						b.region = new Rectangle((int)this.getRegion().getMinX(),
								this.getLabel().getYLoc()
								,(int)this.getRegion().getWidth(), (int)this.getRegion().getHeight()-this.getLabel().getYLoc());
						b.timeInt = this.timeInt;
						this.right = new Node(b);
						break;
					case 2:
						TimeInterval t1 =  new TimeInterval(this.getLabel().getTime(), this.timeInt.endPoint());
						b.timeInt.setTimeInt(t1);
						b.region = this.region;
						this.right = new Node(b);
				}
				
			} else {
				this.right.insert(b);
			}
		} else {
			if (this.left == null){
				b.level = this.level + 1;
				
				switch (disc) {
				case 0: 
					b.region = new Rectangle((int)this.getRegion().getMinX(), 
							(int)this.region.getMinY(),
							this.getLabel().getXLoc()-(int)this.getRegion().getMinX(), (int)this.getRegion().getHeight());
					b.timeInt = this.timeInt;
					this.left = new Node(b);
					break;
				case 1:
					b.region = new Rectangle((int)this.region.getMinX(),
							(int)this.region.getMinY(),
							(int)this.region.getWidth(), this.getLabel().getYLoc()-(int)this.getRegion().getMinY());
					b.timeInt = this.timeInt;
					this.left = new Node(b);
					break;
				case 2:
					b.region = this.region;
					TimeInterval t1 =  new TimeInterval(this.timeInt.startPoint(), this.getLabel().getTime()-1);
					b.timeInt.setTimeInt(t1);
					this.left = new Node(b);
				} 
				
			} else {
				this.left.insert(b);

			}
		}
	}
	/*Getters
	 * 
	 */
	public PointAYK getLabel(){
		return label;
	}
	public Rectangle getRegion(){
		return region;
	}
	public Node getLeft(){
		return left;
	}
	public Node getRight(){
		return right;
	}
	public String printRegion(){
		return ("X: ["+this.region.getMinX()+", "+this.region.getMaxX()+"]"
				+"Y: ["+this.region.getMinY()+", "+this.region.getMaxY()+"] " + this.timeInt +" " + this.label.getTime());
	}
}
