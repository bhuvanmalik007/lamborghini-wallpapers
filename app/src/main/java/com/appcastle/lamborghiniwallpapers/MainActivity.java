package com.appcastle.lamborghiniwallpapers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.LightingColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	Random randomGenerator = new Random();

	
	public static void addImageToGallery(final String filePath, final Context context) {

	    ContentValues values = new ContentValues();

	    values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
	    values.put(Images.Media.MIME_TYPE, "image/jpeg");
	    values.put(MediaStore.MediaColumns.DATA, filePath);

	    context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
	}
	
	Button n, p, s;
	ImageView iv;
	int c = 0;
	Integer[] ar = { R.drawable.ao, R.drawable.aw, R.drawable.gh,
			R.drawable.mhgx, R.drawable.jhgf , R.drawable.oi , R.drawable.po , R.drawable.fd , R.drawable.df , R.drawable.bn };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		

		iv = (ImageView) findViewById(R.id.iv);
		n = (Button) findViewById(R.id.n);
		p = (Button) findViewById(R.id.p);
		s = (Button) findViewById(R.id.s);

		p.setOnClickListener(this);
		s.setOnClickListener(this);
		n.setOnClickListener(this);
		iv.setImageResource(ar[c]);
		iv.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				
				OutputStream output;
	 
				// Retrieve the image from the res folder
				iv.setDrawingCacheEnabled(true);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ar[c]);
		// Find the SD Card path
				File filepath = Environment.getExternalStorageDirectory();
	 
				// Create a new folder in SD Card
				File dir = new File(filepath.getAbsolutePath()
						+ "/Pictures/Lamborghini/");
				dir.mkdirs();  
	 
				// Create a name for the saved image
				File file = new File(dir, randomGenerator.nextInt(10000)+".jpg");
	 
				// Show a toast message on successful save
				Toast.makeText(MainActivity.this, "Image Saved to Gallery",
						Toast.LENGTH_SHORT).show();
				try {
	 
					output = new FileOutputStream(file);
	 
					// Compress into png format image from 0% - 100%
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
					output.flush();
					output.close();
					addImageToGallery(file.getAbsolutePath(), MainActivity.this);
				}
	 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		});

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {

		case R.id.n:
			if (c == 9)
				c = -1;
			iv.setImageResource(ar[++c]);
			break;

		case R.id.p:
			if (c == 0)
				c = 10;
			iv.setImageResource(ar[--c]);
			break;
		case R.id.s:
			WallpaperManager wm = WallpaperManager
					.getInstance(getApplicationContext());
			try {
				Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT)
						.show();
				wm.setResource(ar[c]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:

			Intent intent = new Intent("android.intent.action.MAIN.save");
			startActivity(intent);
			break;

		case R.id.item2:
			Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
			Intent i = new Intent("android.intent.action.about");
			startActivity(i);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void saveBitmap(Bitmap bmp) throws IOException {
		String file_path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/lambo";
		File dir = new File(file_path);
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(dir, "myImage.png");
		FileOutputStream fOut = new FileOutputStream(file);

		bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
		fOut.flush();
		fOut.close();
	}
}

