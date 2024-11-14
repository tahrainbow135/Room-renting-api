package com.tuanh.repository;

import com.tuanh.entities.RentedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentedRoomRepository extends JpaRepository<RentedRoom, Integer> {
	List<RentedRoom> findAllByRoomId(Integer roomId);

	@Query("SELECT CASE WHEN COUNT(rr) > 0 THEN TRUE ELSE FALSE END FROM RentedRoom rr WHERE rr.room.id = ?1 AND ((rr.startDate <= ?2 AND rr.endDate >= ?2) OR (rr.startDate <= ?3 AND rr.endDate >= ?3) OR (rr.startDate >= ?2 AND rr.endDate <= ?3))")
	Boolean checkIfRoomIsRentedInPeriod(Integer roomId, LocalDate startDate, LocalDate endDate);
}
