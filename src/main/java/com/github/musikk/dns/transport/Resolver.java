package com.github.musikk.dns.transport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Random;

import com.github.musikk.dns.Class;
import com.github.musikk.dns.Domain;
import com.github.musikk.dns.Header;
import com.github.musikk.dns.Header.Opcode;
import com.github.musikk.dns.Message;
import com.github.musikk.dns.Question;
import com.github.musikk.dns.Type;

public class Resolver {

	private static final Random RANDOM = new Random();

	public static final int DNS_PORT = 53;

	private final InetAddress dnsServer;

	public Resolver(InetAddress dnsServer) {
		this.dnsServer = dnsServer;
	}

	public Message request(String domain) throws IOException {
		return this.request(domain, Type.ALL);
	}

	public Message request(String domain, Type questionType) throws IOException {
		return this.request(domain, Class.IN, questionType);
	}

	public Message request(String domain, Class questionClass) throws IOException {
		return this.request(domain, questionClass, Type.ALL);
	}

	public Message request(String domain, Class questionClass, Type questionType) throws IOException {
		Message requestMessage = createMessage(domain, questionClass, questionType);
		return getReply(requestMessage);
	}

	public Message getReply(Message requestMessage) throws IOException {
		if (requestMessage.getHeader().getId() == 0) {
			requestMessage.getHeader().setId((short) RANDOM.nextInt(1 << 15));
		}

		ByteBuffer requestBuffer = ByteBuffer.allocate(1500);
		requestMessage.toBytes(requestBuffer);
		requestBuffer.flip();

		byte[] messageBytes = new byte[requestBuffer.limit()];
		requestBuffer.get(messageBytes);

		DatagramPacket requestPacket = new DatagramPacket(
				messageBytes, messageBytes.length,
				dnsServer, DNS_PORT);

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

	private Message createMessage(String domain, Class questionClass, Type questionType) {
		Question question = new Question();
		question.setDomain(Domain.fromQName(domain));
		question.setQuestionClass(questionClass);
		question.setQuestionType(questionType);

		Header header = new Header();
		header.setOpcode(Opcode.QUERY);
		header.setRequest(true);
		header.setRecursionDesired(true);

		Message requestMessage = new Message();
		requestMessage.setHeader(header);
		requestMessage.getQuestions().add(question);

		return requestMessage;
	}

}
