package com.wifi.logic.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.wifishare.MainActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * 
 * @author Vishnudev K
 *
 *common utilities used for wifi related stuffs
 */
public class WifiUtils {

	
	public static boolean isWifiConnected() {
		Context context = MainActivity.getContext();
		
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
		if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting() ) {
		    	return true ;
		    }
//		return getWifiApState(context);
		return false;

	}
	
	
	/**
	 * These two methods are not working but kept for referance
	 * 
	 * app still doesnt work with wifi hotspot	 
	 * */
/*	//method returns if hotspot is ative
	public static boolean isWifiApEnabled(Context context){ 
        boolean isWifiAPEnabled = false;        
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Method[] wmMethods = wifi.getClass().getDeclaredMethods();
        for(Method method: wmMethods){
            if(method.getName().equals("isWifiApEnabled")) {  
                try {
                  isWifiAPEnabled = (Boolean) method.invoke(wifi);
                } catch (IllegalArgumentException e) {
                	System.out.println("1");
                } catch (IllegalAccessException e) {
                	System.out.println("2");
                } catch (InvocationTargetException e) {
                	System.out.println("3");
                	isWifiAPEnabled=true;
                }
            }
        }
        System.out.println("wifi ap enabled "+isWifiAPEnabled);
        return isWifiAPEnabled;
    }
	
	
	public static boolean getWifiApState(Context context) {
		try {
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			Method method = wifi.getClass().getMethod("getWifiApState");
			int i= (Integer) method.invoke(wifi);
			return true;
		} catch (Exception e) {
			return false;
		}
	}*/

	public static String getipAddress() { 
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address ) {
                        String ipaddress=inetAddress.getHostAddress().toString();
                        Log.e("ip address",""+ipaddress);
                        return ipaddress;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Socket exception in GetIP Address of Utilities", ex.toString());
        }
        return null; 
}
}
