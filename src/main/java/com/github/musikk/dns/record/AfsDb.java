package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;

public class AfsDb extends RecordData<AfsDb> {

	private int subtype;
	private final Domain domain;

	public AfsDb() {
		this.domain = new Domain();
	}

	public AfsDb(int subtype, Domain domain) {
		if (subtype < 0 || subtype > 65535) {
			throw new IllegalArgumentException("The subtype must be between 0 and 65535 (was" + subtype + ").");
		}
		this.subtype = subtype;
		this.domain = domain;
	}

	public int getSubtype() {
		return subtype;
	}

	public Domain getDomain() {
		return domain;
	}

	@Override
	public RecordData<AfsDb> toBytes(ByteBuffer buf) throws IOException {
		buf.putShort((short) subtype);
		domain.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<AfsDb> fromBytes(ByteBuffer buf) throws IOException {
		subtype = buf.getShort() & 0xFFFF;
		domain.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "AfsDb [subtype=" + subtype + ", domain=" + domain + "]";
	}

}
