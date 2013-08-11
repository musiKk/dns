package com.github.musikk.dns.transport;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Message;

/**
 * Common interface for low level communication with a DNS server.
 *
 * @author Werner
 * @see UdpTransport
 * @see TcpTransport
 *
 */
public abstract class DnsTransport {

	/**
	 * Sends a request to a DNS server and returns the result synchronously.
	 *
	 * @param request
	 *            the request message
	 * @return the reply message
	 * @throws IOException
	 */
	public abstract Message sendQuery(Message request) throws IOException;

	/**
	 * Serializes the message into the DNS wire format.
	 *
	 * @param requestMessage
	 *            the request message
	 * @param maxPacketSize
	 *            the maximum length of the packet
	 * @return the encoded DNS message
	 * @throws IOException
	 */
	protected byte[] serializeMessage(Message requestMessage, int maxPacketSize) throws IOException {
		ByteBuffer requestBuffer = ByteBuffer.allocate(maxPacketSize);
		requestMessage.toBytes(requestBuffer);
		requestBuffer.flip();

		byte[] messageBytes = new byte[requestBuffer.limit()];
		requestBuffer.get(messageBytes);
		return messageBytes;
	}

}
