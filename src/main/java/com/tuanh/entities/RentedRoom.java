package com.tuanh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rented_room")
public class RentedRoom extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	private String tenantName;
	@Column(nullable = false)
	private String tenantPhone;
	@Column(nullable = false)
	private Integer numberOfTenants;
	@Column(nullable = false)
	private LocalDate startDate;
	@Column(nullable = false)
	private LocalDate endDate;
	@Column(nullable = false)
	private Integer paymentDay;
	@Column(nullable = false)
	private String contractUrl;

	@Column(nullable = false)
	private Integer price;
	@Column(nullable = false)
	private Integer electricityPrice;
	@Column(nullable = false)
	private Integer waterPrice;
	@Column(nullable = false)
	private Integer internetPrice;
	@Column(nullable = false)
	private Integer generalPrice;
	@Column(nullable = false)
	private Integer initElectricityNum;
	@Column(nullable = false)
	private Integer initWaterNum;

	@ManyToOne
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private Room room;
}
