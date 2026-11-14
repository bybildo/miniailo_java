package com.example.lab_8.repository;

import com.example.lab_8.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>
{
    Optional<Place> findByNameIgnoreCase(String name);
}