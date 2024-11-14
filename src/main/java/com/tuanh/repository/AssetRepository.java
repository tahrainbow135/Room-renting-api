package com.tuanh.repository;

import com.tuanh.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
	List<Asset> findAllByRoomId(Integer id);
}
