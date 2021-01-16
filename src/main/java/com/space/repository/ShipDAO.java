package com.space.repository;

import com.space.model.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipDAO extends JpaRepository<Ship, Integer> {
}