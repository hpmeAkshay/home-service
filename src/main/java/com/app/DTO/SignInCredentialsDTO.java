package com.app.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignInCredentialsDTO {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	public SignInCredentialsDTO(@NotBlank @Email String email, @NotBlank String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SignInCredentialsDTO [email=" + email + ", password=" + password + "]";
	}

}
