import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;
import Jama.*;
import java.lang.Math;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class MyTests<T> {

	@Test
	public void testRussianMultiply(){
		for(int i = 0;i<10;++i){
			int a = (int)(Math.random()*100)+1;
			int b = (int)(Math.random()*100)+1;
			int res = a*b;
			int russRes = RussianMultiply.multiply(a, b);
			assertEquals(a + "*" + b + "=" + res+"and your code returned " + russRes,res,russRes);
		}
	}

	//http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
	// Implementing Fisher–Yates shuffle
	static void shuffleArray(int[] ar){
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }
	
	@Test
	public void testLomuto(){
		for(int i=0;i<10;++i){
			final int NUMS_SIZE = 20;
			int nums[]= new int[NUMS_SIZE];
			int start = (int)(Math.random()*100);
			for(int j = 0; j<NUMS_SIZE;++j){
				nums[j] = start+j;
			}
			shuffleArray(nums);
			int low = 5;
			int high = 15;
			int orig_nums[] = nums.clone();
			int LomutoPivotLocation = Lomuto.partition(nums, low, high);
			for(int j = 0;j<low;++j){
				if(nums[j]!=orig_nums[j]) fail("Your Lomuto Partitioning Algorithm changed something OUTSIDE the partition bounds (before it)");
			}
			for(int j = high+1;j<NUMS_SIZE;++j){
				if(nums[j]!=orig_nums[j]) fail("Your Lomuto Partitioning Algorithm changed something OUTSIDE the partition bounds (after it)");
			}
			int sorted[] = new int[high-low+1];
			for(int j = 0;j<sorted.length;++j){
				sorted[j] = orig_nums[low+j];
			}
			Arrays.sort(sorted);
			int properPosition =-1;
			int pivot = orig_nums[low];
		
			for(int j=0;j<sorted.length;++j){
				if(sorted[j] == pivot) {
					properPosition = j+low;
					break;
				}
			}
			if(nums[properPosition] != pivot){
				fail("Your pivot is in the wrong position (not where expected");
			}
			if(LomutoPivotLocation != properPosition){
				fail("Your code RETURNED a pivot location that was incorrect");
			}
			for(int j=low;j<properPosition;++j){
				if(nums[j]>pivot){
					fail("Your code has a number before the pivot that is greater than it");
				}
			}
			for(int j=properPosition+1;j<=high;++j){
				if(nums[j]<pivot){
					fail("Your code has a number after the pivot that is less than it");
				}
			}
			
			Arrays.sort(orig_nums);
			Arrays.sort(nums);
			for(int j = 0;j<nums.length;++j){
				if(orig_nums[j]!=nums[j]){
					fail("Somehow, you changed a number in your array (overwrite with a swap?)");
				}
			}
			
		}
		
	}

	private List<Boolean> getRandomState(int n){
		List<Boolean> state = new ArrayList<Boolean>(n);
		for(int i = 0;i<n;++i){
			state.add(Math.random() >0.5 );
		}
		return state;
	}
	
	private boolean compareStates(List<Boolean> a, List<Boolean> b){
		if(a.size()!=b.size()) throw new RuntimeException("Should never happen -- compareStates() is comparing states of different sizes");
		for(int i = 0;i<a.size();++i){
			if(a.get(i)!=b.get(i)) return false;
		}
		return true;
	}
	
	private List< List<Boolean> > getStates(int n){
		List<Boolean> target = getRandomState(n);
		List<Boolean> init;
		do{
			init=getRandomState(n);
		}while(compareStates(init,target));
		
		List<List<Boolean> > states=new ArrayList<List <Boolean> >();
		states.add(target);
		states.add(init);
		return states;
	}
	
	@Test
	public void testSwitchGenerator(){
		System.out.println("Testing SwitchGenerator:");
		for(int ct = 0; ct<100;++ct){
			int n = 4+(int)(Math.random()*4);
			List<List <Boolean> > states = getStates(n);
			List<Boolean> state = states.get(1);
			List<Boolean> target = states.get(0);
			
			SwitchGenerator sg = new SwitchGenerator(n);
			
			int i = 0;
			int max_iterations = (int)Math.pow(2, n);
			for(/*i outside loop*/  ; i<max_iterations;++i){
				int ind = sg.next();
				state.set(ind, !state.get(ind));//toggle
				if (compareStates(state,target)) break;//done
				
			}
			if(i==max_iterations){
				fail("Ran out of iterations... your generator is insufficient ");
			}
			else{
				System.out.println("You got it in " + (i+1) + " iterations!");
			}
		}
		
	}

	

}

