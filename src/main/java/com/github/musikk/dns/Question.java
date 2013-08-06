package com.github.musikk.dns;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;


public class Question implements MessageContent<Question> {
	private Domain domain;
	private Type questionType;
	private Class questionClass;
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public Type getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Type questionType) {
		this.questionType = questionType;
	}
	public Class getQuestionClass() {
		return questionClass;
	}
	public void setQuestionClass(Class questionClass) {
		this.questionClass = questionClass;
	}
	@Override
	public byte[] toBytes() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutput dataOut = new DataOutputStream(out);

		byte[] domainBytes = domain.toBytes();
		dataOut.write(domainBytes);
		dataOut.writeShort(questionType.getCode());
		dataOut.writeShort(questionClass.getCode());

		return out.toByteArray();
	}
	@Override
	public Question fromBytes(ByteBuffer buf) throws IOException {
		domain = new Domain().fromBytes(buf);

		questionType = Type.byCode(buf.getShort());
		questionClass = Class.byCode(buf.getShort());

		return this;
	}
	@Override
	public String toString() {
		return "Question [domain=" + domain + ", questionType="
				+ questionType + ", questionClass=" + questionClass + "]";
	}
}