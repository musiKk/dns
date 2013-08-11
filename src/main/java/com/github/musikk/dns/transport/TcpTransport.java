package com.github.musikk.dns.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Message;

/**
 * Performs DNS requests over TCP.
 *
 * @author Werner
 * @see UdpTransport
 *
 */
public class TcpTransport extends DnsTransport {

	public static final int PORT = 53;

	private final InetAddress server;
	private final int port;

	public TcpTransport(InetAddress server) {
		this(server, PORT);
	}

	public TcpTransport(InetAddress server, int port) {
		this.server = server;
		this.port = port;
	}

	@Override
	public Message sendQuery(Message request) throws IOException {
		byte[] messageBytes = serializeMessage(request, 65536);

		try (Socket s = new Socket(server, port);
				OutputStream out = s.getOutputStream();
				InputStream in = s.getInputStream()) {
			int requestLength = messageBytes.length;
			out.write(new byte[] { (byte) ((requestLength >> 8) & 0xFF), (byte) requestLength });
			out.write(messageBytes);
			out.flush();

			int b1 = in.read();
			if (b1 == -1) {
				throw new IOException("Didn't receive any input.");
			}
			int b2 = in.read();
			if (b2 == -1) {
				throw new IOException("Didn't receive second length byte.");
			}
			int length = ((b1 & 0xFF) << 8) | (b2 & 0xFF);
			byte[] replyBytes = new byte[length];
			byte[] buf = new byte[4096];
			int readTotal = 0;
			while (readTotal < length) {
				int read = in.read(buf);
				if (read == -1) {
					throw new IOException("Stream closed but only read " + readTotal + " of " + length + " bytes.");
				}
				System.arraycopy(buf, 0, replyBytes, readTotal, read);
				readTotal += read;
			}
			return new Message().fromBytes(ByteBuffer.wrap(replyBytes));
		}
	}

}
