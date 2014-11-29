// Author: SANTHOSH KUMAR KASA
// E-Mail ID: skasa@andrew.cmu.edu
// CLASS OF 2015 - MAY BATCH.
// HEINZ COLLEGE- CARNEGIE MELLON UNIVERSITY.

/**
  Function: This is a driver program for Grow Array which tests up to 3 D arrays.
  the concept is that N dimensional array is collection of N-1 dimensional arrays. 
 */


import java.util.*;
public class TestArrayGrowth {

	public static void main (String[] args) {

//One Dimensional array
// To print 1-D array 'toString' method from 'Arrays' class can be used.
		int[] a = {1,2,3,4};
		System.out.println("One Dimensional Array");
		System.out.println("Original Array");
		System.out.println(Arrays.toString(a));
		int[] b = (int[])GrowArray.arrayGrow(a);
		System.out.println("Final Array after growth");
		System.out.println(Arrays.toString(b));
		System.out.println("---------------------------------------------");

// To print 2D and higher dimension arrays deepToString method from 'Arrays' class can be used.		
// Two Dimensional Array		 
		 int[][] c = {{1,2,3},{4,5,6}};
		 System.out.println("Two Dimensional Array");
		 System.out.println("Original Array");
		 System.out.println(Arrays.deepToString(c));	
		 int[][] d = (int[][])GrowArray.arrayGrow(c);
		 System.out.println("Final Array after growth");
		 System.out.println(Arrays.deepToString(d));
		 System.out.println("---------------------------------------------");
// Three Dimensional Array
		 int[][][] e = {{{1,2},{3,4}},{{5,6},{7,8}}};
		 System.out.println("Three Dimensional Array");
		 System.out.println("Original Array");
		 System.out.println(Arrays.deepToString(e));	
		 int[][][] f = (int[][][])GrowArray.arrayGrow(e);
		 System.out.println("Final Array after growth");
		 System.out.println(Arrays.deepToString(f));
		 System.out.println("---------------------------------------------");
		 
// To test with Strings 		 
		 System.out.println("One Dimensional String Array");
    	 System.out.println("Original Array");
		 String[] names = {"Tom","Dick","Harry"};
 		 System.out.println(Arrays.toString(names));		 
    	 String[] resnames = (String[])GrowArray.arrayGrow(names);
	   	 System.out.println("Final Array after growth");
		 System.out.println(Arrays.toString(resnames)); 
		 System.out.println("---------------------------------------------");

		 testobj[] array = {new testobj(1,new String("name1")), new testobj(2,new String("name2")), new testobj(3,"name3")};
		 testobj[] resarray = (testobj[])GrowArray.arrayGrow(array);
		 System.out.println(Arrays.toString(resarray));
	}
}
