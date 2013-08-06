package com.github.musikk.dns;

import java.net.InetAddress;
import java.util.Random;

import com.github.musikk.dns.transport.Resolver;


public class DnsTest {

	public static final Random RANDOM = new Random();

//	public static final String DNS_HOST = "192.168.2.1";
	public static final String DNS_HOST = "208.67.222.222";
	public static final int PORT = 53;

	public static void main(String[] args) throws Exception {
		Resolver r = new Resolver(InetAddress.getByName(DNS_HOST));
		Message reply = r.request("ns2.google.com");
		System.err.println(reply);
	}

	private static Message createMessage() {
		Message m = new Message();

		Header h = new Header();
		h.setId(RANDOM.nextInt(1 << 15));
		h.setRequest(true);
		h.setOpcode(Header.Opcode.QUERY);
		h.setRecursionDesired(true);

		m.setHeader(h);

		Question q = new Question();
		q.setDomain(Domain.fromQName("ns2.google.com"));
		q.setQuestionType(Type.ALL);
		q.setQuestionClass(Class.IN);

		m.getQuestions().add(q);

		return m;
	}

}
