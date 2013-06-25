package com.wifi.logic.utils.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import android.util.Log;

import com.wifi.logic.utils.Constants;

/**
 * This is a util class which has methods for receiving files.
 * @author vishnudev
 *
 */
public class FileReceiver{

	private static String loggerTag ="FileReciver";
	DataInputStream input; 
	DataOutputStream output; 
	Socket clientSocket; 
	
	public static void waitConnection(){
		try{ 
			int serverPort = Constants.TCPPort; 
			ServerSocket listenSocket = new ServerSocket(serverPort); 
			System.out.println("TCP server start listening... ... ...");
			Log.d(loggerTag, "TCP Server listening");
			listenSocket.setSoTimeout(3000);
			///while(true) { 
				Socket clientSocket = listenSocket.accept(); 
				FileReceiver c = new FileReceiver(clientSocket); 
			//} 
			} 
		catch(Exception e) {/*DO nothing*/} 
	}
	
	public FileReceiver(Socket aClientSocket) {
		try {
			this.reciveFile();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void reciveFile() throws EOFException, IOException {
			try{
				input = new DataInputStream( clientSocket.getInputStream());
				output =new DataOutputStream( clientSocket.getOutputStream()); 
				
				int a= input.readInt();
				System.out.println(a);
				}  
			finally { 
			  try { 
				  clientSocket.close();
			  }
			  catch (IOException e){/*close failed*/}
			}
	}
}
