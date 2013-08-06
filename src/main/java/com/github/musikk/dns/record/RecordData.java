package com.github.musikk.dns.record;

import com.github.musikk.dns.MessageContent;

public abstract class RecordData<T> implements MessageContent<RecordData<T>> {
	private int recordLength;

	void setRecordLength(int recordLength) {
		this.recordLength = recordLength;
	}
	protected int getRecordLength() {
		return recordLength;
	}
}