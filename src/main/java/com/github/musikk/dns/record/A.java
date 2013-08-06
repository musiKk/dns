package com.github.musikk.dns.record;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class A extends RecordData<A> {

	private InetAddress address;

	public A() {
	}

	public A(InetAddress address) {
		checkAddress(address);
		this.address = address;
	}

	public InetAddress getAddress() {
		return address;
	}

	private static void checkAddress(InetAddress address) {
		if (address instanceof Inet6Address) {
			throw new IllegalArgumentException("A records are for IPv4 only. Use AAAA for IPv6.");
		}
	}

	@Override
	public A toBytes(ByteBuffer buf) throws IOException {
		buf.put(address.getAddress());
		return this;
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