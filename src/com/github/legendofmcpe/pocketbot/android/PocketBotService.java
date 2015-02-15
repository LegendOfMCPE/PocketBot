package com.github.legendofmcpe.pocketbot.android;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.github.legendofmcpe.pocketbot.PocketBot;

public class PocketBotService extends Service{
	public final static String MY_NAME = "com.github.legendofmcpe.pocketbot." +
			"android.PocketBotService";
	public final static String BOTS_INTENT_KEY = MY_NAME.concat(".bots");

	private PocketBotServiceBinder binder = new PocketBotServiceBinder(this);
	private Map<String, PocketBot> bots = new ArrayMap<String, PocketBot>();
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		for(String botData: intent.getStringArrayListExtra(BOTS_INTENT_KEY)){
			// for each list entry: server_name,ip:port,username
			String[] tokens = botData.split(",");
			String name = tokens[0];
			String[] addressString = tokens[1].split(":");
			String username = tokens[2];
			try{
				InetAddress address = InetAddress.getByName(addressString[0]);
				short port = Short.parseShort(addressString[1]);
				InetSocketAddress fullAddress = new InetSocketAddress(address, port);
				PocketBot bot = new PocketBot(name, fullAddress,
						new Console(this), new MLang(this),
						username, new Random().nextLong());
				new Thread(bot).start();
				bots.put(name, bot);
			}
			catch(UnknownHostException e){
				Log.e(MY_NAME, "Invalid host passeed to " +
						"PocketBotService: ".concat(addressString[0]));
			}
		}
		return START_NOT_STICKY;
	}
	public PocketBot getBot(String name){
		return bots.get(name);
	}
	@Override
	public IBinder onBind(Intent intent){
		return binder;
	}
}
