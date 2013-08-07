package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;

public class Minfo extends RecordData<Minfo> {

	private final Domain domain;
	private final Domain errorDomain;

	public Minfo() {
		this(new Domain(), new Domain());
	}

	public Minfo(Domain domain, Domain errorDomain) {
		this.domain = domain;
		this.errorDomain = errorDomain;
	}

	public Domain getDomain() {
		return domain;
	}

	public Domain getErrorDomain() {
		return errorDomain;
	}

	@Override
	public RecordData<Minfo> toBytes(ByteBuffer buf) throws IOException {
		domain.toBytes(buf);
		errorDomain.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Minfo> fromBytes(ByteBuffer buf) throws IOException {
		domain.fromBytes(buf);
		errorDomain.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Minfo [domain=" + domain + ", errorDomain=" + errorDomain + "]";
	}

}
