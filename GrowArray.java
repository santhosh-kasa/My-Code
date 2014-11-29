// Author: SANTHOSH KUMAR KASA
// E-Mail ID: skasa@andrew.cmu.edu
// CLASS OF 2015 - MAY BATCH.
// HEINZ COLLEGE- CARNEGIE MELLON UNIVERSITY.

/**
  Function: This method increases the length of array by 4 in all plausible dimensions.
  This leverages the code presented in the class lecture on 'Growing an Array'
 */

import java.lang.reflect.Array;

public class GrowArray {
	static Object arrayGrow(Object a) {
	    Class cl = a.getClass();
	    if (!cl.isArray()) return null;
	    Class componentType = cl.getComponentType();
	    int length = Array.getLength(a);
	    int newLength = length + 4;
	    Object newArray = Array.newInstance (componentType,newLength);
	  // Copy the elements to new array   
	    System.arraycopy(a, 0, newArray, 0, length);
	  // Each element could be another array, so try to grow them  
	    for (int i =0; i<length;i++) {
           Object el = arrayGrow(Array.get(newArray, i));
           if (el != null) {
      // If its an array and growable, then replace the content of that element with new array  
            Array.set(newArray,i, el); }	    	
	    }
	    
	    return newArray;
	}

	
}
