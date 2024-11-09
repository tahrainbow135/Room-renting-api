package com.tuanh.services;

import com.tuanh.constants.Message;
import com.tuanh.dtos.LoginResponseDto;
import com.tuanh.dtos.UserDto;
import com.tuanh.entities.ApplicationUser;
import com.tuanh.entities.Role;
import com.tuanh.exceptions.HttpException;
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

	public ApplicationUser registerUser(String username, String password) {
		String encodedPassword = passwordEncoder.encode(password);
		Role userRole = roleRepository.findByAuthority("USER").get();

		Set<Role> authorities = new HashSet<>();

		authorities.add(userRole);

		ApplicationUser user = new ApplicationUser();
		user.setUsername(username);
		user.setPassword(encodedPassword);
		user.setAuthorities(authorities);

		return userRepository.save(user);
	}

	public LoginResponseDto loginUser(String username, String password) {
		try {
			Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
			);

			String token = tokenService.generateJwt(auth);

			ApplicationUser user = userRepository.findByUsername(username)
				.orElseThrow(() -> HttpException.notFound(Message.USER_NOT_FOUND.getMessage()));

			UserDto userDto = new UserDto();
			userDto.setUsername(user.getUsername());
			userDto.setAuthorities(user.getAuthorities());

			return new LoginResponseDto(userDto, token);

		} catch (AuthenticationException e) {
			return new LoginResponseDto(null, "");
		}
	}

}
