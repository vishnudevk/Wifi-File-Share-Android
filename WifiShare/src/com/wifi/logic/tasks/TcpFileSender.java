package com.wifi.logic.tasks;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

import com.wifi.logic.utils.Constants;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Used to send file through TCP.
 * param[0] : IP address of target device
 * param[1] : file to send
 * @author vishnudev
 *
 */
public class TcpFileSender extends AsyncTask<String, Void, Void>{

	private static String loggerTag="TcpFileSender";
	@Override
	protected Void doInBackground(String... params) {

		 String IP =params[0];
		 String fileName= params[1];
		 
		 ///First send a UDP packet saying we are going to send a file.
		if(sendUDPHandshake(IP).equalsIgnoreCase("SUCCESS")){
			System.out.println("file is going to send");
		}
		 
		return null;
	}
	
	
	private String sendUDPHandshake(String Ip) {

		String returnMsg ="falure in handshake";
		DatagramSocket socket;
		byte[] sendData = Constants.serverFileSending.getBytes();

		try {
			socket = new DatagramSocket();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(Ip), Constants.UDPPort);
			socket.send(sendPacket);
			Log.d(loggerTag,">>> Handshake packet sent to:"+Ip );
			//waiting for the reply from client
			byte[] recvBuf = new byte[15000];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			socket.setSoTimeout(3000);
			try
			{
				socket.receive(receivePacket);
				String message = new String(receivePacket.getData()).trim();
				if (message.equals(Constants.clientFileReceive)) {
					returnMsg="SUCCESS";
					Log.d(loggerTag, "Handshake done with the client");
				}
			}
			catch (SocketTimeoutException e) 
			{
				returnMsg="target device is not responding";
			}
		} catch (Exception e) {
		}
		return returnMsg;
	}
	
}
