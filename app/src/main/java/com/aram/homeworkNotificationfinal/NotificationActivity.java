package com.aram.homeworkNotificationfinal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity {
	private NotificationManager mNotifyManager;
	private android.support.v7.app.NotificationCompat.Builder mBuilder;
	int id = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		assert fab != null;
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
	}

	public void showNotification(View view) {
		Intent resultIntent = new Intent(this, ShareActivity.class);

		PendingIntent resultPendingIntent =
				PendingIntent.getActivity(
						this,
						0,
						resultIntent,
						PendingIntent.FLAG_UPDATE_CURRENT
				);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.drawable.side_nav_bar)
						.setContentTitle("My notification")
						.setContentText("Hello World!")
				.setContentIntent(resultPendingIntent);

		// Sets an ID for the notification
		int mNotificationId = 100;

		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr =
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
      // Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}

	private class MyDownloader extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// Displays the progress bar for the first time.
			mBuilder.setProgress(100, 0, false);
			mNotifyManager.notify(id, mBuilder.build());
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// Update progress
			mBuilder.setProgress(100, values[0], false);
			mNotifyManager.notify(id, mBuilder.build());
			super.onProgressUpdate(values);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			int i;
			for (i = 0; i <= 100; i += 5) {
				// Sets the progress indicator completion percentage
				publishProgress(Math.min(i, 100));
				try {
					// Sleep for 5 seconds
					Thread.sleep(2 * 1000);
				} catch (InterruptedException e) {
					Log.d("TAG", "sleep failure");
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			mBuilder.setContentText("Download complete");
			// Removes the progress bar
			mBuilder.setProgress(0, 0, false);
			mNotifyManager.notify(id, mBuilder.build());
		}
	}
}
