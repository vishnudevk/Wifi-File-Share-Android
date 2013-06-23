package com.wifi.logic.tasks;

import java.util.List;

import com.wifi.logic.utils.UDP.MulticastServer;

import android.os.AsyncTask;

/**
 * 
 * @author vishnudev
 * this method sends a multicast packet to client listning port
 */
public class Multicast extends AsyncTask<String, Integer , List<String> >{

	//TODO its not used to be removed
	@Override
	protected List<String> doInBackground(String... params) {
		return MulticastServer.getAvaialableClients();
	}

}

