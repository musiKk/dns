package com.github.musikk.dns;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

import com.github.musikk.dns.record.Record;


public class Message implements MessageContent<Message> {
	private Header header;
	private final Collection<Question> questions = new ArrayList<>();
	private final Collection<Record> answers = new ArrayList<>();
	private final Collection<Record> authority = new ArrayList<>();
	private final Collection<Record> additional = new ArrayList<>();
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Collection<Question> getQuestions() {
		return questions;
	}
	public Collection<Record> getAnswers() {
		return answers;
	}
	public Collection<Record> getAuthority() {
		return authority;
	}
	public Collection<Record> getAdditional() {
		return additional;
	}
	@Override
	public byte[] toBytes() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(512);

		header.setQuestionEntries(questions.size());
		header.setAnswerEntries(answers.size());
		header.setAuthorityRecords(authority.size());
		header.setAdditionalRecords(additional.size());

		byte[] headerBytes = header.toBytes();
		out.write(headerBytes, 0, headerBytes.length);

		for (Question q : questions) {
			byte[] questionBytes = q.toBytes();
			out.write(questionBytes, 0, questionBytes.length);
		}
		for (Record r : answers) {
			byte[] recordBytes = r.toBytes();
			out.write(recordBytes, 0, recordBytes.length);
		}
		for (Record r : authority) {
			byte[] recordBytes = r.toBytes();
			out.write(recordBytes, 0, recordBytes.length);
		}
		for (Record r : additional) {
			byte[] recordBytes = r.toBytes();
			out.write(recordBytes, 0, recordBytes.length);
		}

		return out.toByteArray();
	}
	@Override
	public Message fromBytes(ByteBuffer buf) throws IOException {
		header = new Header();
		header.fromBytes(buf);

		questions.clear();
		answers.clear();
		authority.clear();
		additional.clear();
		for (int i = 0; i < header.getQuestionEntries(); i++) {
			questions.add(new Question().fromBytes(buf));
		}
		for (int i = 0; i < header.getAnswerEntries(); i++) {
			answers.add(new Record().fromBytes(buf));
		}
		for (int i = 0; i < header.getAuthorityRecords(); i++) {
			authority.add(new Record().fromBytes(buf));
		}
		for (int i = 0; i < header.getAdditionalRecords(); i++) {
			additional.add(new Record().fromBytes(buf));
		}
		return this;
	}
	@Override
	public String toString() {
		return "Message [header=" + header + ", questions=" + questions
				+ ", answers=" + answers + ", authority=" + authority
				+ ", additional=" + additional + "]";
	}
}