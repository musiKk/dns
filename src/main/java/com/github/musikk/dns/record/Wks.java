package com.github.musikk.dns.record;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.BitSet;

public class Wks extends RecordData<Wks> {

	private InetAddress address;
	private int protocol;
	private BitSet bitmap;

	public Wks() {
	}

	public Wks(InetAddress address, int protocol, BitSet bitmap) {
		if (protocol < 0 || protocol > 255) {
			throw new IllegalArgumentException("A protocol must be between 0 and 255 (was" + protocol + ").");
		}
		this.address = address;
		this.protocol = protocol;
		this.bitmap = (BitSet) bitmap.clone();
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getProtocol() {
		return protocol;
	}

	public BitSet getBitmap() {
		return bitmap;
	}

	@Override
	public RecordData<Wks> toBytes(ByteBuffer buf) throws IOException {
		buf.put(address.getAddress());
		buf.put((byte) protocol);
		buf.put(bitmap.toByteArray());
		return this;
	}

	@Override
	public RecordData<Wks> fromBytes(ByteBuffer buf) throws IOException {
		byte[] addressBytes = new byte[4];
		buf.get(addressBytes);
		address = InetAddress.getByAddress(addressBytes);

		protocol = buf.get() & 0xFF;

		byte[] bitmapBytes = new byte[getRecordLength() - 5];
		buf.get(bitmapBytes);
		bitmap = BitSet.valueOf(bitmapBytes);

		return this;
	}

	@Override
	public String toString() {
		return "Wks [address=" + address + ", protocol=" + protocol
				+ ", bitmap=" + bitmap + "]";
	}

}
