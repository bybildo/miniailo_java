package com.example.lab_8.repository;

import com.example.lab_8.entity.Ticket;
import com.example.lab_8.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventNameContainingIgnoreCaseAndStatusOrderByNumberAsc(String eventName, TicketStatus status);
    List<Ticket> findByEventIdAndStatusOrderByNumberAsc(Long eventId, TicketStatus status);
}