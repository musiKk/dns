package com.github.musikk.dns;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

public class Mailbox implements MessageContent<Mailbox> {

	private String address;

	public Mailbox() {
	}

	public Mailbox(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public Mailbox toBytes(ByteBuffer buf) throws IOException {
		// of course this only handles the most basic email addresses
		Domain.fromQName(address.replace('@', '.')).toBytes(buf);
		return this;
	}

	@Override
	public Mailbox fromBytes(ByteBuffer buf) throws IOException {
		Domain d = new Domain().fromBytes(buf);

		Iterator<String> labelIt = d.getLabels().iterator();
		String name = labelIt.next();
		address = String.format("%s@%s", name, StringUtils.join(labelIt, '.'));

		return this;
	}

	@Override
	public String toString() {
		return "Mailbox [address=" + address + "]";
	}

}
