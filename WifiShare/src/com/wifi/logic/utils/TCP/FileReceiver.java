package com.wifi.logic.utils.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import android.os.Environment;
import android.util.Log;

import com.wifi.logic.utils.Constants;
import com.wifi.logic.utils.FileProcess;

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
		catch(Exception e) {Log.d(loggerTag, "TCP time out. No server connection"+e.toString());} 
	}
	
	public FileReceiver(Socket aClientSocket) {
		try {
			clientSocket=aClientSocket;
			this.reciveFile();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void reciveFile() throws EOFException, IOException {
		FileOutputStream fos = null;
		try{
				input = new DataInputStream( clientSocket.getInputStream());
				output =new DataOutputStream( clientSocket.getOutputStream()); 
				
				int fileLength = input.readInt();
				System.out.println("test integer recived"+fileLength);
				String actualFileName = "";
				for(int i=0;i<fileLength;i++)
				{
					actualFileName =actualFileName+input.readChar(); 
				}
				Log.d(loggerTag,"file is going to be recieved"+actualFileName  );
				
				File dir =getFile("wifishare");
						File file =new File(dir.getAbsoluteFile()+"/"+actualFileName); 
						Log.d(loggerTag,"file is going to be saved at"+file.getAbsolutePath()  );
						long temp = input.readLong();
						byte[] rFile = new byte[ (int) temp ];
						input.readFully(rFile); 
						//input.read( rFile );
						 FileProcess.makeFile(file.getAbsolutePath(), rFile);
				
				
				Log.d(loggerTag, "file success fully recived");
				} 
			catch (Exception e) {
				System.out.println(e);
			}
			finally { 
			  try { 
				  	fos.close();
				  	output.close();
		            input.close();
				  clientSocket.close();
			  }
			  catch (IOException e){/*close failed*/}
			}
	}
	
	
	public File getFile(String fileName) {
	    // Get the directory for the user's public pictures directory. 
	    File file = new File(Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_DOWNLOADS), fileName);
	    if (!file.mkdirs()) {
	        Log.e(loggerTag, "Directory not created");
	    }
	    return file;
	}
}
