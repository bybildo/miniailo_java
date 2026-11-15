package com.example.lab_8.service;

import com.example.lab_8.entity.Customer;
import com.example.lab_8.entity.Ticket;
import com.example.lab_8.entity.TicketStatus;
import com.example.lab_8.repository.CustomerRepository;
import com.example.lab_8.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;

    public List<Ticket> findFreeTicketsByEventName(String eventName) {
        return ticketRepository.findByEventNameContainingIgnoreCaseAndStatusOrderByNumberAsc(
                eventName, TicketStatus.FREE
        );
    }

    public List<Ticket> findFreeTicketsByEventId(Long eventId) {
        return ticketRepository.findByEventIdAndStatusOrderByNumberAsc(eventId, TicketStatus.FREE);
    }

    @Transactional
    public Ticket assignTicketToCustomerByEmail(Long ticketId, String customerEmail) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Квиток з ID " + ticketId + " не знайдено"));

        if (ticket.getStatus() == TicketStatus.SOLD || ticket.getCustomer() != null) {
            throw new IllegalStateException("Квиток №" + ticket.getNumber() + " вже продано!");
        }

        Customer customer = customerRepository.findByEmailIgnoreCase(customerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Покупця з email " + customerEmail + " не знайдено"));

        ticket.setCustomer(customer);
        ticket.setStatus(TicketStatus.SOLD);

        return ticketRepository.save(ticket);
    }
}