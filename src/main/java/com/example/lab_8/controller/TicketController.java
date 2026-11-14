package com.example.lab_8.controller;

import com.example.lab_8.entity.Ticket;
import com.example.lab_8.service.CustomerService;
import com.example.lab_8.service.EventService;
import com.example.lab_8.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final CustomerService customerService;
    private final EventService eventService;

    @GetMapping("/search")
    public String searchFreeTicketsByEventName(@RequestParam String eventName, Model model) {
        List<Ticket> freeTickets = ticketService.findFreeTicketsByEventName(eventName);
        model.addAttribute("tickets", freeTickets);
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("pageTitle", "Вільні квитки за пошуком: \"" + eventName + "\"");
        model.addAttribute("searchQuery", eventName);
        return "tickets";
    }

    @GetMapping("/event/{eventId}")
    public String getFreeTicketsForEvent(@PathVariable Long eventId, Model model) {
        var event = eventService.getEventById(eventId);
        List<Ticket> freeTickets = ticketService.findFreeTicketsByEventId(eventId);
        model.addAttribute("tickets", freeTickets);
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("pageTitle", "Вільні квитки на подію: " + event.getName());
        model.addAttribute("eventId", eventId);
        return "tickets";
    }

    @PostMapping("/buy")
    public String buyTicket(@RequestParam Long ticketId, @RequestParam Long customerId, @RequestParam(required = false) String redirectUrl, RedirectAttributes redirectAttributes) {
        try {
            Ticket soldTicket = ticketService.assignTicketToCustomer(ticketId, customerId);
            redirectAttributes.addFlashAttribute("successMessage", "Квиток №" + soldTicket.getNumber() + " успішно оформлено на покупця " + soldTicket.getCustomer().getName() + "!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:" + (redirectUrl != null && !redirectUrl.isBlank() ? redirectUrl : "/events");
    }
}