package info.nicola.jeremy.rpmmeter.utils;

import android.util.FloatMath;
import edu.emory.mathcs.jtransforms.fft.FloatFFT_1D;

public class SignaProcessor {

//	private MainActivity activity;
	private HammingWindow window;
	private FloatFFT_1D fft;
	private float max; // used for normalization
	private float[] data;
	private float[] windowedData;
	private float[] spectrum;
	private int spectrogramSize;
	private float[][] spectrogram;
	
	
	public SignaProcessor(/*MainActivity activity,*/ int spectrogramSize){
//		this.activity=activity;
		window=null;
		fft=null;
	}
	
	public void processData(byte[] data) {
		if(window==null){
			window=new HammingWindow(data.length/2);
			windowedData=new float[data.length/2];
		}
		if(fft==null){
			fft=new FloatFFT_1D(data.length/2);
			this.data=new float[data.length/2];
			this.spectrum=new float[data.length/4];
			this.spectrogram=new float[spectrogramSize][data.length/4];
		}
		try {
			this.data=AudioConverter.convert(data);
			windowedData=window.mult(this.data);
			float[] fftData=new float[windowedData.length];
			System.arraycopy(this.data, 0, fftData, 0, windowedData.length);
			fft.realForward(fftData);
			this.spectrum=spectralEnergyDensity(fftData);
			shiftSpectrum();
			
		} catch (Exception e) {
			// Hopefully, this should not happen
			e.printStackTrace();
		}
	}
	
	private float[] spectralEnergyDensity(float [] fftData){
		float[] res=new float[fftData.length/2];
		max=0;
		
		// TODO: Parallelization
		for(int i=0; i<fftData.length/2; i++){
			float real=fftData[i*2];
			float im=fftData[i*2+1];
			res[i]=FloatMath.sqrt(real*real+im*im);
			if(res[i]>max)max=res[i];
		}
		
		// normalization
		for(int i=0; i<res.length; i++){
			res[i]/=max;
		}
		return res;
	}
	
	/**
	 * Builds the spectrogram by inserting the current
	 * spectrum at the begining of the spectrogram
	 * TODO: Parallelize it
	 */
	private void shiftSpectrum(){
		for(int x=1; x<spectrogram.length; x++){
			System.arraycopy(spectrogram[x-1], 0, spectrogram[x], 0, spectrogram[0].length);
		}
		System.arraycopy(spectrum, 0, spectrogram[0], 0, spectrogram[0].length);
	}

	public HammingWindow getWindow() {
		return window;
	}

	public FloatFFT_1D getFft() {
		return fft;
	}

	public float getMax() {
		return max;
	}

	public float[] getData() {
		return data;
	}

	public float[] getWindowedData() {
		return windowedData;
	}

	public float[] getSpectrum() {
		return spectrum;
	}

	public int getSpectrogramSize() {
		return spectrogramSize;
	}

	public float[][] getSpectrogram() {
		return spectrogram;
	}
	
}
