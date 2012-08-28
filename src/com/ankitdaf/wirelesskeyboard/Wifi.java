package com.ankitdaf.wirelesskeyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class Wifi extends Service {
	
    // default ip
    public static String SERVERIP = "10.0.2.15";

    public static final String WIRELESS_DATA_RECEIVED = "com.ankitdaf.wirelesskeyboard.wireless";
    public static final String WIRELESS_TEXT = "WIRELESS_TEXT";
    public static final String IP_ADDRESS_SET = "com.ankitdaf.wirelesskeyboard.ip";
    public static final String IP_ADDRESS = "IP_ADDRESS";
    private static final String IP_ADDRESS_PREF = "ip_address_pref";
    // designate a port
    public static final int SERVERPORT = 26015;
    private ServerSocket serverSocket;

    public class ServerThread implements Runnable {

        public void run() {
            try {
                if (SERVERIP != null) {
                	broadcastIPAddress(SERVERIP);
                    serverSocket = new ServerSocket(SERVERPORT);
                    while (true) {
                        // listen for incoming clients
                    	Log.d("WifiServer","waiting");
                        Socket client = serverSocket.accept();
                        Log.d("WifiServer","Accepted");
                        try {
                        	BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            String line = null;
                            while ((line = in.readLine()) != null) {
                            	handleWirelessData(line);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                	Log.d("WifiServer","No internet");
                }
            } catch (Exception e) {
            	Log.d("WifiServer","Error");
                e.printStackTrace();
            }
        }
    }

    // gets the ip address of your phone's network
    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) { return inetAddress.getHostAddress().toString(); }
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
    }
    
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
        SERVERIP = getLocalIpAddress();
        Thread fst = new Thread(new ServerThread());
        fst.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
        try {
            // make sure you close the socket upon exiting
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	public void handleWirelessData(String data)
	{
		Intent wintent = new Intent();
		wintent.setAction(WIRELESS_DATA_RECEIVED);
		wintent.putExtra(WIRELESS_TEXT, data);
		sendBroadcast(wintent);
	}
	
	public void broadcastIPAddress(String ip)
	{
		Intent wintent = new Intent();
		wintent.setAction(IP_ADDRESS_SET);
		wintent.putExtra(IP_ADDRESS, ip);
		sendBroadcast(wintent);
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public void commitippref()
	{
		SharedPreferences prefs = getSharedPreferences("wirelessprefs", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = prefs.edit();
		prefsEditor.putString(IP_ADDRESS_PREF, SERVERIP);
		prefsEditor.commit();
	}
}
