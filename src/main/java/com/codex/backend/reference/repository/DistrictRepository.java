package com.codex.backend.reference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codex.backend.reference.entity.table.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

}
