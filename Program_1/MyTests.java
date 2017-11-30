import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;
import Jama.*;
import java.lang.Math;
/*
 * MyTests was provided to test my algorithms for AnagramChecker and Concentric
 */


public class MyTests<T> {


	private List<Matrix> testPoints(double arcAngle,double minRadius, double maxRadius, double cRadius, int ct, boolean isValid){
		List<Matrix> pts = new ArrayList<Matrix>();
		
		double subArc = arcAngle/ct;
		
		double radius = minRadius + Math.random()*(maxRadius-minRadius);
		double [][] cArr = 
			    {{-cRadius+2*Math.random()*cRadius},
				{-cRadius+2*Math.random()*cRadius}};
		Matrix c = new Matrix(cArr);
		
		if(!isValid) ct = ct-3;//three points will be random
		
		for(int i = 0; i<ct;++i){
			double base = i*subArc;
			double ang = base + Math.random()*subArc;
			double x = radius*Math.cos(ang);
			double y = radius*Math.sin(ang);
	        double [][] ptArr = {{x},{y}};
	        Matrix pt = new Matrix(ptArr);
	        pts.add(pt);
			
		}
		
		if(!isValid){//pollute the dataset
			for(int i =0;i<3;++i){
				double [][] ptArr = 
				    {{-cRadius+2*Math.random()*cRadius},
					{-cRadius+2*Math.random()*cRadius}};
				Matrix randPt = new Matrix(ptArr);
				pts.add(randPt);
			}
		}
		
		Collections.shuffle(pts);
		return pts;
	}
	
	@Test
	public void testValidConcentricFullCircle(){
		List<Matrix> pts = testPoints(2*Math.PI,5,10,20,9,true);
		
		assertEquals("Failed on full circle valid set", true, Concentric.areConcentric(pts, Concentric.EPSILON));
	}
	
	@Test
	public void testInvalidConcentricFullCircle(){
		List<Matrix> pts = testPoints(2*Math.PI,5,10,20,9,false);
		
		assertEquals("Failed on full circle valid set", false, Concentric.areConcentric(pts, Concentric.EPSILON));
	}
	
	@Test
	public void testValidConcentricSmallArc(){
		List<Matrix> pts = testPoints(0.2*Math.PI,5,10,20,9,true);
		
		assertEquals("Failed on full circle valid set", true, Concentric.areConcentric(pts, Concentric.EPSILON));
	}
	
	@Test
	public void testInvalidConcentricSmall(){
		List<Matrix> pts = testPoints(0.2*Math.PI,5,10,20,9,false);
		
		assertEquals("Failed on full circle valid set", false, Concentric.areConcentric(pts, Concentric.EPSILON));
	}
	
	private static char int2letter(int c){
		return (char)(c+(int)'a');
	}
	
	private List<String> genAnagramSample(boolean isAnagram, int minSize, int maxSize){
		
		int sizeRange=maxSize-minSize+1;
		
		int size = (int)(minSize + Math.random()*(sizeRange+1));
		
		String a = "";
		
		for( int i = 0; i<size;++i){
			a = a + int2letter((int)(Math.random()*26));
		}
		
		ArrayList<Character> carr = new ArrayList();
		
		for(char c: a.toCharArray()){
			carr.add(c);
		}
		
		Collections.shuffle(carr);
		
		String b = "";
		
		for(char c : carr){
			b = b+ c;
		}
		
		List<String> ans = new ArrayList<String>();
		ans.add(a);
		
		if(!isAnagram) {//corrupt in some way...
			double way = Math.random()*3;
			
			int idx = (int)(Math.random()*a.length());
			
			if(way<1.0){//delete a letter
				b = b.substring(0,idx)+b.substring(idx+1,a.length());
			}
			else if(way<2.0){
				
				b = b.substring(0,idx)+ int2letter((int)(Math.random()*26)) +b.substring(idx,a.length());
			}
			else{//change a char to something else
				char curr = b.charAt((idx));
				char newChar =int2letter((int)(Math.random()*26)); 
				while(newChar == curr){
					newChar =int2letter((int)(Math.random()*26));
				}
				b = b.substring(0,idx)+newChar+b.substring(idx+1,a.length());
			}
		}
		
		ans.add(b);
		
		return ans;
	}
	
	@Test
	public void testAnagram(){
		for(int i = 0; i<1000; ++i){
			boolean isAna = i%2 == 0;
			int minSize = 4;
			int maxSize = minSize + (int)(Math.random()*5);
			
			List<String> sample = genAnagramSample(isAna,minSize,maxSize);
			
			boolean testedVal = AnagramChecker.areAnagrams(sample.get(0),sample.get(1));
			String firstcondString  = testedVal? "":" not";
			String secondcondString = isAna? "":" not";
			String msg = "Your code said that \"" + sample.get(0) +"\" and \"" + sample.get(1)
				+ "\" are" + firstcondString + " anagrams when they are" + secondcondString+".";
			assertEquals(msg,isAna,testedVal);
			if(isAna!=testedVal) System.out.println(msg);
		}
	}
	

}

