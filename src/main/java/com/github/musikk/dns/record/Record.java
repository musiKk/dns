package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Class;
import com.github.musikk.dns.Domain;
import com.github.musikk.dns.MessageContent;
import com.github.musikk.dns.Type;

public class Record implements MessageContent<Record> {
	private Domain domain;
	private Type recordType;
	private Class recordClass;
	private long ttl;
	private RecordData<?> recordData;
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public Type getRecordType() {
		return recordType;
	}
	public void setRecordType(Type recordType) {
		this.recordType = recordType;
	}
	public Class getRecordClass() {
		return recordClass;
	}
	public void setRecordClass(Class recordClass) {
		this.recordClass = recordClass;
	}
	public long getTtl() {
		return ttl;
	}
	public void setTtl(long ttl) {
		this.ttl = ttl;
	}
	public RecordData<?> getRecordData() {
		return recordData;
	}
	public void setRecordData(RecordData<?> recordData) {
		this.recordData = recordData;
	}
	@Override
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Record fromBytes(ByteBuffer buf) throws IOException {
		domain = new Domain().fromBytes(buf);
		recordType = Type.byCode(buf.getShort());
		recordClass = Class.byCode(buf.getShort());
		ttl = buf.getInt() & 0xFFFFFFFF;
		int recordDataLength = buf.getShort() & 0xFFFF;
		switch (recordType) {
		case A:
			recordData = new A();
			break;
		case CNAME:
			recordData = new Cname();
			break;
		case MX:
			recordData = new Mx();
			break;
		case NS:
			recordData = new Ns();
		}
		if (recordData != null) {
			recordData.fromBytes(buf);
		} else {
			System.err.println("unknown type " + recordType);
			buf.get(new byte[recordDataLength]);
		}
		return this;
	}
	@Override
	public String toString() {
		return "Record [domain=" + domain + ", recordType=" + recordType
				+ ", recordClass=" + recordClass + ", ttl=" + ttl
				+ ", recordData=" + recordData + "]";
	}
}