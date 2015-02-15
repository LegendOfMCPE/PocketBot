package com.github.legendofmcpe.pocketbot.packet.raknet;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import com.github.legendofmcpe.pocketbot.PocketBot;
import com.github.legendofmcpe.pocketbot.utils.Constants;

public abstract class ReceivedRaknetPacket implements Constants{
	private ByteBuffer bb;

	public void decode(PocketBot bot, ByteBuffer bb){
		this.bb = bb;
		try{
			decode(bot);
		}
		catch(BufferUnderflowException e){
			e.printStackTrace(bot.getLogger().getPrinter());
		}
	}
	protected abstract void decode(PocketBot bot);
	protected final ByteBuffer bb(){
		return bb;
	}

	public static void registerTypes(RaknetPacketParser parser){
		parser.registerPacketType(OPEN_CONNECTION_REPLY_1, OpenConnectionReply1.class);
		parser.registerPacketType(OPEN_CONNECTION_REPLY_2, OpenConnectionReply2.class);
		parser.registerPacketType(INCOMPATIBLE_PROTOCOL_VERSION, IncompatibleProtocolVersion.class);
		for(int i = 0x80; i <= 0x8F; i++) {
			parser.registerPacketType((byte)i, ReceivedCustomPacket.class);
		}
	}
	protected int getTriad(){
		// in buffer: aa bb cc
		// wanted: cc bb aa
		int t = bb.get(); // aa
		int f = Short.reverseBytes(bb.getShort()) << 010; // cc bb 00
		return t + f; // cc bb (aa + 00 = aa)
	}
}
