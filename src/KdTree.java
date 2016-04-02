import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
/*
 * Data Structure that represents the entire Tree
 * Has an initial node initialized to null
 */
public class KdTree {
	private Node root;
	public KdTree(){
		this.root = null;
	}
	/* 
	 * Getter method
	 */
	public Node getRoot(){
		return this.root;
	}
	
	/*
	 * Inserts a node into the KdTree. If the root is Null,
	 * Create a new node and set it as the Root.
	 * Otherwise, Create a node with the corresponding Point and
	 * call insert on the Root.
	 */
	public void insert(Node T, PointAYK rec){
		if (root == null){
			root = new Node(rec);
		} else {
			Node b = new Node(0, rec);
			root.insert(b);
		//	System.out.println("INSERT METHOD: "+ b.printRegion());
		}
	}
	
	public void insertFile(Node T, File f){
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			String line = "";
			do {
				try {
					line = in.readLine();
					if (line == null) continue;
				} catch (IOException e2) {
					System.err.println("IO Exception");
				}	
				String[] array = line.split(",");
				if (array.length != 4) {
					System.out.println("Array is too long/short");
					System.exit(1);
				}
				for (int i = 0; i < array.length; i++) {
					array[i] = array[i].trim();
				}
				long id = Long.parseLong(array[0]);
				int xLoc = Integer.parseInt(array[1]);
				int yLoc = Integer.parseInt(array[2]);
				int time = Integer.parseInt(array[3]);
				
				PointAYK curr_point = new PointAYK(id, xLoc, yLoc, time);
				insert(T, curr_point);
			} while (line != null);
			try {
			 in.close();
			} catch (IOException e3){
				System.err.println("IO Exception");
			}
		} catch (FileNotFoundException e){
			System.err.println("File not Found");
		}
	}
	
	//Read From the triangles file and Query each triangle
	public void setTri(Node T, File infile, Writer outfile){
		if (T == null){
			return;
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(infile));
			String line = "";
			do {
				try {
					line = in.readLine();
					if (line == null) continue;
				} catch (IOException e2) {
					System.err.println("IO Exception");
				}	
				String[] array = line.split(",");
				if (array.length != 7) {
					System.out.println("Array is too long/short");
					System.exit(1);
				}
				for (int i = 0; i < array.length; i++) {
					array[i] = array[i].trim();
				}
				int time = Integer.parseInt(array[0]);
				Point p1 = new Point(Integer.parseInt(array[1]),
						Integer.parseInt(array[2])); 
				Point p2 = new Point(Integer.parseInt(array[3]),
						Integer.parseInt(array[4]));
				Point p3 = new Point(Integer.parseInt(array[5]),
						Integer.parseInt(array[6]));
				
				Triangle query_t = new Triangle(p1, p2, p3);
				Set<Long> solution = new HashSet<Long>();
				root.triangle(query_t, time, solution);
				try {
				outfile.write(convertSolution(solution)+"\n");
				} catch (IOException e4){
					System.err.println("IO Exception");
				}
			} while (line != null);
			try {
			 in.close();
			} catch (IOException e3){
				System.err.println("IO Exception");
			}
		} catch (FileNotFoundException e){
			System.err.println("File not Found");
		}
	}
	/*Function created for Task 3. Reads the input file and creates a Rectangle Object
	 * as well as a TimeInterval Object. The Rectangle, Time Interval, and output
	 * file are passed to the root of the tree and the algorithm in Node.java prunes
	 * appropriately based on region/interval intersections.
	 */
	public void timeIntQ(Node T, File infile, Writer outfile){
		if (T == null){
			return;
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(infile));
			String line = "";
			do {
				try {
					line = in.readLine();
					if (line == null) continue;
				} catch (IOException e2) {
					System.err.println("IO Exception");
				}	
				String[] array = line.split(",");
				if (array.length != 6) {
					System.out.println("Array is too long/short");
					System.exit(1);
				}
				for (int i = 0; i < array.length; i++) {
					array[i] = array[i].trim();
				}
				int e1 = Integer.parseInt(array[0]);
				int e2 = Integer.parseInt(array[1]);
				int x1 = Integer.parseInt(array[2]);
				int y1 = Integer.parseInt(array[3]);
				int x2 = Integer.parseInt(array[4]);
				int y2 = Integer.parseInt(array[5]);
				int width = x2-x1;
				int height = y2-y1;
				TimeInterval queryInt = new TimeInterval(e1, e2);
				Rectangle queryRec = new Rectangle(x1, y1, width+1, height+1); //used width + 1and height + 1 to make rectangle closed on all sides
				Set<Long> solutions = new HashSet<Long>();
				root.recQuery(queryRec, queryInt, solutions);
				try {
				outfile.write(convertSolution(solutions)+"\n");
				} catch (IOException e4){
					System.err.println("IO Exception");
				}
			} while (line != null);
			try {
			 in.close();
			} catch (IOException e3){
				System.err.println("IO Exception");
			}
		} catch (FileNotFoundException e){
			System.err.println("File not Found");
		}
	}
	
	public void timeAllQ(Node T, File infile, Writer outfile){
		if (T == null){
			return;
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(infile));
			String line = "";
			do {
				try {
					line = in.readLine();
					if (line == null) continue;
				} catch (IOException e2) {
					System.err.println("IO Exception");
				}	
				String[] array = line.split(",");
				if (array.length != 6) {
					System.out.println("Array is too long/short");
					System.exit(1);
				}
				for (int i = 0; i < array.length; i++) {
					array[i] = array[i].trim();
				}
				int e1 = Integer.parseInt(array[0]);
				int e2 = Integer.parseInt(array[1]);
				int x1 = Integer.parseInt(array[2]);
				int y1 = Integer.parseInt(array[3]);
				int x2 = Integer.parseInt(array[4]);
				int y2 = Integer.parseInt(array[5]);
				int width = x2-x1;
				int height = y2-y1;
				TimeInterval queryInt = new TimeInterval(e1, e2);
				//used width + 1and height + 1 to make rectangle closed on all sides
				Rectangle queryRec = new Rectangle(x1, y1, width+1, height+1); 
				HashMap<Long, BitSet> solutions = new HashMap<Long, BitSet>();
				root.recQueryWithTime(queryRec, queryInt, solutions);
				try {
				outfile.write(convertSolutionHashMap(solutions, queryInt)+"\n");
				} catch (IOException e4){
					System.err.println("IO Exception");
				}
			} while (line != null);
			try {
			 in.close();
			} catch (IOException e3){
				System.err.println("IO Exception");
			}
		} catch (FileNotFoundException e){
			System.err.println("File not Found");
		}
	}
	
	public static String convertSolution(Set<Long> solution){
		StringBuffer buf = new StringBuffer();
		int i = 0;
		for (long l : solution){
			if (i != solution.size()-1) buf.append(l+", ");
			else buf.append(l+",");
			i++;
		}
		return buf.toString();
	}
	
	public static String convertSolutionHashMap(HashMap<Long, BitSet> solution, TimeInterval queryInterval){
		StringBuffer buf = new StringBuffer();
		int i = 0;
		int intervalLength = queryInterval.endPoint()-queryInterval.startPoint()+1;
		//System.out.println(solution + " | "+ solution.get(key).size() + " | " + solution.get(key).cardinality);
		for (long key : solution.keySet()){
				if(intervalLength == solution.get(key).cardinality()){
				if (i != solution.keySet().size()-1) buf.append(key+", ");
				else buf.append(key+",");
			} else {
			
			}
			i++;
		}
		return buf.toString();
	}
	
	public void print(Node T, Writer writer){
		
		
		if (T == null){
			return;
		} else {
			try {
				
				if (T.getLeft() != null){
					writer.write(T.getLabel().getKey() + " LCHILD " + T.getLeft().getLabel().getKey() + " " + "\n" );
				}
				if (T.getRight() != null) {
					writer.write(T.getLabel().getKey() + " RCHILD " + T.getRight().getLabel().getKey() + " " + "\n");
				}
				print(T.getLeft(), writer);
				print(T.getRight(), writer);
			} catch (IOException e3){
				System.err.println("IO Exception");
			}
		}
		
	}
}
