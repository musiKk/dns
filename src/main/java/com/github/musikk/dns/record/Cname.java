package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;

public class Cname extends RecordData<Cname> {

	private final Domain domain;

	public Cname() {
		this(new Domain());
	}

	public Cname(Domain domain) {
		this.domain = domain;
	}

	public Domain getDomain() {
		return domain;
	}

	@Override
	public Cname toBytes(ByteBuffer buf) throws IOException {
		domain.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Cname> fromBytes(ByteBuffer buf) throws IOException {
		domain.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Cname [domain=" + domain + "]";
	}
}