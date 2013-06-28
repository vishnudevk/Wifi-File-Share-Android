package com.wifi.logic.utils.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import android.util.Log;

import com.receivers.WifiReceiver;
import com.wifi.logic.utils.Constants;
import com.wifi.logic.utils.WifiUtils;
import com.wifi.logic.utils.TCP.FileReceiver;

public class ClientListner implements Runnable {
	
	private static String loggerTag="ClientListner";
	@Override
	public void run() {

		DatagramSocket socket;
		int clientListnport = Constants.UDPPort;

		try {
			// Keep a socket open to listen to all the UDP trafic that is
			// destined for this port
			socket = new DatagramSocket(clientListnport,
					(Inet4Address)InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			socket.setSoTimeout(10000);

			while (WifiReceiver.isWifi) {
				System.out.println(">>>Ready to receive broadcast packets!");

				// Receive a packet
				byte[] recvBuf = new byte[15000];
				DatagramPacket packet = new DatagramPacket(recvBuf,
						recvBuf.length);
				try {
					 socket.receive(packet);
					
					// Packet received
					System.out.println(">>>Discovery packet received from: "
							+ packet.getAddress().getHostAddress());
					// See if the packet holds the right command (message)
					String message = new String(packet.getData()).trim();
					if (message.equals(Constants.serverDiscoverString)) {
						byte[] sendData = Constants.clientReplyString.getBytes();
						// Send a response
						
						if(!WifiUtils.getipAddress().equalsIgnoreCase(packet.getAddress().getHostAddress().toString())){
							DatagramPacket sendPacket = new DatagramPacket(
									sendData, sendData.length, packet.getAddress(),
									packet.getPort());
							socket.send(sendPacket);

							System.out.println(">>>Sent packet to: "
									+ sendPacket.getAddress().getHostAddress());
	
						}
					}
					else if(message.equals(Constants.serverFileSending))
					{
						System.out.println("sending reply to recive handshake");
						byte[] sendData = Constants.clientFileReceive.getBytes();
						DatagramPacket sendPacket = new DatagramPacket(
								sendData, sendData.length, packet.getAddress(),
								packet.getPort());
						socket.send(sendPacket);
						Log.d(loggerTag, "handshaking is done");
						FileReceiver.waitConnection();
						
					}
				} catch (SocketTimeoutException e) {
					// no need of doing anything
				}
			}
			System.out.println("CLientListener : Listener is stoped");
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
