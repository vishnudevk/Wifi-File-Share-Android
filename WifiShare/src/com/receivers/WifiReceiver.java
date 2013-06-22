package com.receivers;

import com.wifi.logic.utils.UDP.ClientListner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * this class created is a broadcastreceiver.
 * it is triggered when ever there is a change in network state.
 * when it is triggered it will start the listener.
 * @author vishnudev
 *
 */
public class WifiReceiver extends BroadcastReceiver {
	
		private static Thread thread = null ;
		public static boolean isWifi= false ;
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			ConnectivityManager conMan = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = conMan.getActiveNetworkInfo();
			if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				System.out.println("WifiReceiver : Have Wifi Connection");
				System.out.println("WifiReceiver : going to start listner");
				runListener();
			} else {
				System.out.println("WifiReceiver : Don't have Wifi Connection");
				System.out.println("WifiReceiver : going to stop listener");
				isWifi=false;
			}
		}
		public static void runListener(){
			isWifi=true;
			if(thread==null)thread = new Thread(new ClientListner());
			if(!thread.isAlive()) thread.start(); //start the listener
		}
	}