package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Nsap extends RecordData<Nsap> {

	private byte[] address;

	public Nsap() {
	}

	public Nsap(byte[] address) {
		this.address = address.clone();
	}

	public byte[] getAddress() {
		return address.clone();
	}

	@Override
	public RecordData<Nsap> toBytes(ByteBuffer buf) throws IOException {
		buf.put(address);
		return this;
	}

	@Override
	public RecordData<Nsap> fromBytes(ByteBuffer buf) throws IOException {
		if (buf.remaining() < getRecordLength()) {
			throw new IOException("Tried to read " + getRecordLength() + " bytes but the buffer has only " + buf.remaining() + " remaining.");
		}
		address = new byte[getRecordLength()];
		buf.get(address);
		return this;
	}

	@Override
	public String toString() {
		return "Nsap [address=" + Arrays.toString(address) + "]";
	}

}
