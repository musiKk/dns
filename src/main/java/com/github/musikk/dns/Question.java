package com.github.musikk.dns;

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
	public Question toBytes(ByteBuffer buf) throws IOException {
		domain.toBytes(buf);
		buf.putShort((short) questionType.getCode());
		buf.putShort((short) questionClass.getCode());

		return this;
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