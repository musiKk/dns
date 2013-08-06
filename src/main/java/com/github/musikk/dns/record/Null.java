package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Null extends RecordData<Null> {

	private byte[] data;

	public Null() {
	}

	public Null(byte[] data) {
		this.data = data;
	}

	@Override
	public RecordData<Null> toBytes(ByteBuffer buf) throws IOException {
		buf.put(data);
		return this;
	}

	@Override
	public RecordData<Null> fromBytes(ByteBuffer buf) throws IOException {
		data = new byte[getRecordLength()];
		buf.get(data);
		return this;
	}

}
