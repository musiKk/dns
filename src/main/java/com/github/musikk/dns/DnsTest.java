package com.github.musikk.dns;

import java.net.InetAddress;
import java.util.Random;

import com.github.musikk.dns.record.Record;
import com.github.musikk.dns.transport.Resolver;

public class DnsTest {

	public static final Random RANDOM = new Random();

	public static final String DNS_HOST = "192.168.2.1";
//	public static final String DNS_HOST = "208.67.222.222";
	public static final int PORT = 53;

	public static void main(String[] args) throws Exception {
		Resolver r = new Resolver(InetAddress.getByName(DNS_HOST));
		Message reply = r.request("google.com");
//		System.err.println(reply);
		System.err.println("questions:");
		for (Question q : reply.getQuestions()) {
			System.err.println(" - " + q);
		}
		System.err.println("answers:");
		for (Record a : reply.getAnswers()) {
			System.err.println(" - " + a);
		}
	}

}
