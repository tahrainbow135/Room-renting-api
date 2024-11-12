package com.tuanh.repository;

import com.tuanh.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Integer> {
	List<House> findAllByOwnerId(Integer ownerId);
}
