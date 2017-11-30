import java.util.Arrays;

public class Lomuto {
	/*
	 * Jordan Leach
	 */

	public static int partition(int[] A, int low, int high){
		
		// set the first item as pivot before partitioning
		int p = A[low];
		int s = low;
		// 
		for (int i = low + 1; i <= high; i++) { 
			// if the current integer is less than the pivot, increment s then
			// swap A[s] and A[i]
			if (A[i] < p) {
				s++; 
				swap(A, s, i);
				}
		
		}
		// after all partitioning is complete, swap the first element and s
		swap(A, low, s);
		return s;
	}
	// simple swap method to set our new pivot
	public static void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
	
}
