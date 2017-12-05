/* Jordan Leach
 * March 27, 2017
 * CSCE 350
 */
#ifndef QUICKSORT_H
#define QUICKSORT_H

#include <algorithm>

using std::swap;

//note returns INDEX of median
template<typename T> inline
int medianOf3(T A[], int l, int r){
	//this is overcommented... also, try and avoid using pointers
	T* a = A + l;//array name is just pointer to 1st (0 index) elem., + l shifts l*(T size)
	T* b = A + l + (r-l)/2;//middle item... int division rounds down
	T* c = A + r;

	//when a is a pointer, *a is the dereference operator (gives value a points to)
	T* m;
	if(*a < *b){
		if(*b < *c) m=b; 
		else if(*c < *a) m=a;
		else m=c;
	} else{ //b <=a
		if(*a < *c) m=a;
		else if(*c < *b) m=b;
		else m=c;
	}
	return m-A; //m-A is the number of elements from A[0]

}
/* medianOf3 was provided for the assignment
 * Everything below this comment was written by me
 */
//remember: l and r are INLCUSIVE (just like Lomuto)
template<typename T>
int hoarePartition(T A[], int l, int r){
  int i = l;  
  int j = r + 1;
  int m = medianOf3(A, l, r);
  std:swap(A[l], A[m]); //swap the median with the leftmost value
  T pivot = A[l]; //pivot of type T not int
  do {
    do{ i++; } while(i <= r && A[i] < pivot); //increment until i > r & A[i] >= pivot
    do{ j--; } while(j >= l && A[j] > pivot); //decrement until j < l & A[j] <= pivot
   std::swap(A[i], A[j]);
  }while(i < j);//repeat until i >= j
  std::swap(A[i], A[j]); //reverse last swap
  std::swap(A[l], A[j]);
  return j; //return result of the partitioning
}

template<typename T>
void quicksort(T A[], int l, int r){
  if (l < r) {
    int s = hoarePartition(A, l, r); // call hoarePartitioning
    quicksort(A, l, s - 1);  //recursive calls of quicksort on two sub arrays
    quicksort(A, s + 1, r);
  }
}


#endif
