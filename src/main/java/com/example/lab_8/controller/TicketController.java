package com.example.lab_8.controller;

import com.example.lab_8.entity.Customer;
import com.example.lab_8.entity.Ticket;
import com.example.lab_8.service.CustomerService;
import com.example.lab_8.service.EventService;
import com.example.lab_8.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final CustomerService customerService;
    private final EventService eventService;

    @GetMapping("/tickets/search")
    public String searchFreeTicketsByEventName(@RequestParam String eventName, Model model) {
        List<Ticket> freeTickets = ticketService.findFreeTicketsByEventName(eventName);
        model.addAttribute("tickets", freeTickets);
        model.addAttribute("pageTitle", "Вільні квитки за пошуком: \"" + eventName + "\"");
        model.addAttribute("searchQuery", eventName);
        return "tickets";
    }

    @GetMapping("/tickets/event/{eventId}")
    public String getFreeTicketsForEvent(@PathVariable Long eventId, Model model) {
        var event = eventService.getEventById(eventId);
        List<Ticket> freeTickets = ticketService.findFreeTicketsByEventId(eventId);
        model.addAttribute("tickets", freeTickets);
        model.addAttribute("pageTitle", "Вільні квитки на подію: " + event.getName());
        model.addAttribute("eventId", eventId);
        return "tickets";
    }

    @PostMapping("/tickets/buy")
    public String buyTicket(@RequestParam Long ticketId, @RequestParam(required = false) String redirectUrl, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            Ticket soldTicket = ticketService.assignTicketToCustomerByEmail(ticketId, principal.getName());
            redirectAttributes.addFlashAttribute("successMessage",
                    "Ви успішно придбали квиток №" + soldTicket.getNumber() + "!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:" + (redirectUrl != null && !redirectUrl.isBlank() ? redirectUrl : "/events");
    }

    @GetMapping("/my-tickets")
    public String myTickets(Principal principal, Model model) {
        Customer currentCustomer = customerService.findByEmail(principal.getName());
        model.addAttribute("customer", currentCustomer);
        return "my-tickets";
    }
}