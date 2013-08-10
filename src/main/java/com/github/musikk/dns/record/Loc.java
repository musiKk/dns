package com.github.musikk.dns.record;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Loc extends RecordData<Loc> {

	/**
	 * Altitude is based at 100km below the WGS 84 reference spheroid. This
	 * constant's unit is cm.
	 */
	public final static int ALTITUDE_BASE = 10_000_000;
	private final static int LATITUDE_LONGITUDE_CORRECTION = 1 << 31;

	private long size;
	private long horizontalPrecision;
	private long verticalPrecision;
	private int latitude;
	private int longitude;
	private int altitude;

	public Loc() {
	}

	public Loc(long size, long horizontalPrecision,
			long verticalPrecision, int latitude, int longitude, int altitude) {
		this.size = size;
		this.horizontalPrecision = horizontalPrecision;
		this.verticalPrecision = verticalPrecision;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	/**
	 * The size in cm of the sphere enclosing the entity described by the
	 * record.
	 *
	 * @return
	 */
	public long getSize() {
		return size;
	}

	/**
	 * The horizontal precision in cm.
	 *
	 * @return
	 */
	public long getHorizontalPrecision() {
		return horizontalPrecision;
	}

	/**
	 * The vertical precision in cm.
	 *
	 * @return
	 */
	public long getVerticalPrecision() {
		return verticalPrecision;
	}

	/**
	 * The latitude in thousandths of arc seconds of the center of the sphere. 0
	 * (zero) is the equator.
	 *
	 * @return
	 */
	public int getLatitude() {
		return latitude;
	}

	/**
	 * The longitude in thousandth of arc seconds of the center of the sphere. 0
	 * (zero) is the prime meridian. Positive values are east longitude.
	 *
	 * @return
	 */
	public int getLongitude() {
		return longitude;
	}

	/**
	 * The altitude in cm of the center of the sphere. The altitude is based at
	 * {@link #ALTITUDE_BASE}.
	 *
	 * @return
	 */
	public int getAltitude() {
		return altitude;
	}

	@Override
	public RecordData<Loc> toBytes(ByteBuffer buf) throws IOException {
		byte s = byteFromSize(size);
		byte hP = byteFromSize(horizontalPrecision);
		byte vP = byteFromSize(verticalPrecision);

		buf.put((byte) 0);
		buf.put(s);
		buf.put(hP);
		buf.put(vP);

		buf.putInt(latitude - LATITUDE_LONGITUDE_CORRECTION);
		buf.putInt(longitude - LATITUDE_LONGITUDE_CORRECTION);
		buf.putInt(altitude);

		return this;
	}

	@Override
	public RecordData<Loc> fromBytes(ByteBuffer buf) throws IOException {
		byte version = buf.get(buf.position());
		if (version != 0) {
			throw new IOException("The version of a LOC record must be 0 but was " + version + ".");
		}

		size = sizeFromByte(buf.get(buf.position() + 1));
		horizontalPrecision = sizeFromByte(buf.get(buf.position() + 2));
		verticalPrecision = sizeFromByte(buf.get(buf.position() + 3));
		buf.position(buf.position() + 4);

		latitude = buf.getInt() + LATITUDE_LONGITUDE_CORRECTION;
		longitude = buf.getInt() + LATITUDE_LONGITUDE_CORRECTION;
		altitude = buf.getInt();
		return this;
	}

	private static long sizeFromByte(byte size) {
		int base = (size >> 4) & 0xF;
		int power = size & 0xF;

		if (base > 9 || power > 9) {
			throw new IllegalArgumentException(String.format("Base and power must be no greater than 9 (base: %d, power: %d).", base, power));
		}

		return base * ((long) Math.pow(10, power));
	}

	private static byte byteFromSize(long size) {
		int power = (int) Math.log10(size);
		int base = (int) (size / (int) Math.pow(10, power));

		if (base > 9 || power > 9) {
			throw new IllegalArgumentException(String.format("Base and power must be no greater than 9 (base: %d, power: %d).", base, power));
		}

		return (byte) ((base & 0xF) << 4 | (power & 0xF));
	}

	@Override
	public String toString() {
		return "Loc [size=" + size + ", horizontalPrecision="
				+ horizontalPrecision + ", verticalPrecision="
				+ verticalPrecision + ", latitude=" + latitude + ", longitude="
				+ longitude + ", altitude=" + altitude + "]";
	}

}
