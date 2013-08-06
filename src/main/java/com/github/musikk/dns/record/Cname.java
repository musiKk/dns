package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;


public class Cname extends RecordData<Cname> {
	private Domain domain;
	@Override
	public byte[] toBytes() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public RecordData<Cname> fromBytes(ByteBuffer buf) throws IOException {
		domain = new Domain();
		domain.fromBytes(buf);
		return this;
	}
	@Override
	public String toString() {
		return "Cname [domain=" + domain + "]";
	}
}