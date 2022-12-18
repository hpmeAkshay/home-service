package com.app.DTO;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

	private int userId;
	private String firstName;
	private String lastName;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
	private String phoneNo;
	private LocalDate joinDate;

}
