package com.example.lab_8.repository;

import com.example.lab_8.entity.Event;
import com.example.lab_8.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByPlaceAndEventDate(Place place, LocalDate eventDate);
    List<Event> findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate currentDate);
    List<Event> findByNameContainingIgnoreCase(String name);
}