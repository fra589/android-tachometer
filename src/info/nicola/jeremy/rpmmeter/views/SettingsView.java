package info.nicola.jeremy.rpmmeter.views;

import com.example.android_tachometer.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class SettingsView extends View{

	private Bitmap b;
	public SettingsView(Context context) {
		super(context);
		b=BitmapFactory.decodeResource(getResources(), R.drawable.musicequalizer);
	}

	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);

	}
}
