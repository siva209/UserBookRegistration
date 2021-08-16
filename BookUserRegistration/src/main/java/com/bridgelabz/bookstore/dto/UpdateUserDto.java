package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class UpdateUserDto {
	private String FirstName;
	private String LastName;
	private String Kyc;
	private String dataOfBirth;
	private String otp;
	private String Email;
	private String Password;

}
