package com.github.musikk.dns;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface MessageContent<T extends MessageContent<T>> {
	T fromBytes(ByteBuffer buf) throws IOException;
	byte[] toBytes() throws IOException;
}