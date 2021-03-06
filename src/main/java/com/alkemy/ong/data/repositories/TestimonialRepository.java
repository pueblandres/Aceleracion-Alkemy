package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.TestimonialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepository extends JpaRepository<TestimonialEntity, Long> {
}
