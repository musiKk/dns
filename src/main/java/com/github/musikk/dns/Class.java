package com.github.musikk.dns;

public enum Class {

	IN(1), CS(2), CH(3), HS(4), ANY(255);

	private final int code;

	private Class(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public boolean isQuestionClass() {
		return this.code == 255;
	}

	public static Class byCode(int code) {
		for (Class c : values()) {
			if (c.code == code) {
				return c;
			}
		}
		throw new IllegalArgumentException("No class with code " + code + " exists.");
	}
}