package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;

public class Mb extends RecordData<Mb> {

	private final Domain domain;

	public Mb() {
		this(new Domain());
	}

	public Mb(Domain domain) {
		this.domain = domain;
	}

	public Domain getDomain() {
		return domain;
	}

	@Override
	public Mb toBytes(ByteBuffer buf) throws IOException {
		domain.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Mb> fromBytes(ByteBuffer buf) throws IOException {
		domain.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Mb [domain=" + domain + "]";
	}

}
