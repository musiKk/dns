package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.github.musikk.dns.Util;

public class Txt extends RecordData<Txt> {

	private final Collection<String> strings = new ArrayList<>();

	public Txt() {
	}

	public Txt(Collection<String> strings) {
		this.strings.addAll(strings);
	}

	public Collection<String> getStrings() {
		return Collections.unmodifiableCollection(strings);
	}

	@Override
	public RecordData<Txt> toBytes(ByteBuffer buf) throws IOException {
		for (String s : strings) {
			Util.writeCharacterString(buf, s.getBytes());
		}
		return this;
	}

	@Override
	public RecordData<Txt> fromBytes(ByteBuffer buf) throws IOException {
		int length = getRecordLength();
		while (length > 0) {
			int stringLength = buf.get(0);
			if (length - stringLength < 0) {
				System.err.println("Unable to parse TXT record. Character string is longer than total length allows.");
				return this;
			}
			byte[] b = Util.parseCharacterString(buf);
			length -= b.length;
			strings.add(new String(b));
		}
		return this;
	}

	@Override
	public String toString() {
		return "Txt [strings=" + strings + "]";
	}

}
