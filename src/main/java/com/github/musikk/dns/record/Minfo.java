package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Mailbox;

public class Minfo extends RecordData<Minfo> {

	private final Mailbox mailbox;
	private final Mailbox errorMailbox;

	public Minfo() {
		this(new Mailbox(), new Mailbox());
	}

	public Minfo(Mailbox mailbox, Mailbox errorMailbox) {
		this.mailbox = mailbox;
		this.errorMailbox = errorMailbox;
	}

	public Mailbox getMailbox() {
		return mailbox;
	}

	public Mailbox getErrorMailbox() {
		return errorMailbox;
	}

	@Override
	public RecordData<Minfo> toBytes(ByteBuffer buf) throws IOException {
		mailbox.toBytes(buf);
		errorMailbox.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Minfo> fromBytes(ByteBuffer buf) throws IOException {
		mailbox.fromBytes(buf);
		errorMailbox.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Minfo [mailbox=" + mailbox + ", errorMailbox=" + errorMailbox
				+ "]";
	}

}
