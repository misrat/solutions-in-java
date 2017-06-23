/* 0/1 knapsack problem */
package dynamicprogramming;

/* 
 * 0/1 KnapSack Equation
 * For every item, indexed by n, if the weight of the item is less than or equal to the remaining weight of the knapsack, then
 * there are two conditions-
 * 1. the item [n] is added in the knapsack.
 * then value  = value[n] + remaining value is recursively computed with the next/previous item, 
 * the total weight of the knapsack is reduced by an amount equal to the weight of the item, which is used in further recursive calls. 
 * 
 * 2. the item [n] is not added in the knapsack. 
 * In that case, find the value recursively with the next/previous item. 
 * Thus, the max of the two values is the answer for that instance of n and weight.
 * 
 * Finally, if the weight of the item [n] is greater than the current weight of the knapsack, skip that item, do step 2.
 * 
 * recursively, 
 * find_max_value(n,max_w) = find_max_value(n-1, max_w), if W[n] > max_w,
 * 							 max( V[n] + find_max_value(n-1, max_w - W[n]), find_max_value(n-1, max_w)   if W[n] <= max_w
 * 
 * recursive is achieved with a top-down approach, i.e. start with the given problem of 'n' and 'max_value'. then, call the sub-problems 
 * as needed, and return their values. 
 * 
 * Speed can be improved by memoization, i.e. saving the results of the smaller subproblems, i.e. varying n and max_w. 
 * 
 * dynamic programming
 *  max_value[n, max_w] = 	max_value[n - 1, max_w], if W[n] > max_w
 *  						max(V[n] + max_value[n-1, max_w-W[n]], max_value[n - 1, max_w] ), if W[n] <= max_w
 * 
 * in this iterative method, start bottom-up, i.e begin with 0 weight and 0 items, and build up solution for every n and all possible
 * weights till max_w. i.e when computing for n, we also need the value of (n-1, max_w - W[n]) to have already been computed and saved in a 
 * table/2-D array.  			
 *  
 */

public class KnapsackProblemZeroOne {
	// basic recursive approach
	// since n is used as the number of items, as well as the index to an array, n=0 is a valid index position
	// care has to be taken to make sure w[0] or v[0] is not ignored due to the first if condition
	

	public static int bestValueRecursive(int itemNumberIndex, int capacity, final int[] W, final int[] V){
			if (itemNumberIndex < 0 || capacity == 0){
				return 0; //Value in the knapsack is zero if no items or no capacity
			}
			int result = 0;

			if(W[itemNumberIndex] > capacity){ //if the nth item cannot be added in the knapsack due to W[n] > permitted capacity of knapsack
				result = bestValueRecursive(itemNumberIndex-1, capacity, W, V);
			}
			else{//each item 'n' may or may not be included in the knapsack, the max of the two cases found recursively is the solution.
				// in the second case, n is decremented since in 0/1 knapsack there can be a single instance of any variable
				result = Math.max(bestValueRecursive(itemNumberIndex-1, capacity, W, V), 
						(V[itemNumberIndex] + bestValueRecursive(itemNumberIndex-1, (capacity - W[itemNumberIndex]), W, V)));
			}

			return result;
	}
	
	// same as above, except start with the first item's weight and value.
	// the terminating conditions will change to make sure n doesn't exceed the max number of items.
	public static int bestValueRecursive2(int itemNumberIndex, int capacity, final int[] W, final int[] V){
		if (itemNumberIndex >= W.length || capacity == 0){
			return 0; //Value in the knapsack is zero if no items remaining or no capacity
		}
		int result = 0;

		if(W[itemNumberIndex] > capacity){ //if the nth item cannot be added in the knapsack due to W[n] > permitted capacity of knapsack
			result = bestValueRecursive2(itemNumberIndex+1, capacity, W, V);
		}
		else{
			// each item indexed by 'n' may or may not be included in the knapsack, the max of the two cases found recursively is the solution.
			// 'n' is incremented since in 0/1 knapsack there can be a single instance of any variable.
			result = Math.max(bestValueRecursive2(itemNumberIndex+1, capacity, W, V), 
					(V[itemNumberIndex] + bestValueRecursive2(itemNumberIndex+1, (capacity - W[itemNumberIndex]), W, V)));
		}

		return result;
}
	
	//unit testing
	public static void main(String [] args){
		int capacity = 50;
		int[] V = {60,100,120};
		int[] W = {10,20,30};
		//answer = 220
		
//		int capacity = 15;
//		int[] V = {7,9,5,12,14,6,12};
//		int[] W = {3,4,2,6,7,3,5};
		//answer = 34
		
//		int numberOfItems = V.length;
//		System.out.println("Result = " + bestValueRecursive(numberOfItems-1,capacity,W,V));
		System.out.println("Result = " + bestValueRecursive2(0,capacity,W,V)); //calling bestValueRecursive2

	}

}