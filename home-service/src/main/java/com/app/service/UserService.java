package com.app.service;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.DTO.SignInCredentialsDTO;
import com.app.DTO.UserDTO;
import com.app.DTO.UserSignUpDTO;
import com.app.entity.Users;
import com.app.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;

	public Users signup(UserSignUpDTO userDto) {

		Users transUser = mapper.map(userDto, Users.class);
		Optional<Users> check = userRepo.findByEmail(transUser.getEmail());
		if (check.isEmpty()) {
			transUser.setPassword(encoder.encode(userDto.getPassword()));
			return userRepo.save(transUser);
		}
		return transUser;

	}

	public Users getUser(@Valid SignInCredentialsDTO credentials) {
		Optional<Users> signinuser = userRepo.findByEmail(credentials.getEmail());
		String rawPassword = credentials.getPassword();
		if (signinuser.isPresent() && encoder.matches(rawPassword, signinuser.get().getPassword())) {
			return signinuser.get();
		} else {
			return null;
		}

	}

	public Object deleteUser(int userId) {
		userRepo.deleteById(userId);
		return 1;
	}

	public Object update(int userId, UserDTO editUser) {
		Users checkUser = userRepo.getReferenceById(userId);
		if (checkUser != null) {
			Users updateUser = mapper.map(editUser, Users.class);
			updateUser.setUserId(checkUser.getUserId());
			updateUser.setPassword(checkUser.getPassword());
			userRepo.save(updateUser);
		}
		return null;
	}
}
