package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;

public class Mg extends RecordData<Mg> {

	private final Domain domain;

	public Mg() {
		this(new Domain());
	}

	public Mg(Domain domain) {
		this.domain = domain;
	}

	public Domain getDomain() {
		return domain;
	}

	@Override
	public RecordData<Mg> toBytes(ByteBuffer buf) throws IOException {
		domain.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Mg> fromBytes(ByteBuffer buf) throws IOException {
		domain.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Mg [domain=" + domain + "]";
	}

}
