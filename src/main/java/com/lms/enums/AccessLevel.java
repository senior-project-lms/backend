package com.lms.enums;

public enum AccessLevel {
	
	SUPER_ADMIN(1101010010),
	ADMIN(1010000101),
	LECTURER(1001010010),
	ASSISTANT(1100101100),
	STUDENT(1101011010);

	
	public long CODE;

	AccessLevel(long CODE) {
		this.CODE = CODE;
	}
	
	
	
}
