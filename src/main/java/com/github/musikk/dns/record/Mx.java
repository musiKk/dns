package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;


public class Mx extends RecordData<Mx> {
	private int preference;
	private Domain exchange;
	@Override
	public byte[] toBytes() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public RecordData<Mx> fromBytes(ByteBuffer buf) throws IOException {
		preference = buf.getShort() & 0xFFFF;
		exchange = new Domain();
		exchange.fromBytes(buf);
		return this;
	}
	@Override
	public String toString() {
		return "Mx [preference=" + preference + ", exchange=" + exchange
				+ "]";
	}
}