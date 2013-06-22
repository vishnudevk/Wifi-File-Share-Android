package com.wifishare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.wifi.logic.tasks.WifiStatusPoller;
import com.wifi.logic.utils.UDP.ClientListner;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView connectionStatusIndicator = null;
	private static Context context;
	private static List<String> availableClents;
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
				System.out.println("mainActiviy : button clicked");
				try {
					availableClents = new WifiStatusPoller().execute(connectionStatusIndicator).get();
					setClientList(availableClents, activity);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
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

	private static void setClientList(List<String> clients,MainActivity activity){
		
		LinearLayout layout = (LinearLayout) activity
				.findViewById(R.id.clientList);
		layout.removeAllViews();
		Iterator<String> itr = clients.iterator();
		while(itr.hasNext())
		{
			TextView titleView = new TextView(activity);
	        LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        titleView.setLayoutParams(lparams);
	        titleView.setTextAppearance(activity, android.R.attr.textAppearanceLarge);
	        titleView.setText(itr.next());
	        layout.addView(titleView);	
		}
		
	}
}
