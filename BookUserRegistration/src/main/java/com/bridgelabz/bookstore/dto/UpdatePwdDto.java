package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class UpdatePwdDto {
	private String newpassword;
	private String conformpassword;
	private String token;
}
	