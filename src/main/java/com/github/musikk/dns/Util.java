package com.github.musikk.dns;

import java.nio.ByteBuffer;

public class Util {

	public static void writeCharacterString(ByteBuffer buf, byte[] bytes) {
		if (bytes.length > 254) {
			throw new IllegalArgumentException("The length of a character string must be between 0 and 254 octets.");
		}
		buf.put((byte) bytes.length);
		buf.put(bytes);
	}

	public static byte[] parseCharacterString(ByteBuffer buf) {
		int length = buf.get() & 0xFF;
		byte[] bytes = new byte[length];

		buf.get(bytes);

		return bytes;
	}

}
