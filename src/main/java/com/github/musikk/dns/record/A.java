package com.github.musikk.dns.record;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class A extends RecordData<A> {
	private InetAddress address;
	@Override
	public byte[] toBytes() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public RecordData<A> fromBytes(ByteBuffer buf) throws IOException {
		byte[] b = new byte[4];
		buf.get(b);
		address = InetAddress.getByAddress(b);
		return this;
	}
	@Override
	public String toString() {
		return "A [address=" + address + "]";
	}
}