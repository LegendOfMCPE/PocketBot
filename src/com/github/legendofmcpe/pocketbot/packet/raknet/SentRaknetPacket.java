package com.github.legendofmcpe.pocketbot.packet.raknet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.github.legendofmcpe.pocketbot.utils.BinaryWriter;
import com.github.legendofmcpe.pocketbot.utils.Constants;
import com.github.legendofmcpe.pocketbot.utils.Gettable;

public abstract class SentRaknetPacket implements Constants{
	private ByteBuffer bb;
	protected SentRaknetPacket(int malloc){
		bb = ByteBuffer.allocate(malloc).order(ByteOrder.LITTLE_ENDIAN);
	}
	protected SentRaknetPacket(Gettable<Integer> malloc){
		this(malloc.run());
	}
	public abstract byte getPid();
	public void sendTo(DatagramSocket sk, SocketAddress addr) throws IOException{
		bb.put(getPid());
		encode();
		byte[] buffer = bb.array();
		sk.send(new DatagramPacket(buffer, buffer.length, addr));
	}
	public void putTriad(int i){
		BinaryWriter bw = new BinaryWriter(3, true);
		bw.putTriad(i);
		bb().put(bw.getBuffer());
	}
	protected abstract void encode();
	protected final ByteBuffer bb(){
		return bb;
	}
}
