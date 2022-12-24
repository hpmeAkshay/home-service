package com.app.DTO;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserSignUpDTO {

	private int userId;

	@NotBlank(message = "First Name is Required")
	private String firstName;

	@NotBlank(message = "Lst Name is Required")
	private String lastName;

	@NotBlank(message = "Email is Required")
	@Email
	private String email;

	@NotBlank(message = "password is Required")
	@Size(min = 5, max = 10)
	private String password;

	@NotBlank(message = "Phone Number is Required")
	@Size(min = 10, max = 10)
	private String phoneNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date joinDate;

	
}
