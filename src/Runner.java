import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;


public class Runner {
	
	
	public static void main(String args[]){
	
		Writer writer, writer2, writer3, writer4;
		KdTree Tree = new KdTree();
		File infile = new File("points");
		File triangleFile = new File("triangle");
		File recFile = new File("time_int");
		File timeAllFile = new File("time_all_int");
		Tree.insertFile(Tree.getRoot(), infile);
		//Tree.getRoot().testInterval();
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("output1"), "utf-8"));
			writer2 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("output2"), "utf-8"));
			writer3 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("output3"), "utf-8"));
			writer4 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("output4"), "utf-8"));
		Tree.print(Tree.getRoot(), writer);
		Tree.setTri(Tree.getRoot(), triangleFile, writer2);
		Tree.timeIntQ(Tree.getRoot(), recFile, writer3);
		Tree.timeAllQ(Tree.getRoot(), timeAllFile, writer4);
		writer.flush();
		writer.close();
		writer2.flush();
		writer2.close();
		writer3.flush();
		writer3.close();
		writer4.flush();
		writer4.close();
		} catch (FileNotFoundException e){
			System.err.println("File not open for writing");
		} catch (UnsupportedEncodingException e2){
			System.err.println("Unnsupported Encoding");
		} catch (IOException e3){
			System.err.println("IO Exception");
		}
	}
}
