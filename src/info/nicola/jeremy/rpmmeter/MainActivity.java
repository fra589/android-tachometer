package info.nicola.jeremy.rpmmeter;

import com.example.android_tachometer.R;

import info.nicola.jeremy.rpmmeter.utils.AudioRecorder;
import info.nicola.jeremy.rpmmeter.utils.SignaProcessor;
import info.nicola.jeremy.rpmmeter.utils.SignalAnalyzer;
import info.nicola.jeremy.rpmmeter.views.SettingsView;
import android.app.TabActivity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	/**
	 * Parameters
	 */
	public static final int SPECTROGRAM_SIZE = 400;

	private AudioRecorder recorder;
	private SignaProcessor processor;
	private int currentMainFreq;

	protected Resources res;

	public void feedMe(byte[] data) {
		processor.processData(data);
		// TODO: do something with it
		int hIndex = SignalAnalyzer.FindHighestAmplitude(processor
				.getSpectrum());
		currentMainFreq = hIndex * recorder.getFs() / recorder.getSampleSize();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		res = getResources();

		TabHost tabHost = getTabHost();
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {

			}
		});

		TabHost.TabSpec spec;
		TabSpec tabSpec = tabHost.newTabSpec("rpm");
		tabSpec.setIndicator("", getResources().getDrawable(R.drawable.tachometer));
		tabSpec.setContent(R.id.rpm_view);
		spec=tabSpec;
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("scientific");
		spec.setContent(R.id.scientific_view);
		spec.setIndicator("",
				getResources().getDrawable(R.drawable.musicequalizer));
		tabHost.addTab(spec);
		
		spec = tabHost.newTabSpec("settings");
		spec = spec.setIndicator("", getResources().getDrawable(R.drawable.settings));
		spec.setContent(R.id.settings_view);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#000000"));
			tabHost.getTabWidget().getChildAt(i).setPadding(0, 0, 0, 0);
		}

		// super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		//
		// // nb of threads jTransforms will use
		// ConcurrencyUtils.setNumberOfThreads(CoreCounter.count());
		//
		// this.recorder=new AudioRecorder(this);
		// this.processor=new SignaProcessor(SPECTROGRAM_SIZE);
	}

	public AudioRecorder getAudioRecorder() {
		return this.recorder;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		if(recorder!=null)recorder.close();
		super.onDestroy();
	}
}
