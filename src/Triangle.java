import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
/* Triangle class to represent the query triangles. Rather than find the intersect of
 * the triangle within in each region, I Bounded the triangle inside of a rectangle and
 * I am using the rectangle as an overestimate of the triangles region. Therefore
 * during queries, we will still be able to prune parts of the k-d tree, however not as
 * efficiently.
 */
public class Triangle{
	private Point p1;
	private Point p2;
	private Point p3;
	private Rectangle boundary = null;
	
	public Triangle(Point p1, Point p2, Point p3){
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		int[] coords = findCoords(p1, p2, p3);
		boundary = new Rectangle(coords[0], coords[2],
				coords[1]-coords[0], coords[3]-coords[2]);
	}
	public Rectangle getBoundary(){
		return boundary;
	}
	
	//Finds the smallest and largest X and Y coordinates and puts them into a sorted array.
	private int[] findCoords(Point p1, Point p2, Point p3){
		int[] x = {(int) p1.getX(), (int)p2.getX(), (int)p3.getX()};
		int[] y = {(int) p1.getY(), (int)p2.getY(), (int)p3.getY()};
		Arrays.sort(x);
		Arrays.sort(y);
		int[] ret = {x[0], x[2], y[0], y[2]};
		return ret;
	}
	//Checks if a float value is between 1 and 0 inclusive
	private boolean within(float x){
		return (0f <= x && x <= 1f);
	}
	/*This function finds whether or not a point is inside the triangle using barycentric coordinates.
	 * If the query point is on one of the triangle vertices, it immediately returns true.
	 * If alpha, beta, and gamma are all between 0 and 1, then the point lies either on the lines of the triangle,
	 * or inside of it.
	 * 
	 * Source: http://stackoverflow.com/a/13301035
	 */
	public boolean intersects(Point qp){
		float det = ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));
		float factAlpha = ((p2.y - p3.y)*(qp.x - p3.x) + (p3.x - p2.x)*(qp.y - p3.y));
		float factBeta = ((p3.y - p1.y)*(qp.x - p3.x) + (p1.x - p3.x)*(qp.y - p3.y));
		
		float beta = factBeta/det;
		float alpha = factAlpha/det;
		float gamma = 1.0f-alpha-beta;
		
		
		if (qp.equals(p1) || qp.equals(p2) || qp.equals(p3)){
			return true;
			
		}
		return (within(alpha) && within(beta) && within(gamma));
		
	}
	
	
	
	//To String function used for debugging.
	public String toString(){
		return ("("+p1.x + ","+ p1.y + ") " +
				"("+p2.x + ","+ p2.y + ") " +
				"("+p3.x + ","+ p3.y + ") " );
	
	}

	
}
