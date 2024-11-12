package com.tuanh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String authority;

	public Role() {
		super();
	}

	public Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

	@ManyToMany(mappedBy = "authorities")
	private Set<User> users;
}
