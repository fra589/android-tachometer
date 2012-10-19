package info.nicola.jeremy.rpmmeter.utils;

import info.nicola.jeremy.rpmmeter.MainActivity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;

public class AudioRecorder extends Thread {

	// the delegate
	private AudioRecord recorder;
	private boolean run;
	private static final int[] SAMPLE_RATES = new int[] { 8000, 11025, 16000,
			22050, 44100 };
	private int bufferSize;
	private byte buffer[];
	private MainActivity activity;
	private int fs;

	public AudioRecorder(MainActivity activity) {
		this.activity=activity;
		start();
	}
	
	public int getFs(){
		return fs;
	}
	
	public int getSampleSize(){
		return bufferSize/2;
	}
	
	public AudioRecord getDelegate(){
		return this.recorder;
	}

	@Override
	public void start() {
		this.run = true;
		super.start();
	}

	@Override
	public void run() {
		android.os.Process
				.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		
		boolean isRunning = false;
		while (!isRunning) {
			try {
				
				int fs = getHighestSampleRate();
				bufferSize=AudioRecord.getMinBufferSize(fs,
						 AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
				buffer=new byte[bufferSize];
				
				
				this.recorder = new AudioRecord(AudioSource.MIC, fs,
						AudioFormat.CHANNEL_IN_MONO,
						AudioFormat.ENCODING_PCM_16BIT,
						AudioRecord.getMinBufferSize(fs,
								AudioFormat.CHANNEL_IN_MONO,
								AudioFormat.ENCODING_PCM_16BIT));
				
				recorder.startRecording();
				isRunning=true;
				while (run) {
					
					// TODO: this might not fill the buffer
					/*int recorded=*/recorder.read(buffer, 0, bufferSize);
					
					// feed the activity with this
					activity.feedMe(buffer);
				}
			} catch (Throwable x) {
				isRunning=false;
			}
		}
		recorder.stop();
		recorder.release();
	}

	/**
	 * @see http 
	 *      ://stackoverflow.com/questions/11549709/how-can-i-find-out-what-
	 *      sampling -rates-are-supported-on-my-tablet
	 * @return
	 */
	public static int getHighestSampleRate() {
		int i = 0;
		for (int rate : SAMPLE_RATES) {
			int bufferSize = AudioRecord
					.getMinBufferSize(rate, AudioFormat.CHANNEL_IN_MONO,
							AudioFormat.ENCODING_PCM_16BIT);

			// if returned 0 or less, can't support higher Fs
			if (!(bufferSize > 0)) {

				// TODO: add opening an AudioRecord at this Fs=> if fails, take
				// the
				// previous freq
				
				// then return the last supported frequency
				return SAMPLE_RATES[i];
			}
			i++;
		}

		// Default is 8KHz
		return SAMPLE_RATES[0];
	}

	public void close() {
		this.run = false;
	}
}
