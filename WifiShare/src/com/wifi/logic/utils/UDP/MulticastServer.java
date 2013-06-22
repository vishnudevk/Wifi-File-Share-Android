package com.wifi.logic.utils.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import com.wifi.logic.utils.Constants;

/**
 * 
 * @author vishnudev this method sends a multicast packet to client listening
 *         port
 */
public class MulticastServer {
	/**
	 * This method returns the list of other devices in the same LAN connection 
	 * @return
	 */
	public static List<String> getAvaialableClients() {	
		return sendMulticastPacket();
	}
	/**
	 * This method sends a multicast packet to client listening
	 *   port.
	 * @return 
	 */
	private static List<String> sendMulticastPacket(){
		
		DatagramSocket socket = null ;
		List<String> clientList=new ArrayList<String>();
		int clientPort =Constants.clientListnPort;
		// Find the server using UDP broadcast
		try {
		  //Open a random port to send the package
		  socket = new DatagramSocket();
		  socket.setBroadcast(true);

		  byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();

		  //Try the 255.255.255.255 first
		  try {
		    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), clientPort);
		    socket.send(sendPacket);
		    System.out.println(">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
		  } catch (Exception e) {
		  }
/*
		  // Broadcast the message over all the network interfaces
		  Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		  while (interfaces.hasMoreElements()) {
		    NetworkInterface networkInterface = interfaces.nextElement();

		    if (networkInterface.isLoopback() || !networkInterface.isUp()) {
		      continue; // Don't want to broadcast to the loopback interface
		    }

		    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
		      InetAddress broadcast = interfaceAddress.getBroadcast();
		      if (broadcast == null) {
		        continue;
		      }

		      // Send the broadcast package!
		      try {
		        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
		        socket.send(sendPacket);
		      } catch (Exception e) {
		      }

		      System.out.println(">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
		    }
		  }

		  System.out.println(">>> Done looping over all network interfaces. Now waiting for a reply!");
*/
		  //Wait for a response
		  byte[] recvBuf = new byte[15000];
		  DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
		  socket.setSoTimeout(3000);
		  try
		  {
			  socket.receive(receivePacket);
	
			  //We have a response
			  System.out.println(">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
	
			  //Check if the message is correct
			  String message = new String(receivePacket.getData()).trim();
			  if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
			    //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
				  System.out.println("cliet address"+receivePacket.getAddress());
				  clientList.add(receivePacket.getAddress().toString());
			  }
		  }
		  catch (SocketTimeoutException e) 
		  {
			// do nothing
		  }
		} catch (IOException ex) 
		{
			System.err.println(ex);
		}
		finally
		{
			if(socket !=null && !socket.isClosed()){socket.close();}
		}
		return clientList;
	}

}
