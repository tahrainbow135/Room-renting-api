package com.tuanh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	private Integer electricityPrice;
	@Column(nullable = false)
	private Integer waterPrice;
	@Column(nullable = false)
	private Integer roomPrice;
	@Column(nullable = false)
	private Integer internetPrice;
	@Column(nullable = false)
	private Integer generalPrice;
	@Column(nullable = false, unique = true)
	private LocalDate dueDate;
	private LocalDate paymentDate;
	@Column(nullable = false)
	private Integer electricityNum;
	@Column(nullable = false)
	private Integer waterNum;

	@ManyToOne
	@JoinColumn(name = "rented_room_id", referencedColumnName = "id")
	private RentedRoom rentedRoom;
}
