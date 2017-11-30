import Jama.*;
import java.util.*;
import java.lang.Math;
/*
 * Jordan Leach
 * 1/26/2017
 * Class to test if circlces are concentric (meaning they share the same center)
 */

public class Concentric {

	public final static double EPSILON = 1E-6;
	
	//rotates a vector 90 clockwise around the origin
	// rotates a point around another point
	public static Matrix cw90Rot2D(Matrix pt, Matrix origin){
		Matrix ptDiff = pt.minus(origin);
		
		double [][]Qarr = {{0,1},{-1,0}};
		Matrix Q = new Matrix(Qarr);
		Matrix ptRot =  Q.times(ptDiff);
		
		return origin.plus(ptRot);
	}
	
	//finds the 2D intersection of two lines (l0,l1), each defined by two points on the line
	//https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
	public static Matrix lineLineIntersection(Matrix l0a,Matrix l0b, Matrix l1a, Matrix l1b ) throws ParallelLinesException {
		double x1 = l0a.get(0, 0);
		double x2 = l0b.get(0, 0);
		double x3 = l1a.get(0, 0);
		double x4 = l1b.get(0, 0);
		double y1 = l0a.get(1, 0);
		double y2 = l0b.get(1, 0);
		double y3 = l1a.get(1, 0);
		double y4 = l1b.get(1, 0);
		
		double denom = (x1-x2)*(y3-y4)-(y1-y2)*(x3-x4);
		
		if(Math.abs(denom)<EPSILON) throw new ParallelLinesException();
		
		double numX = (x1*y2-y1*x2)*(x3-x4) - (x1-x2)*(x3*y4-y3*x4);
		double numY = (x1*y2-y1*x2)*(y3-y4) - (y1-y2)*(x3*y4-y3*x4);
		
		double [][] intArr = {{numX/denom},{numY/denom}};
		return new Matrix(intArr);
	}
	

	//Points are 2x1 matrices ( column vectors)
	//we're using JAMA ( http://math.nist.gov/javanumerics/jama/doc/ )... read the Matrix doc
	public static boolean areConcentric(List<Matrix> pts, double epsilon) {
		/*
	         * The code below is mine, the code above was provided for use to help create the algorithm 
	         */
		boolean pointsAreGood = false;
		if(pts.size() <= 3) {
			return true;
		}
		//get points from the Matrix
		Matrix pointA = pts.get(0);
		Matrix pointB = pts.get(1);
		Matrix pointC = pts.get(2);
		
		//midpoints of two chords
		//gets the x coordinate of the midpoint of AB
		double midXab = ((pointA.get(0, 0) + pointB.get(0, 0))/2);
		//gets the y coordinate of the midpoint of AB
		double midYab = ((pointA.get(1,0) + pointB.get(1, 0))/2);
		double[][] midpointAB = {{midXab}, {midYab}};
		Matrix midpoint1 = new Matrix(midpointAB);//coordinate of midpointAB
		//x coordinate of midpoint of BC
		double midXbc = ((pointB.get(0, 0) + pointC.get(0, 0))/2);
		//y coordinate of midpoint of BC
		double midYbc = ((pointB.get(1, 0) + pointC.get(1, 0))/2);
		double [][] midpointBC = {{midXbc}, {midYbc}};
		Matrix midpoint2 = new Matrix(midpointBC);//coordinate of midpointBC
		//rotate around 2 points starting at the midpoint to get the lines
		// of the perpendicular bisectors
		Matrix findPossibleCenter1 = cw90Rot2D(pointA, midpoint1);
		Matrix findPossibleCenter2 = cw90Rot2D(pointB, midpoint2);
		//find the possible center by getting the point that the lines intersect
		//where the perpendicular bisectors intersect should be the center
		//perpendicular bisectors intersect at the center
		
		Matrix maybeCenter = null;
		try {
			maybeCenter = lineLineIntersection(midpoint1, findPossibleCenter1, midpoint2, findPossibleCenter2);
		} catch (ParallelLinesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get the distance from each point to maybeCenter
		Matrix diff1 = pointA.minus(maybeCenter);
		double dist1 = diff1.norm2();//distance from point A to the possible center
		Matrix diff2 = pointB.minus(maybeCenter);
		double dist2 = diff2.norm2();//distance from point B to the possible center
		Matrix diff3 = pointC.minus(maybeCenter);
		double dist3 = diff3.norm2();//distance from point C to the possible center
		
		Matrix center = null;
		double radius = 0.0;
		
		
		//if the distances are the same then the maybeCenter is the definite center
		if(Math.abs(dist1-dist2)<=epsilon && Math.abs(dist2-dist3)<=epsilon && Math.abs(dist1-dist3)<=epsilon )
		{
			//establish that maybeCenter is the true center of the circle
			//set the radius equal to one of the distances
			center = maybeCenter;
			radius = dist1;
			//run a loop to test whatever given amount of points there are
			for (int i = 0; i < pts.size(); ++i) 
			{
				
				Matrix difference = pts.get(i).minus(center);
				double distanceFromCenterToPoints = difference.norm2();
				if (Math.abs(radius - distanceFromCenterToPoints) > epsilon)
				{
					return false;
				}
				else if(Math.abs(radius - distanceFromCenterToPoints) <= epsilon)
				{
					pointsAreGood = true;
				}
				//as long as the difference between the radius and distance from
				//the center is less or equal to epsilon, they are pretty much equal
			}
		}
		//returns that the boolean function is true
		return pointsAreGood; 
	}
}


