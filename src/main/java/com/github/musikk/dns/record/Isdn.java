package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.github.musikk.dns.Util;

public class Isdn extends RecordData<Isdn> {

	private byte[] address;
	private byte[] subAddress;

	public Isdn() {
	}

	public Isdn(byte[] address, byte[] subAddress) {
		this.address = address.clone();
		if (subAddress != null) {
			this.subAddress = subAddress.clone();
		}
	}

	public byte[] getAddress() {
		return address.clone();
	}

	public byte[] getSubAddress() {
		return subAddress.clone();
	}

	@Override
	public RecordData<Isdn> toBytes(ByteBuffer buf) throws IOException {
		Util.writeCharacterString(buf, address);
		if (subAddress != null) {
			Util.writeCharacterString(buf, subAddress);
		}
		return this;
	}

	@Override
	public RecordData<Isdn> fromBytes(ByteBuffer buf) throws IOException {
		address = Util.parseCharacterString(buf, getRecordLength());
		if (address.length < getRecordLength()) {
			subAddress = Util.parseCharacterString(buf, getRecordLength() - address.length);
		}
		return this;
	}

	@Override
	public String toString() {
		return "Isdn [address=" + Arrays.toString(address) + ", subAddress="
				+ Arrays.toString(subAddress) + "]";
	}

}
