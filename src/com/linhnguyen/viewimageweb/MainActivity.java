package com.linhnguyen.viewimageweb;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	String url = "http://www.androidbegin.com";
	ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button logobutton = (Button) findViewById(R.id.logobutton);
		// Capture button click
		logobutton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// Execute Logo AsyncTask
				new Logo().execute();
			}
		});
	}

	// Logo AsyncTask
	private class Logo extends AsyncTask<Void, Void, Void> {
		Bitmap bitmap;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(MainActivity.this);
			mProgressDialog.setTitle("Android Basic JSoup Tutorial");
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				// Connect to the web site
				Document document = Jsoup.connect(url).get();
				// Using Elements to get the class data
				Elements img = document
						.select("a[class=brand brand-image] img[src]");
				// Locate the src attribute
				String imgSrc = img.attr("src");
				// Download image from URL
				InputStream input = new java.net.URL(imgSrc).openStream();
				// Decode Bitmap
				bitmap = BitmapFactory.decodeStream(input);

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// Set downloaded image into ImageView
			ImageView logoimg = (ImageView) findViewById(R.id.logo);
			logoimg.setImageBitmap(bitmap);
			mProgressDialog.dismiss();
		}
	}

}
