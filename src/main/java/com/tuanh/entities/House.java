package com.tuanh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "house")
public class House extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String addressLine;

	@Column(nullable = false)
	private String ward;

	@Column(nullable = false)
	private String district;

	@Column(nullable = false)
	private String city;

	@Column(name = "floor_count", nullable = false)
	private Integer floorCount;

	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	@OneToMany(mappedBy = "house")
	private List<Room> rooms;
}
