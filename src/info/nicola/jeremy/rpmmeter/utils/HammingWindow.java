package info.nicola.jeremy.rpmmeter.utils;

import android.util.FloatMath;

/**
 * Simply Apply a Hamming window on the provided buffer
 * 
 * @author Jeremy
 * 
 */
public class HammingWindow {

	private int size;
	private float[] values;

	public HammingWindow(int size) {
		this.size = size;
		compute();
	}

	/**
	 * Computes array values for a Hamming window in the specified interval
	 */
	private void compute() {
		for(int i=0; i<size; i++){
			values[i]=hamming(i);
		}
	}

	private float hamming(float x){
		return (0.54f-0.56f*FloatMath.cos(2*(float)(Math.PI)/(size-1)));
	}
	
	public float[] mult(float[] input) throws Exception {
		
		// TODO: parallelization
		if (input.length == size) {
			
			float res[] = new float[input.length];
			for(int i=0; i<input.length; i++){
				res[i]=values[i]*input[i];
			}
			return res;
		}
		throw new Exception("Can't apply window if vector lengths are different");
	}
}
