package com.tuanh;

import com.tuanh.entities.Role;
import com.tuanh.entities.User;
import com.tuanh.repository.RoleRepository;
import com.tuanh.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode) {
		return args -> {
			if (roleRepository.findAllByAuthorityIn(List.of("ADMIN", "OWNER", "USER")).size() >= 3) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("OWNER"));
			roleRepository.save(new Role("USER"));

			if (userRepository.findByUsername("admin").isPresent()) return;

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User();
			admin.setId(1);
			admin.setUsername("admin");
			admin.setFullName("System Admin");
			admin.setPassword(passwordEncode.encode("password"));
			admin.setAuthorities(roles);

			userRepository.save(admin);
		};
	}
}
