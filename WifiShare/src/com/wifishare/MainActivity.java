package com.wifishare;

import java.util.Iterator;
import java.util.List;

import ua.com.vassiliev.androidfilebrowser.FileBrowserActivity;

import com.wifi.logic.tasks.WifiStatusPoller;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView connectionStatusIndicator = null;
	private static Context context;
	private static MainActivity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this.getApplicationContext();
		connectionStatusIndicator = (TextView) this
				.findViewById(R.id.WifiConnectionStatusIndicator);
		activity = this;
		new WifiStatusPoller().execute(connectionStatusIndicator);
		
		ImageButton button = (ImageButton) this.findViewById(R.id.imageButton2);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new WifiStatusPoller().execute(connectionStatusIndicator);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static Context getContext() {
		return context;
	}
	
	/**
	 * can be used to get instance of main activity to make UI changes form async tasks
	 * @return
	 */
	public static MainActivity getInstance(){
		return activity;
	}

	public void setClientList(List<String> clients){
		
		LinearLayout layout = (LinearLayout) activity
				.findViewById(R.id.clientList);
		layout.removeAllViews();
		Iterator<String> itr = clients.iterator();
		while(itr.hasNext())
		{
			TextView client = new TextView(activity);
	        LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        client.setLayoutParams(lparams);
	        client.setTextAppearance(activity, android.R.attr.textAppearanceLarge);
	        client.setTextColor(Color.WHITE);
	        client.setTextSize(getResources().getDimension(R.dimen.clientListSize));
	        client.setText(itr.next());
	        client.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View clientButton) {
	                TextView textViewClientIP =  (TextView)clientButton;
	                String IP = textViewClientIP.getText().toString();
	                Toast.makeText(context, "Connecting to the IP "+IP ,
	        				Toast.LENGTH_SHORT).show();
	                
	                Log.d("MainActivity", "StartFileBrowser4File button pressed");
	    			Intent fileExploreIntent = new Intent(
	    					FileBrowserActivity.INTENT_ACTION_SELECT_FILE,
	        				null,
	        				context,
	        				FileBrowserActivity.class
	        				);
	        		startActivityForResult(fileExploreIntent,2);//2 is for select file option
	                
	            }
	        });
	        layout.addView(client);	
		}
		
	}
}
