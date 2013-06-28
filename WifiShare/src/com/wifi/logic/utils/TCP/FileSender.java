package com.wifi.logic.utils.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

import com.wifi.logic.utils.Constants;
import com.wifi.logic.utils.FileProcess;
import com.wifi.logic.utils.StringUtil;

public class FileSender   {

	private static String loggerTag ="FileSender";
	
	public static void sendFile(String IP,String fileName){
		
		int serverPort =Constants.TCPPort;
		Socket s = null;
		try {
			s = new Socket(IP, serverPort);
			DataInputStream input = new DataInputStream( s.getInputStream());
			DataOutputStream output = new DataOutputStream( s.getOutputStream()); 
			
			String actualFileName = StringUtil.getFileName(fileName);
			output.writeInt(actualFileName.length());
			Log.d(loggerTag, "sending file name");
			for(int i =0;i<actualFileName.length();i++)
			{
				output.writeChar(actualFileName.charAt(i));
			}
			
			File file = new File(fileName);
			Log.d(loggerTag, "file going to send"+fileName);
			
			output.writeLong(file.length() );
			
			output.write( FileProcess.getBytes( file ) );
			
			
			/*FileInputStream fileInputStream = new FileInputStream(file);
			 * byte[] buffer = new byte[1024];
			int number;

			
			while ((number = fileInputStream.read(buffer)) != -1) {
				output.write(buffer, 0, number);
			}
			output.write(buffer, 0, -1);
			*/
			System.out.println("Data recived at server"+input.readInt());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
