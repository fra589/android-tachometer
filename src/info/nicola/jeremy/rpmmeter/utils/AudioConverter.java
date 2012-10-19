package info.nicola.jeremy.rpmmeter.utils;

import java.nio.ByteBuffer;

/**
 * To convert PCM16 bits to float array
 * @see http://stackoverflow.com/questions/10324355/how-to-convert-16-bit-pcm-audio-byte-array-to-double-or-float-array
 * @author Jeremy
 *
 */
public class AudioConverter {

	public static float[] convert(byte[] data){
		return(floatMe(shortMe(data)));
	}
	
	private static float[] floatMe(short[] data){
		
		// TODO: parallelization
		float[] out=new float[data.length];
		for(int i=0; i<data.length; i++){
			out[i]=data[i];
		}
		return out;
	}
	
	private static short[] shortMe(byte[] data){
		short[] out=new short[data.length/2];
		ByteBuffer buffer=ByteBuffer.wrap(data);
		
		// TODO: parallelization
		for(int i=0; i<out.length; i++){
			out[i]=buffer.getShort();
		}
		return out;
	}
}
