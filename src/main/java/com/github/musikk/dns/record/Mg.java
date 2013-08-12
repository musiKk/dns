package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Mailbox;

public class Mg extends RecordData<Mg> {

	private final Mailbox mailbox;

	public Mg() {
		this(new Mailbox());
	}

	public Mg(Mailbox mailbox) {
		this.mailbox = mailbox;
	}

	public Mailbox getMailbox() {
		return mailbox;
	}

	@Override
	public RecordData<Mg> toBytes(ByteBuffer buf) throws IOException {
		mailbox.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Mg> fromBytes(ByteBuffer buf) throws IOException {
		mailbox.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Mg [mailbox=" + mailbox + "]";
	}

}
