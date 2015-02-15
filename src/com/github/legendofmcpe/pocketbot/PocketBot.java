package com.github.legendofmcpe.pocketbot;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.github.legendofmcpe.pocketbot.packet.NetworkManager;

public class PocketBot implements Runnable {
	private InetSocketAddress address;
	private NetworkManager networkMgr;
	private Logger logger;
	private Lang lang;
	private List<String> msgQueue = new ArrayList<String>();
	private boolean running = false;
	private String username;
	private long clientId;
	private short mtu = Short.MAX_VALUE;

	public PocketBot(String name, InetSocketAddress address, Logger logger, Lang lang, String username, long clientId){
		this.address = address;
		this.logger = logger;
		this.lang = lang;
		this.username = username;
		this.clientId = clientId;
		networkMgr = new NetworkManager(this);
	}

	@Override
	public void run(){
		running = true;
		networkMgr.start();
	}
	public void end(){
		running = false;
		logger.info(lang.stopping());
	}

	public InetSocketAddress getAddress(){
		return address;
	}
	public Logger getLogger(){
		return logger;
	}
	public Lang getLang(){
		return lang;
	}
	public boolean isRunning(){
		return running;
	}
	public String getUsername(){
		return username;
	}
	public long getClientId(){
		return clientId;
	}
	public short getMtu(){
		return mtu;
	}
	public void setMtu(short mtu){
		this.mtu = mtu;
	}
	public NetworkManager getNetworkMgr(){
		return networkMgr;
	}

	public void queueMessage(String str){
		if(str.length() > 0xFFFF){
			throw new StringIndexOutOfBoundsException("The string is too long (more than 65535 bytes)!");
		}
		msgQueue.add(str);
	}
}
