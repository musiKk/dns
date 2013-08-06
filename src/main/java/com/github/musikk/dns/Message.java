package com.github.musikk.dns;

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
	public Message toBytes(ByteBuffer buf) throws IOException {
		header.setQuestionEntries((short) questions.size());
		header.setAnswerEntries((short) answers.size());
		header.setAuthorityRecords((short) authority.size());
		header.setAdditionalRecords((short) additional.size());

		header.toBytes(buf);

		for (Question q : questions) {
			q.toBytes(buf);
		}
		for (Record r : answers) {
			r.toBytes(buf);
		}
		for (Record r : authority) {
			r.toBytes(buf);
		}
		for (Record r : additional) {
			r.toBytes(buf);
		}

		return this;
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