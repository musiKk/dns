package com.github.musikk.dns;

public enum Type {
	A(1), NS(2), MD(3), MF(4), CNAME(5), SOA(6), MB(7), MG(8), MR(9),
	NULL(10), WKS(11), PTR(12), HINFO(13), MINFO(14), MX(15), TXT(16),
	AXFR(252), MAILB(253), MAILA(254), ALL(255), UNKNOWN(-1);
	private final int code;
	private Type(int code) {
		this.code = code;
	}
	public int getCode() {
		return this.code;
	}
	public boolean isQuestionType() {
		return this.code > 16;
	}
	public static Type byCode(int code) {
		for (Type t : values()) {
			if (t.code == code) {
				return t;
			}
		}
		System.err.println("No type with code " + code + " exists.");
		return UNKNOWN;
	}
}