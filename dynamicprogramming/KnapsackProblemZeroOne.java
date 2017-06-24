/* 0/1 knapsack problem */
package dynamicprogramming;

import java.util.Arrays;

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
	
	/* Dynamic programming to implement 0/1 KnapSack Problem.
	 * It is a bottom-up iterative approach.
	 * Use a 2 D array to save results of smaller problems, till the given problem is computed 
	 * using the saved results. 
	 * Advantage- No recursive function calls, hence recursion overhead avoided.
	 * Disadvantage- Since all possible sub-problems are computed, hence sometimes results in 
	 * more subproblems, as compared to the recursive approach where only required sub-problems are computed.
	 */
	
	public static int KnapSackDynamicProgramming(final int n, final int max_c, final int[] W, final int[] V) {

		if (n <= 0 || max_c <= 0)
			return 0;

		int[][] resultValue = new int[n + 1][max_c + 1];
		// when n = 0, value = 0
		// when max = 0, value = 0
		// initialize the table with -1,
		for (int i = 1; i < n + 1; ++i) // begin with i = 1, for i = 0 (number of items = 0), value is 0.
			Arrays.fill(resultValue[i], -1);
		for (int i = 1; i < n + 1; ++i)
			resultValue[i][0] = 0; // when max_c = 0, value is 0 for any number items

		int result = 0;
		for (int item = 1; item < n + 1; ++item) { //n+1 is the length of tghe 2d array
			for (int wt = 1; wt < max_c + 1; ++wt) {
				if (W[item - 1] > wt) //For W and V array, the index 'item' is always 1 less.
					result = resultValue[item - 1][wt];
				else {
					result = Math.max(V[item - 1] + resultValue[item - 1][wt - W[item - 1]], resultValue[item - 1][wt]);
				}
				resultValue[item][wt] = result;
			}
		}
		return resultValue[n][max_c];
	}
	
	/*****************************************************************************************/
	
	/* recursive approach with memoization. 
	 * store solutions to sub-problems and use when needed for same recursive calls. 
	 * this is also a top down approach, where the recursive calls are placed in the function stack, 
	 * till the smaller sub-problems have returned. 
	 * Use a 2-d array to store the results. */
	
	public static int bestValueRecursiveWithMem(int n, int max_w, int[] W, int[] V){
		
		int[][] resultValue = new int[n + 1][max_w +1]; //W.length gives total number of items
		
		
		//when max_wt = 0, Value is always 0
		//when n = 0, Value is always 0, Java takes care of this when creating the array
		for(int i = 1; i < resultValue.length ; ++i)
			Arrays.fill(resultValue[i], -1);	//initialize the array with -1
		for(int i = 1; i < resultValue.length; ++i ){
			resultValue[i][0] = 0;
		}
		
		//or use two for loops to avoid overwriting in col 0 with -1
//		for(int i = 1; i < resultValue.length  ; ++i )
//			for(int j = 1 ; j < resultValue[i].length; ++j)
//				resultValue[i][j] = -1;
		 
		bestValueRecursiveWithMem(n, max_w, W, V, resultValue);
		return resultValue[n][max_w]; //resultValue[n][max_w] can be used only if n = max value of no of items, not 0
	}
	
	public static int bestValueRecursiveWithMem(int n, int max_w, int[] W, int[] V, int[][] result){
		//check for correct value of n and W
		if(n <= 0 || max_w <= 0)
			return 0;
		
		//check if the sub-problem has already been computed
		if(result[n][max_w] != -1)
			return result[n][max_w];

		
		//else continue with the recursive call
		int tempResult = 0;
		
		if(W[n-1] > max_w){
			tempResult = bestValueRecursiveWithMem(n - 1, max_w, W, V, result);
		}else{
			tempResult = Math.max(bestValueRecursiveWithMem(n - 1, max_w, W, V, result), 
								  V[n-1] + bestValueRecursiveWithMem(n - 1, max_w - W[n-1], W, V, result)	);
		}
		result[n][max_w] = tempResult;
		return 	result[n][max_w];
	}
	
	/*****************************************************************************************/
	
	/* basic recursive approach
	 * since n is used as the number of items, as well as the index to an array, n=0 is a valid index position
	 * care has to be taken to make sure w[0] or v[0] is not ignored due to the first if condition */
	
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
	
	
	
	/* unit testing */
	public static void main(String [] args){
//		int capacity = 50;
//		int[] V = {60,100,120};
//		int[] W = {10,20,30};
		//answer = 220
		
		int capacity = 15;
		int[] V = {7,9,5,12,14,6,12};
		int[] W = {3,4,2,6,7,3,5};
		//answer = 34
		
//		int numberOfItems = V.length;
//		System.out.println("Result = " + bestValueRecursive(numberOfItems-1,capacity,W,V));
//		System.out.println("Result = " + bestValueRecursive2(0,capacity,W,V)); //calling bestValueRecursive2
		
//		System.out.println("Result = " + bestValueRecursiveWithMem(V.length,capacity,W,V)); //calling bestValueRecursiveWithMem
		
		System.out.println("Result = " + KnapSackDynamicProgramming(V.length,capacity,W,V)); //calling KnapSackDynamicProgramming

	}

}