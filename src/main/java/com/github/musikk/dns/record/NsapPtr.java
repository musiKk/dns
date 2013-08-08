package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;

public class NsapPtr extends RecordData<NsapPtr> {

	private final Domain domain;

	public NsapPtr() {
		this(new Domain());
	}

	public NsapPtr(Domain domain) {
		this.domain = domain;
	}

	public Domain getDomain() {
		return domain;
	}

	@Override
	public RecordData<NsapPtr> toBytes(ByteBuffer buf) throws IOException {
		domain.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<NsapPtr> fromBytes(ByteBuffer buf) throws IOException {
		domain.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "NsapPtr [domain=" + domain + "]";
	}

}
