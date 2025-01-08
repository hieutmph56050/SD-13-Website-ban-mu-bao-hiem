package com.example.saferide.repository;

import com.example.saferide.entity.HoanTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoanTraRepository extends JpaRepository<HoanTra, Integer> {
}
