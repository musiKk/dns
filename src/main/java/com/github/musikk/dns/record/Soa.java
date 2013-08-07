package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Domain;

public class Soa extends RecordData<Soa> {

	private final Domain zoneSource;
	private final Domain zoneMailbox;
	private long serial;
	private long refresh;
	private long retry;
	private long expire;
	private long minimum;

	public Soa() {
		zoneSource = new Domain();
		zoneMailbox = new Domain();
	}

	public Soa(Domain zoneSource, Domain zoneMailbox, long serial,
			long refresh, long retry, long expire, long minimum) {
		checkInt(serial);
		checkInt(refresh);
		checkInt(retry);
		checkInt(expire);
		checkInt(minimum);

		this.zoneSource = zoneSource;
		this.zoneMailbox = zoneMailbox;
		this.serial = serial;
		this.refresh = refresh;
		this.retry = retry;
		this.expire = expire;
		this.minimum = minimum;
	}

	public Domain getZoneSource() {
		return zoneSource;
	}

	public Domain getZoneMailbox() {
		return zoneMailbox;
	}

	public long getSerial() {
		return serial;
	}

	public long getRefresh() {
		return refresh;
	}

	public long getRetry() {
		return retry;
	}

	public long getExpire() {
		return expire;
	}

	public long getMinimum() {
		return minimum;
	}

	@Override
	public RecordData<Soa> toBytes(ByteBuffer buf) throws IOException {
		zoneSource.toBytes(buf);
		zoneMailbox.toBytes(buf);
		buf.putInt((int) serial);
		buf.putInt((int) refresh);
		buf.putInt((int) retry);
		buf.putInt((int) expire);
		buf.putInt((int) minimum);
		return this;
	}

	@Override
	public RecordData<Soa> fromBytes(ByteBuffer buf) throws IOException {
		zoneSource.fromBytes(buf);
		zoneMailbox.fromBytes(buf);
		serial = buf.getInt() & 0xFFFFFFFF;
		refresh = buf.getInt() & 0xFFFFFFFF;
		retry = buf.getInt() & 0xFFFFFFFF;
		expire = buf.getInt() & 0xFFFFFFFF;
		minimum = buf.getInt() & 0xFFFFFFFF;
		return this;
	}

	private static void checkInt(long val) {
		if (val > (1 >> 31) || val < 0) {
			throw new IllegalArgumentException("32 bit value expected but got " + val);
		}
	}

	@Override
	public String toString() {
		return "Soa [zoneSource=" + zoneSource + ", zoneMailbox=" + zoneMailbox
				+ ", serial=" + serial + ", refresh=" + refresh + ", retry="
				+ retry + ", expire=" + expire + ", minimum=" + minimum + "]";
	}

}
