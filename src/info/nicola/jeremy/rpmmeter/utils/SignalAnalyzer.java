package info.nicola.jeremy.rpmmeter.utils;

public class SignalAnalyzer {

	/**
	 * Returns the index of the Highest amplitude in the signal
	 * @param signal
	 * @return
	 */
	public static int FindHighestAmplitude(float[] signal) {
		float max = 0;
		int index=0;
		for(int i=0; i<signal.length; i++){
			if(signal[i]>max){
				max=signal[i];
				index=i;
			}
		}
		return index;
	}
}
