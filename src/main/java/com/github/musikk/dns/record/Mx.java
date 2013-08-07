package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;

public class Mx extends RecordData<Mx> {

	private int preference;
	private final Domain exchange;

	public Mx() {
		this(0, new Domain());
	}

	public Mx(int preference, Domain exchange) {
		if (preference > 65535 || preference < 0) {
			throw new IllegalArgumentException("Preference must be between 0 and 65535.");
		}
		this.preference = preference;
		this.exchange = exchange;
	}

	public int getPreference() {
		return preference;
	}

	public Domain getExchange() {
		return exchange;
	}

	@Override
	public Mx toBytes(ByteBuffer buf) throws IOException {
		buf.putShort((short) preference);
		exchange.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Mx> fromBytes(ByteBuffer buf) throws IOException {
		preference = buf.getShort() & 0xFFFF;
		exchange.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Mx [preference=" + preference + ", exchange=" + exchange
				+ "]";
	}
}