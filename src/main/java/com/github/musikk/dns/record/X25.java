package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.github.musikk.dns.Util;

public class X25 extends RecordData<X25> {

	private byte[] psdnAddress;

	public X25() {
	}

	public X25(byte[] psdnAddress) {
		this.psdnAddress = psdnAddress.clone();
	}

	public byte[] getPsdnAddress() {
		return psdnAddress;
	}

	@Override
	public RecordData<X25> toBytes(ByteBuffer buf) throws IOException {
		Util.writeCharacterString(buf, psdnAddress);
		return this;
	}

	@Override
	public RecordData<X25> fromBytes(ByteBuffer buf) throws IOException {
		psdnAddress = Util.parseCharacterString(buf, getRecordLength());
		return this;
	}

	@Override
	public String toString() {
		return "X25 [psdnAddress=" + Arrays.toString(psdnAddress) + "]";
	}

}
