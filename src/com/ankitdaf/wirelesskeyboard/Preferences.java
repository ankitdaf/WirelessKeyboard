package com.ankitdaf.wirelesskeyboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {

	private Preference ip;
	private static final String IP_ADDRESS_SET = "com.ankitdaf.wirelesskeyboard.ip";
	private static final String IP_ADDRESS_PREF = "ip_address_pref";
	private static String summary = new String();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerReceiver(ipreceiver, new IntentFilter(IP_ADDRESS_SET));
		addPreferencesFromResource(R.layout.prefs);
		ip = findPreference("ip_address");
		if (!summary.equals("")) {
			ip.setSummary(summary);
		}

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (!summary.equals("")) {
			ip.setSummary(summary);
		}
	}

	public void commitippref() {
		SharedPreferences prefs = getSharedPreferences("wirelessprefs",MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor prefsEditor = prefs.edit();
		prefsEditor.putString(IP_ADDRESS_PREF, "");
		prefsEditor.commit();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(ipreceiver);
	}

	// @Override
	public void onSharedPreferenceChanged(SharedPreferences pref, String value) {
		// TODO Auto-generated method stub
	}

	private BroadcastReceiver ipreceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(IP_ADDRESS_SET)) {
				String data = intent.getExtras().getString(Wifi.IP_ADDRESS);
				summary = data;
				ip.setSummary(summary);
			}
			

		}
	};
}
