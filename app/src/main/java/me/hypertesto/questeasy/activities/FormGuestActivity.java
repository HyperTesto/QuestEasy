package me.hypertesto.questeasy.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import me.hypertesto.questeasy.ui.DatePickerFragment;
import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.adapters.PlaceAutoCompleteAdapter;
import me.hypertesto.questeasy.ui.DelayAutoCompleteTextView;
import me.hypertesto.questeasy.ui.DocumentDataFragment;
import me.hypertesto.questeasy.ui.PersonalDataFragment;
import me.hypertesto.questeasy.utils.CitizenshipRequest;
import me.hypertesto.questeasy.utils.PlaceRequest;

public class FormGuestActivity extends AppCompatActivity {




	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;

	// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "QuestEasy";
	private Uri fileUri; // file url to store image/video


	//REQ_CODE for voice
	private final int REQ_CODE_SPEECH_INPUT = 100;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_guest);


		//dateFomatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);



		/*button_photo.setOnClickListener(new
												View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// capture picture
				captureImage();
			}
		});
		button_voice_form.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				promptSpeechInput();
			}
		});
		*/



	}


	//showing date
	public void showDatePickerDialog(View v){
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

/*
	private Bitmap getCircleBitmap(Bitmap bitmap) {
		final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(output);

		final int color = Color.RED;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawOval(rectF, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		bitmap.recycle();

		return output;
	}
	*/
	/*
 	 Launch the camera
 	*/
	/*
	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// start the image capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}
	*/
	/*
     * Display image from a path to ImageView
     */
	/*private void previewCapturedImage() {
		try {

			photo_guest1.setVisibility(View.VISIBLE);


			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 16;


			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath());
			Bitmap circle = getCircleBitmap(bitmap);
			photo_guest1.setImageBitmap(circle);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	*/

	/**
	 * Creating file uri to store image/video
	 */
	/*
	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}
	*/

	/*
	 * returning image / video
	 */

	/*
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.e(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		}
		else {
			return null;
		}

		return mediaFile;
	}
	*/
	//manage data voice insertion

	/**
	 * Showing google speech input dialog
	 * */

	/*
	private void promptSpeechInput(){
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ITALY);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
		try{
			startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a){
			Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
		}
	}

*/
	/**
	 * Receiving speech input
	 * */

	/*
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, data);

		switch(requestCode){

			case REQ_CODE_SPEECH_INPUT :
				if (resultCode == RESULT_OK && null!=data){
					ArrayList<String> result = data.getStringArrayListExtra
							(RecognizerIntent.EXTRA_RESULTS);
					ParsingAndAssignData(result);

				}
				break;

			case CAMERA_CAPTURE_IMAGE_REQUEST_CODE :
				if (resultCode == RESULT_OK){
					Toast.makeText(this, "Image saved to:\n" +
							fileUri.toString(), Toast.LENGTH_LONG).show();
					Log.e("SAVED",fileUri.toString());
					//previewCapturedImage();
				}else if (resultCode == RESULT_CANCELED){
					// user cancelled Image capture
					Toast.makeText(getApplicationContext(),
							"L'utente ha cancellato l'immagine", Toast.LENGTH_SHORT)
							.show();
				}else{
					Toast.makeText(getApplicationContext(),
							"Ooops si è verificato un errore", Toast.LENGTH_SHORT)
							.show();
				}

			break;
			default:break;
		}
	}

	private void ParsingAndAssignData(ArrayList<String>result){
		String [] data = result.get(0).split(" ");
		for (int i = 0; i < data.length; i++){
			String wordSupport = data[i].toUpperCase();
			System.out.println("***Value " + wordSupport);
			switch (wordSupport){
				//manage more than one name
				case "NOME" :
					guest_name.setText(data[i+1]);
					break;
				case "COGNOME" :
					guest_surname.setText(data[i+1]);
					break;
				case "CITTADINANZA" :
					guest_citizenship.setText(data[i+1]);
					break;
				case "SESSO" :
					String sex = data[i+1].toUpperCase();
					if (sex.equals("UOMO")){
						guest_sexMan.setChecked(true);
					}else if (sex.equals("DONNA")){
						guest_sexWoman.setChecked(true);
					}
					//guest_surname.setText(result.get(i+2).toString());
					break;
				case "NASCITA" :
					guestBirthPlace.setText(data[i+1]);
					break;
				case "CODICE" :
					guest_documentCode.setText(data[i+1]);
					break;
				case "NUMERO" :
					if (data[i+1].toUpperCase().equals("DOCUMENTO"))
						guest_documentNumber.setText(data[i+2]);
					break;
				case "RILASCIO" :
					guest_documentPlace.setText(data[i+1]);
					break;
				default : break;
			}


		}

	}

*/
	/**
	 * Here we store the file url as it will be null after returning from camera
	 * app
	 */

	/*
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on scren orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}
*/
	/*
	 * Here we restore the fileUri again
	 */

	/*
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.form_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}
*/

}
