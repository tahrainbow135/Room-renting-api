package com.tuanh.repository;

import com.tuanh.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
	List<Room> findAllByHouseId(Integer houseId);
}
