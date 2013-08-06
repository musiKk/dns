package com.github.musikk.dns;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface MessageContent<T extends MessageContent<T>> {
	T toBytes(ByteBuffer buf) throws IOException;
	T fromBytes(ByteBuffer buf) throws IOException;
}