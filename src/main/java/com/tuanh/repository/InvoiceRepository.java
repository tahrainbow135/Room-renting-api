package com.tuanh.repository;

import com.tuanh.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	List<Invoice> findAllByRentedRoomId(Integer rentedRoomId);
}
