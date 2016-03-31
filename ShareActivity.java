package com.aram.homeworkNotificationfinal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ShareActivity extends AppCompatActivity {
	SwipeRefreshLayout swipeRefreshLayout;
	TextView text;
	int[] colors = {Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED, Color.DKGRAY};


	ListView list;
	String[] web = {
			"Google Plus",
			"Twitter",
			"Windows",
			"Bing",
			"Itunes",
			"Wordpress",
			"Drupal"
	} ;
	Integer[] imageId = {
			R.drawable.image1,
			R.drawable.image2,
			R.drawable.image3,
			R.drawable.image4,
			R.drawable.image5,
			R.drawable.image6,
			R.drawable.image7

	};




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);


		CustomList adapter = new
				CustomList(ShareActivity.this, web, imageId);
		list=(ListView)findViewById(R.id.listview);
		assert list != null;
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Toast.makeText(ShareActivity.this, "You Clicked at " + web[+position],
						Toast.LENGTH_SHORT).show();

			}
		});

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
		text = (TextView) findViewById(R.id.swipedText);

		swipeRefreshLayout.setOnRefreshListener(
				new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {

						// This method performs the actual data-refresh operation.
						// The method calls setRefreshing(false) when it's finished.
						updateOperation();
					}
				}
		);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.swipe, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			// Check if user triggered a refresh:
			case R.id.menu_refresh:

				// Signal SwipeRefreshLayout to start the progress indicator
				swipeRefreshLayout.setRefreshing(true);

				// Start the refresh background task.
				// This method calls setRefreshing(false) when it's finished.
				updateOperation();

				return true;
		}

		// User didn't trigger a refresh, let the superclass handle this action
		return super.onOptionsItemSelected(item);

	}


	private void updateOperation(){
		new Thread(new Runnable() {
			@Override
			synchronized public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Random random = new Random();
						int hint = random.nextInt(5);
						if (text != null) {
							if (text.getVisibility() == View.VISIBLE) {

								text.setVisibility(View.GONE);

							} else {
								text.setVisibility(View.VISIBLE);
							}
						}
						if (list != null) {
							if (list.getVisibility() == View.VISIBLE) {
								list.setBackgroundColor(colors[hint]);

								list.setVisibility(View.GONE);

							} else {
								list.setVisibility(View.VISIBLE);
							}
						}
					}
				});


				try {
					wait(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(false);
					}
				});
			}
		}).start();

	}
}
