package com.ofs.portal.config;

public enum Constants {

	SECURITY_HEADER("security");

	private final String constants;

	Constants(String constants) {
		this.constants = constants;
	}
	
	public String toString() {
		return this.constants;
	}
}
