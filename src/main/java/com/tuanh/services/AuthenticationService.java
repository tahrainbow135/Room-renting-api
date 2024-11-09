package com.tuanh.services;

import com.tuanh.dtos.LoginResponseDto;
import com.tuanh.models.ApplicationUser;
import com.tuanh.models.Role;
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

		return userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities));
	}

	public LoginResponseDto loginUser(String username, String password) {
		try {
			Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
			);

			String token = tokenService.generateJwt(auth);

			return new LoginResponseDto(userRepository.findByUsername(username).get(), token);

		} catch (AuthenticationException e) {
			return new LoginResponseDto(null, "");
		}
	}

}
