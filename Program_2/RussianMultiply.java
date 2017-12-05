import java.lang.Math;

public class RussianMultiply {
	/*
	 * Jordan Leach
	 * Implementation of Russian Multiplication
	 */

public static int multiply(int a, int b){
	
	boolean aIsEven = false; 
	int rusMult = 0;
	// check to see if the first number is even
	if (a % 2 == 0) {
		aIsEven = true;
	}
	//also if the first number is 1, just return the second
	if(a == 1) {
		return b;
	}
	//if the first number is even implement Russian Multiplication regularly
	else if (aIsEven) {
		rusMult = multiply(a/2, 2*b);
	}
	//othewise if its odd use RusMult on the ((floor of a/2) and 2b) + b 
	else {
		rusMult = multiply((a-1)/2, 2*b) + b;
	}
	//return the final answer
	return rusMult;
}
	
}
