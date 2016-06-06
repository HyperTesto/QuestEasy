package me.hypertesto.questeasy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import me.hypertesto.questeasy.R;

/**
 * An image button that play-stop the audio track declared in his res file
 * Created by hypertesto on 04/06/16.
 */
public class AudioTrackButton extends ImageView {

	protected MediaPlayer mediaPlayer;

	public AudioTrackButton(Context context, AttributeSet attrs){
		super(context, attrs);
		setImageResource(R.drawable.ic_play);

		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.AudioTrackButton,
				0, 0);
		try {

			mediaPlayer = MediaPlayer.create(context, a.getResourceId(R.styleable.AudioTrackButton_audioTrack,0));

			//we make sure to update the view when the track is finished
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					setImageResource(R.drawable.ic_play);
				}
			});

			this.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					//toggle execution and update the view
					if (mediaPlayer.isPlaying()) {
						System.out.println("STOPPING...");
						mediaPlayer.pause();
						mediaPlayer.seekTo(0);
						setImageResource(R.drawable.ic_play);
					} else {
						System.out.println("STARTING");
						mediaPlayer.start();
						setImageResource(R.drawable.ic_stop);
					}
				}
			});
		} finally {
			a.recycle();
		}

	}
}
