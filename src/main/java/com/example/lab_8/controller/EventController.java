package com.example.lab_8.controller;

import com.example.lab_8.dto.EventCreationDTO;
import com.example.lab_8.dto.TicketPackDTO;
import com.example.lab_8.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping({"/", "/events"})
    public String listEvents(@RequestParam(required = false, defaultValue = "false") boolean showAll, Model model) {
        if (showAll)
            model.addAttribute("events", eventService.getAllEvents());
        else
            model.addAttribute("events", eventService.getUpcomingEvents());
        model.addAttribute("showAll", showAll);
        return "events";
    }

    @GetMapping("/events/new")
    public String showCreateEventForm(Model model) {
        EventCreationDTO dto = new EventCreationDTO();
        dto.getTicketPacks().add(new TicketPackDTO());
        dto.getTicketPacks().add(new TicketPackDTO());
        model.addAttribute("eventDto", dto);
        return "create-event";
    }

    @PostMapping("/events/create")
    public String createEvent(@ModelAttribute("eventDto") EventCreationDTO dto, RedirectAttributes redirectAttributes) {
        try {
            eventService.createEvent(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Подію та квитки успішно створено!");
            return "redirect:/events";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/events/new";
        }
    }
}