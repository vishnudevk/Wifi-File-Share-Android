package com.wifi.logic.tasks;

import java.util.ArrayList;
import java.util.List;

import com.receivers.WifiReceiver;
import com.wifi.logic.utils.WifiUtils;
import com.wifi.logic.utils.UDP.MulticastServer;
import com.wifishare.MainActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

/*
 * This class check user is connected to wifi or not and update the textview provided
 * to execute the task user should pass textview to be updated and the context
 */
public class WifiStatusPoller extends AsyncTask<Object, Integer , List<String>> {
	
	private static Thread listenerThread;
	public static Thread getListenerThread() {
		return listenerThread;
	}

	private String wifiStatus = "";
	TextView connectionStatusIndicator= null;
	
	@Override
	protected List<String> doInBackground(Object... objects) {
		System.out.println("WifiStatusPoller doInBackground start");
		connectionStatusIndicator = (TextView) objects[0];
		Context context = MainActivity.getContext(); 
		List<String> clientList = new  ArrayList<String>();
		if(WifiUtils.isWifiConnected())
		{ 
			//connectionStatusIndicator.setText(context.getString(com.wifishare.R.string.wifi_status_connected));
			WifiReceiver.runListener();
			wifiStatus = context.getString(com.wifishare.R.string.wifi_status_connected) ;
			try {
			//	clientList = new Multicast().execute("").get();
				clientList = MulticastServer.getAvaialableClients();
			} catch (Exception e) {System.err.println(e);}
		}
		else
		{
			//connectionStatusIndicator.setText(context.getString(com.wifishare.R.string.wifi_status_not_connected));
			wifiStatus = context.getString(com.wifishare.R.string.wifi_status_not_connected) ;
		}
		return clientList;
		
	}
	
	 @Override
	    protected void onPostExecute(List<String> result)
	{
		 connectionStatusIndicator.setText(wifiStatus);
	}

}
