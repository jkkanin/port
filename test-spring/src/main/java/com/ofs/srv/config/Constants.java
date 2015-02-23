package com.ofs.srv.config;

public enum Constants {

	SECURITY_ALGORITHM("RSA"),

	SECURITY_PROVIDER("BC"), // Bouncy Castle Provider

	SECURITY_HEADER("security");

	private final String constants;

	Constants(String constants) {
		this.constants = constants;
	}
	
	public String toString() {
		return this.constants;
	}
}
