package com.github.musikk.dns;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Util {

	/**
	 * Writes a character string into the buffer. The maximum length of the
	 * character string is 254 bytes.
	 *
	 * @param buf
	 *            the buffer to write to
	 * @param bytes
	 *            the character string to write
	 */
	public static void writeCharacterString(ByteBuffer buf, byte[] bytes) {
		if (bytes.length > 254) {
			throw new IllegalArgumentException("The length of a character string must be between 0 and 254 octets.");
		}
		buf.put((byte) bytes.length);
		buf.put(bytes);
	}

	/**
	 * Does the same as {@link #parseCharacterString(ByteBuffer)} but ensures
	 * that the character string's length does not exceed {@code maxLength}.
	 *
	 * @param buf
	 *            the buffer to read from
	 * @param maxLength
	 *            the maximum length of the character string including the
	 *            length byte
	 * @return the character string as a byte array
	 * @throws IOException
	 *             if the character string indicates that it is longer than
	 *             {@code maxLength} allows or remains in the buffer. In this
	 *             case, the buffer's position remains unchanged.
	 */
	public static byte[] parseCharacterString(ByteBuffer buf, int maxLength) throws IOException {
		int length = buf.get(buf.position()) & 0xFF;
		if (length + 1 > maxLength) {
			throw new IOException("Tried to read " + (length + 1) + " bytes but only " + maxLength + " available.");
		}
		if (length + 1 > buf.remaining()) {
			throw new IOException("Tried to read " + (length + 1) + " bytes but only " + buf.remaining() + " bytes are in the buffer.");
		}
		return parseCharacterString(buf);
	}

	/**
	 * Reads the next character string from the buffer. The first byte
	 * determines how long the string will be. After this method completes, the
	 * buffer will be positioned after the end of the string.
	 *
	 * @param buf
	 *            the buffer to read from
	 * @return the character string as a byte array
	 */
	private static byte[] parseCharacterString(ByteBuffer buf) {
		int length = buf.get() & 0xFF;
		byte[] bytes = new byte[length];

		buf.get(bytes);

		return bytes;
	}

}
