package com.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.SignInCredentialsDTO;
import com.app.DTO.UserDTO;
import com.app.DTO.UserSignUpDTO;
import com.app.entity.Users;
import com.app.security.JwtUtils;
import com.app.service.UserService;

@RestController
@CrossOrigin(origins = "")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtils jwtutil;
	@Autowired
	private AuthenticationManager manager;

	@PostMapping("/signup")
	public ResponseEntity<?> signUpUser(@Valid @RequestBody UserSignUpDTO user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(user));
	}

	@PostMapping("/signin")
	// validate user | create token | authorize user |return User
	public ResponseEntity<?> signInUser(@Valid @RequestBody SignInCredentialsDTO request) {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		System.out.println("auth token " + authToken);
		try {
			Authentication authenticatedDetails = manager.authenticate(authToken);
			String jwt = jwtutil.generateJwtToken(authenticatedDetails);

			String responseCookie = ResponseCookie.from("token", jwt).maxAge(1000).httpOnly(true).build().toString();
			HttpHeaders responseHeader = new HttpHeaders();
			responseHeader.add(HttpHeaders.SET_COOKIE, responseCookie);

			Users signedinuser = userService.getUser(request);
			if (signedinuser != null) {
				return ResponseEntity.ok().body(new AuthResp(signedinuser, jwt, "User Auth Successful!"));
			} else
				throw new BadCredentialsException("Not user or Admin");
		} catch (BadCredentialsException e) {
			System.out.println("Error " + e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deletUser(@PathVariable("userId") int userId) {
		return ResponseEntity.ok().body(userService.deleteUser(userId));
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable("userId") int userId, @RequestBody UserDTO userInfo) {
		return ResponseEntity.ok().body(userService.update(userId, userInfo));
	}

}
