package com.github.musikk.dns;

import java.io.IOException;
import java.nio.ByteBuffer;


public class Header implements MessageContent<Header> {
	private short id;
	private boolean request;
	private Header.Opcode opcode;
	private boolean authoritativeAnswer;
	private boolean truncated;
	private boolean recursionDesired;
	private boolean recursionAvailable;
	private Header.ResponseCode responseCode;
	private short questionEntries;
	private short answerEntries;
	private short authorityRecords;
	private short additionalRecords;

	public int getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public boolean isRequest() {
		return request;
	}
	public void setRequest(boolean request) {
		this.request = request;
	}
	public Header.Opcode getOpcode() {
		return opcode;
	}
	public void setOpcode(Header.Opcode opcode) {
		this.opcode = opcode;
	}
	public boolean isAuthoritativeAnswer() {
		return authoritativeAnswer;
	}
	public void setAuthoritativeAnswer(boolean authoritativeAnswer) {
		this.authoritativeAnswer = authoritativeAnswer;
	}
	public boolean isTruncated() {
		return truncated;
	}
	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}
	public boolean isRecursionDesired() {
		return recursionDesired;
	}
	public void setRecursionDesired(boolean recursionDesired) {
		this.recursionDesired = recursionDesired;
	}
	public boolean isRecursionAvailable() {
		return recursionAvailable;
	}
	public void setRecursionAvailable(boolean recursionAvailable) {
		this.recursionAvailable = recursionAvailable;
	}
	public Header.ResponseCode getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Header.ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
	public int getQuestionEntries() {
		return questionEntries;
	}
	public void setQuestionEntries(short questionEntries) {
		this.questionEntries = questionEntries;
	}
	public int getAnswerEntries() {
		return answerEntries;
	}
	public void setAnswerEntries(short answerEntries) {
		this.answerEntries = answerEntries;
	}
	public int getAuthorityRecords() {
		return authorityRecords;
	}
	public void setAuthorityRecords(short authorityRecords) {
		this.authorityRecords = authorityRecords;
	}
	public int getAdditionalRecords() {
		return additionalRecords;
	}
	public void setAdditionalRecords(short additionalRecords) {
		this.additionalRecords = additionalRecords;
	}
	@Override
	public Header toBytes(ByteBuffer buf) {
		buf.putShort(id);
		short flags = (short) ((request ? 0 : 1) << 15);
		flags |= (opcode.getCode() & 0b1111) << 11;
		// AA, TC set in response
		flags |= (recursionDesired ? 1 : 0) << 8;
		// RA, RCODE set in response

		buf.putShort(flags);
		buf.putShort(questionEntries);
		buf.putShort(answerEntries);
		buf.putShort(authorityRecords);
		buf.putShort(additionalRecords);
		return this;
	}
	@Override
	public Header fromBytes(ByteBuffer buf) throws IOException {
		id = buf.getShort();
		int flags = buf.getShort();
		request = ((flags >> 15) & 1) == 0;
		opcode = Opcode.byCode((flags >> 11) & 0b1111);
		authoritativeAnswer = ((flags >> 10) & 1) == 1;
		truncated = ((flags >> 9) & 1) == 1;
		recursionDesired = ((flags >> 8) & 1) == 1;
		recursionAvailable = ((flags >> 7) & 1) == 1;
		responseCode = ResponseCode.byCode(flags & 0b1111);

		questionEntries = buf.getShort();
		answerEntries = buf.getShort();
		authorityRecords = buf.getShort();
		additionalRecords = buf.getShort();

		return this;
	}
	static void set16Bit(byte[] target, int source, int targetOffset) {
		target[targetOffset] = (byte)((source >> 8) & 0xFF);
		target[targetOffset + 1] = (byte)(source & 0xFF);
	}
	static int get16Bit(byte[] source, int offset) {
		return source[offset] << 8 | source[offset + 1];
	}
	public enum Opcode {
		QUERY(0), IQUERY(1), STATUS(2);
		private final int code;
		private Opcode(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		public static Header.Opcode byCode(int code) {
			for (Header.Opcode o : values()) {
				if (o.code == code) {
					return o;
				}
			}
			throw new IllegalArgumentException("No opcode for code "  + code + " exists.");
		}
	}
	public enum ResponseCode {
		NO_ERROR(0), FORMAT_ERROR(1), SERVER_FAILURE(2), NAME_ERROR(3),
		NOT_IMPLEMENTED(4), REFUSED(5);
		private final int code;

		private ResponseCode(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		public static Header.ResponseCode byCode(int code) {
			for (Header.ResponseCode r : values()) {
				if (r.code == code) {
					return r;
				}
			}
			throw new IllegalArgumentException("No response for code " + code + " exists.");
		}
	}
	@Override
	public String toString() {
		return "Header [id=" + id + ", request=" + request + ", opcode="
				+ opcode + ", authoritativeAnswer=" + authoritativeAnswer
				+ ", truncated=" + truncated + ", recursionDesired="
				+ recursionDesired + ", recursionAvailable="
				+ recursionAvailable + ", responseCode=" + responseCode
				+ ", questionEntries=" + questionEntries
				+ ", answerEntries=" + answerEntries
				+ ", authorityRecords=" + authorityRecords
				+ ", additionalRecords=" + additionalRecords + "]";
	}
}