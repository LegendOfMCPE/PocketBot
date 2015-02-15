package com.github.legendofmcpe.pocketbot;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.io.PrintStream;

public class Runner {
	private static class SystemLogger implements Logger {
		public void debug(String line) { System.out.println("debug: " + line); }
		public void error(String line) { System.out.println("error: " + line); }
		public void warning(String line) { System.out.println("warning: " + line); }
		public void notice(String line) { System.out.println("notice: " + line); }
		public void critical(String line) { System.out.println("critical: " + line); }
		public void cmd(String line) { System.out.println("cmd: " + line); }
		public void info(String line) { System.out.println("info: " + line); }
		public void trace(String line) { System.out.println("trace: " + line); }
		public void exception(Throwable t) { System.out.println("exception: " + t); t.printStackTrace(); }
		public PrintStream getPrinter() { return System.out; }
	}
	
	private static class SystemLang implements Lang {
		public String socket_connect_error() { return "socket_connect_error()"; }
		public String unknown_packet(Byte pid, ByteBuffer bb)	 { return "unknown_packet()"; }
		public String server_no_response() { return "server_no_response()"; }
		public String stopping() { return "stopping()"; }
		public String incorrect_raknet_protocol(byte expected) { return "incorrect_raknet_protocol()"; }
		public String connect_packet_error_abort() { return "connect_error_abort()"; }
	}
	
	public static void main(String[] args) {
		try {
			Logger logger = new SystemLogger();
			Lang lang = new SystemLang();
			logger.trace("main here.");
			InetSocketAddress address = new InetSocketAddress("10.0.1.14", 19132);
			PocketBot bot = new PocketBot("test", address, logger, lang, "otto", 0x00050eca31cf148bL);
			bot.run();
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}
}
