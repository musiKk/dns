package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;

public class Rp extends RecordData<Rp> {

	private final Domain mailbox;
	private final Domain txtDomain;

	public Rp() {
		this(new Domain(), new Domain());
	}

	public Rp(Domain mailbox, Domain txtDomain) {
		this.mailbox = mailbox;
		this.txtDomain = txtDomain;
	}

	public Domain getMailbox() {
		return mailbox;
	}

	public Domain getTxtDomain() {
		return txtDomain;
	}

	@Override
	public RecordData<Rp> toBytes(ByteBuffer buf) throws IOException {
		mailbox.toBytes(buf);
		txtDomain.toBytes(buf);
		return this;
	}

	@Override
	public RecordData<Rp> fromBytes(ByteBuffer buf) throws IOException {
		mailbox.fromBytes(buf);
		txtDomain.fromBytes(buf);
		return this;
	}

	@Override
	public String toString() {
		return "Rp [mailbox=" + mailbox + ", txtDomain=" + txtDomain + "]";
	}

}
