package com.tuanh.services;


import com.tuanh.constants.JwtConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationManager {
	private Map<String, Object> claims;
	private Boolean authenticated;

	public Boolean isAuthenticated() {
		initClaims();
		return authenticated;
	}

	public Integer getUserId() {
		initClaims();
		Long userId = getClaim(JwtConstants.JWT_USER_ID_CLAIM);
		return (int) (long) Optional.ofNullable(userId).orElse(0L);
	}

	public <T> T getClaim(String key) {
		initClaims();
		try {
			return (T) claims.get(key);
		} catch (ClassCastException e) {
			log.error("Error casting claim value", e);
		}
		return null;
	}

	public List<String> getUserRoles() {
		initClaims();
		String roles = getClaim(JwtConstants.JWT_PERMISSIONS_CLAIM);
		String[] rolesArray = roles.split(" ");
		return List.of(rolesArray);
	}

	private void initClaims() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof JwtAuthenticationToken)) {
			authenticated = false;
			claims = Map.of();
			return;
		}
		Jwt jwt = (Jwt) authentication.getPrincipal();
		authenticated = true;
		claims = jwt.getClaims();
	}
}
