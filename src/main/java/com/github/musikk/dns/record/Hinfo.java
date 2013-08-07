package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Util;

public class Hinfo extends RecordData<Hinfo> {

	private String cpu;
	private String os;

	public Hinfo() {
	}

	public Hinfo(String cpu, String os) {
		this.cpu = cpu;
		this.os = os;
	}

	public String getCpu() {
		return cpu;
	}

	public String getOs() {
		return os;
	}

	@Override
	public Hinfo toBytes(ByteBuffer buf) throws IOException {
		Util.writeCharacterString(buf, cpu.getBytes());
		Util.writeCharacterString(buf, os.getBytes());
		return this;
	}

	@Override
	public RecordData<Hinfo> fromBytes(ByteBuffer buf) throws IOException {
		cpu = new String(Util.parseCharacterString(buf, getRecordLength() - 1));
		os = new String(Util.parseCharacterString(buf, getRecordLength() - cpu.length()));
		return this;
	}

	@Override
	public String toString() {
		return "Hinfo [cpu=" + cpu + ", os=" + os + "]";
	}

}
