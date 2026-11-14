package com.example.lab_8.service;

import com.example.lab_8.dto.EventCreationDTO;
import com.example.lab_8.dto.PlaceDTO;
import com.example.lab_8.dto.TicketPackDTO;
import com.example.lab_8.entity.*;
import com.example.lab_8.repository.EventRepository;
import com.example.lab_8.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public Event createEvent(EventCreationDTO dto) {
        PlaceDTO placeDto = dto.getPlace();
        if (placeDto == null || placeDto.getName() == null || placeDto.getName().isBlank()) {
            throw new IllegalArgumentException("Назва місця проведення є обов'язковою");
        }

        Place place = placeRepository.findByNameIgnoreCase(placeDto.getName().trim())
                .orElseGet(() -> {
                    Place newPlace = new Place();
                    newPlace.setName(placeDto.getName().trim());
                    newPlace.setAddress(placeDto.getAddress() != null ? placeDto.getAddress().trim() : "Адресу не вказано");
                    return placeRepository.save(newPlace);
                });

        if (eventRepository.existsByPlaceAndEventDate(place, dto.getEventDate())) {
            throw new IllegalStateException("У місці '" + place.getName() + "' на дату " + dto.getEventDate() + " вже запланована інша подія");
        }

        Event event = new Event();
        event.setName(dto.getName());
        event.setEventDate(dto.getEventDate());
        event.setPlace(place);

        List<Ticket> generatedTickets = new ArrayList<>();
        int seatNumber = 1;

        if (dto.getTicketPacks() != null) {
            for (TicketPackDTO pack : dto.getTicketPacks()) {
                if (pack.getCount() != null && pack.getCost() != null && pack.getCount() > 0) {
                    for (int i = 0; i < pack.getCount(); i++) {
                        Ticket ticket = new Ticket();
                        ticket.setCost(pack.getCost());
                        ticket.setNumber(seatNumber++);
                        ticket.setStatus(TicketStatus.FREE);
                        ticket.setEvent(event);
                        generatedTickets.add(ticket);
                    }
                }
            }
        }

        if (generatedTickets.isEmpty()) {
            throw new IllegalArgumentException("Подія повинна мати хоча б один квиток");
        }

        event.setTickets(generatedTickets);
        return eventRepository.save(event);
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate.now());
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Подію з ID " + id + " не знайдено"));
    }
}