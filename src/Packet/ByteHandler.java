package Packet;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;

import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;

import Objects.DecryptedBytes;

public class ByteHandler {

	private static JBuffer buffer;
	private static int size = 0;
	private static int lengthHeader = 0;
	private static int length = 0;
	
	public static void initialize(PcapPacket p, ArrayList<Integer> listTcp, ArrayList<Integer> listEth, ArrayList<Integer> listIp4, int counter) {
		try {
			setBuffer(p);
			setSize(p.size());
			setHeaderLength(listEth.get(counter) + listIp4.get(counter) + listTcp.get(counter));
			setLength(getSize() - getHeaderLength());
		} catch (IndexOutOfBoundsException ioobe) {
			System.out.println("[ByteHandler] Out of bounds");
		}
	}
	
	public static void setBuffer(PcapPacket p) {
		buffer = p;
	}
	
	public static JBuffer getBuffer() {
		return buffer;
	}
	
	public static void setSize(int s) {
		size = s;
	}
	
	public static int getSize() {
		return size;
	}
	
	public static void setHeaderLength(int lh) {
		lengthHeader = lh;
	}
	
	public static int getHeaderLength() {
		return lengthHeader;
	}
	
	public static void setLength(int l) {
		length = l;
	}
	
	public static int getLength() {
		return length;
	}
	
	public static String getDestination() {
		
		String hexString = "";
		//System.out.printf("Destination: ");
		for (int i = 0; i < 6; i++) {
			if (buffer.getUByte(i) > 15) {
				//System.out.printf("%h ", buffer.getUByte(i));
				hexString = hexString + " " + Integer.toHexString(buffer.getUByte(i));
			} else {
				//System.out.printf("0%h ", buffer.getUByte(i));
				hexString = hexString + " 0" + Integer.toHexString(buffer.getUByte(i));
			}
		}
		return hexString;
	}
	
	public static String getSource() {
		
		String hexString = "";
		//System.out.printf("Source: ");
		for (int i = 6; i < 12; i++) {
			if (buffer.getUByte(i) > 15) {
				//System.out.printf("%h ", buffer.getUByte(i));
				hexString = hexString + " " + Integer.toHexString(buffer.getUByte(i));
				
			} else {
				//System.out.printf("0%h ", buffer.getUByte(i));
				hexString = hexString + " 0" + Integer.toHexString(buffer.getUByte(i));
			}
		}
		return hexString;
	}
	
	public static String getSourceIP() {
		
		String hexString = "";
		//System.out.printf("Source: ");
		for (int i = 26; i < 30; i++) {
			if (buffer.getUByte(i) > 15) {
				//System.out.printf("%h ", buffer.getUByte(i));
				hexString = hexString + " " + Integer.toHexString(buffer.getUByte(i));
				
			} else {
				//System.out.printf("0%h ", buffer.getUByte(i));
				hexString = hexString + " 0" + Integer.toHexString(buffer.getUByte(i));
			}
		}
		return hexString;
	}
	
	public static String getDestIP() {
		
		String hexString = "";
		//System.out.printf("Source: ");
		for (int i = 30; i < 34; i++) {
			if (buffer.getUByte(i) > 15) {
				//System.out.printf("%h ", buffer.getUByte(i));
				hexString = hexString + " " + Integer.toHexString(buffer.getUByte(i));
				
			} else {
				//System.out.printf("0%h ", buffer.getUByte(i));
				hexString = hexString + " 0" + Integer.toHexString(buffer.getUByte(i));
			}
		}
		return hexString;
	}
	
	public static String getAckNumber() {
		
		String hexString = "";
		for (int i = 42; i < 46; i++) {
			hexString = hexString + Integer.toHexString(buffer.getUByte(i));
		}
		return hexString;
	}

	public static String getSeqNumber() {
		
		String hexString = "";
		for (int i = 38; i < 42; i++) {
			hexString = hexString + Integer.toHexString(buffer.getUByte(i));
		}
		return hexString;
	}

	public static Long getWindowSize() {
		
		String hexString = "";
		for (int i = 48; i < 50; i++) {
			hexString = hexString + Integer.toHexString(buffer.getUByte(i));
		}
		return Long.parseLong(hexString, 16);
	}
	
	public static Long getMss() {
		
		String hexString = "";
		for (int i = 56; i <= 57; i++) {
			hexString = hexString + Integer.toHexString(buffer.getUByte(i));
		}
		return Long.parseLong(hexString, 16);
	}
	
	public static Long getTimestamp(String action) {
		
		int start = 0;
		int end = 0;
	
		switch (action) {
		
			case Constants.ACK:
				start = 58;
				end = 61;
				break;
			case Constants.FIN_ACK:
				start = 58;
				end = 61;
				break;
			case Constants.PSH_ACK:
				start = 58;
				end = 61;
				break;
			case Constants.SYN:
				start = 66;
				end = 69;
				break;
			case Constants.SYN_ACK:
				start = 62;
				end = 65;
				break;
			default:
				break;
		}
	
		String hexString = "";
		for (int i = start; i <= end; i++) {
			try {
				hexString = hexString + Integer.toHexString(buffer.getUByte(i));
			} catch (BufferUnderflowException bue) {
				//System.out.println("[ByteHandler] No timestamp for this packet " + bue);
				return 0L;
			}
		}
		return Long.parseLong(hexString, 16);
	}
	
	public static String httpCheck() {
		
		String hexString = "";
		for (int i = 54; i <= 56; i++) {
			try {
				hexString = hexString + Integer.toHexString(buffer.getUByte(i));
			} catch (BufferUnderflowException bue) {
				System.out.println(bue);
			}
		}
		if (hexString.equals("474554")) {
			return "GET";
		}
		
		for (int i = 13; i <= 14; i++) {
			try {
				hexString = hexString + Integer.toHexString(buffer.getUByte(i));
			} catch (BufferUnderflowException bue) {
				System.out.println(bue);
			}
		}
		if (hexString.equals("4f4b")) {
			return "OK";
		}
		
		return "";
	}
	
	//*************************SPDY***************************
	
	public static int getContentType() {
		return buffer.getUByte(54);
	}
	
	public static String getVersion() {
		String hexString = "";
		for (int i = 55; i <= 56; i++) {
			if (buffer.getUByte(i) > 15) {
				hexString = hexString + " " + Integer.toHexString(buffer.getUByte(i));
				
			} else {
				hexString = hexString + " 0" + Integer.toHexString(buffer.getUByte(i));
			}
		}
		return hexString;
	}

	public static String getSslLength() {
		
		String hexString = "";
		for (int i = 57; i <= 58; i++) {
			hexString = hexString + Integer.toHexString(buffer.getUByte(i));
		}
		return hexString;
	}
	
	public static String getStreamLength(DecryptedBytes db, int start, int end) {
		String hexString = "";
		for (int i = start; i <= end; i++) {
			hexString = hexString + db.getBytes().get(i);
		}
		return hexString;
	}
	
	public static String getSpdyIpSource() {
		
		String hexString = "";
		for (int i = 26; i <= 29; i++) {
			hexString = hexString + Integer.toHexString(buffer.getUByte(i));
		}
		return hexString;
	}
	
	public static String getSpdyIpDest() {
		
		String hexString = "";
		for (int i = 30; i <= 33; i++) {
			hexString = hexString + Integer.toHexString(buffer.getUByte(i));
		}
		return hexString;
	}
	
	public static String getQuicCidLength() {
		
		return Integer.toHexString(buffer.getUByte(42));
	}
	
	public static String getQuicCid(int lengthDecimal) {
		
		int end = 42 + lengthDecimal;
		String hexString = "";
		for (int i = 43; i <= end; i++) {
			hexString = hexString + Integer.toHexString(buffer.getUByte(i));
		}
		return hexString;
	}
	
	public static String getQuicSequence(int lengthFlags, int lengthSeq) {
		int start = 43 + lengthFlags;
		int end = start + lengthSeq;
		String hexString = "";
		for (int i = start; i < end; i++) {
			if (buffer.getUByte(i) > 15) {
				hexString = hexString + " " + Integer.toHexString(buffer.getUByte(i));
				
			} else {
				hexString = hexString + " 0" + Integer.toHexString(buffer.getUByte(i));
			}
		}
		return hexString;
	}
	
}
