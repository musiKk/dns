package com.github.musikk.dns.transport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import com.github.musikk.dns.Message;

/**
 * Performs DNS requests over UDP. This is the default method for communication
 * with DNS servers and should always be used except in cases where TCP is
 * mandatory (such as {@link com.github.musikk.dns.Type#AXFR AXFR}).
 *
 * @author Werner
 * @see TcpTransport
 *
 */
public class UdpTransport extends DnsTransport {

	public static final int MAX_PACKET_SIZE = 512;

	public static final int PORT = 53;

	private final InetAddress server;
	private final int port;

	public UdpTransport(InetAddress server) {
		this(server, PORT);
	}

	public UdpTransport(InetAddress server, int port) {
		this.server = server;
		this.port = port;
	}

	@Override
	public Message sendQuery(Message requestMessage) throws IOException {
		byte[] messageBytes = serializeMessage(requestMessage, MAX_PACKET_SIZE);

		DatagramPacket requestPacket = new DatagramPacket(
				messageBytes, messageBytes.length,
				server, port);

		try (DatagramSocket s = new DatagramSocket()) {
			s.send(requestPacket);

			byte[] buf = new byte[1024];
			DatagramPacket response = new DatagramPacket(buf, buf.length);
			s.receive(response);

			byte[] responseBytes = new byte[response.getLength()];
			System.arraycopy(response.getData(), response.getOffset(), responseBytes, 0, response.getLength());

			ByteBuffer byteBuffer = ByteBuffer.wrap(responseBytes);
			Message responseMessage = new Message().fromBytes(byteBuffer);

			return responseMessage;
		}
	}

}
