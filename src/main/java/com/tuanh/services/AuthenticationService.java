package com.tuanh.services;

import com.tuanh.constants.Message;
import com.tuanh.dtos.LoginResponseDto;
import com.tuanh.dtos.UserDto;
import com.tuanh.dtos.request.RegisterUserDto;
import com.tuanh.entities.Role;
import com.tuanh.entities.User;
import com.tuanh.exceptions.HttpException;
import com.tuanh.mapper.UserMapper;
import com.tuanh.repository.RoleRepository;
import com.tuanh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	public User registerUser(RegisterUserDto registerUserDto) {
		Boolean existingUser = userRepository.existsByUsernameOrEmail(registerUserDto.getUsername(), registerUserDto.getEmail());

		if (existingUser) {
			throw HttpException.badRequest(Message.USER_ALREADY_EXISTS.getMessage());
		}

		String encodedPassword = passwordEncoder.encode(registerUserDto.getPassword());
		Role userRole = roleRepository.findByAuthority("USER").get();

		Set<Role> authorities = new HashSet<>();

		authorities.add(userRole);

		User user = new User();
		user.setUsername(registerUserDto.getUsername());
		user.setFullName(registerUserDto.getFullName());
		user.setEmail(registerUserDto.getEmail());
		user.setPhone(registerUserDto.getPhone());
		user.setPassword(encodedPassword);
		user.setAuthorities(authorities);

		return userRepository.save(user);
	}

	public LoginResponseDto loginUser(String username, String password) {
		try {
			Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
			);


			User user = userRepository.findByUsername(username)
				.orElseThrow(() -> HttpException.notFound(Message.USER_NOT_FOUND.getMessage()));

			String token = tokenService.generateJwt(auth, user);

			UserDto userDto = UserMapper.toUserDto(user);
			return new LoginResponseDto(userDto, token);

		} catch (AuthenticationException e) {
			throw HttpException.badRequest(Message.INVALID_CREDENTIALS.getMessage());
		}
	}

}
