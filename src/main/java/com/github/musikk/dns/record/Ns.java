package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;


public class Ns extends RecordData<Ns> {
	private Domain domain;
	@Override
	public byte[] toBytes() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public RecordData<Ns> fromBytes(ByteBuffer buf) throws IOException {
		domain = new Domain();
		domain.fromBytes(buf);
		return this;
	}
	@Override
	public String toString() {
		return "Ns [domain=" + domain + "]";
	}
}