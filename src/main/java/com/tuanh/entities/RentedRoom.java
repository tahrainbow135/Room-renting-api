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

	private String tenantName;
	private String tenantPhone;
	private Integer numberOfTenants;
	private LocalDate startDate;
	private LocalDate endDate;
	private String contractUrl;

	private Integer price;
	private Integer electricityPrice;
	private Integer waterPrice;
	private Integer internetPrice;
	private Integer generalPrice;
	private Integer initElectricityNum;
	private Integer initWaterNum;

	@ManyToOne
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	private Room room;
}
