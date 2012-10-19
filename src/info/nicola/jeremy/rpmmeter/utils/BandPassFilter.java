package info.nicola.jeremy.rpmmeter.utils;

/**
 * Some precomputed 3rd order filters
 * @author Jeremy
 *
 */
public class BandPassFilter {

	// some precomputed filters
	public static final BandPassFilter FILTER_200_8K=new BandPassFilter(200, 8000);
	
	// coefficients
	private float a0, a1, a2;
	
	private float lastVal;
	
	private BandPassFilter(int from, int to){
		
	}
	
	public void filter(float[] signal){
		
	}
}
