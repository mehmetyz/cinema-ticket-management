package com.sqlcinema.backend.controller;

import com.sqlcinema.backend.model.Seat;
import com.sqlcinema.backend.model.Ticket;
import com.sqlcinema.backend.model.response.AirTimesResponse;
import com.sqlcinema.backend.model.response.AvailableMoviesResponse;
import com.sqlcinema.backend.model.response.TicketResponse;
import com.sqlcinema.backend.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/ticket")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<TicketResponse>> getTicket() {
        return ok(ticketService.getTickets());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable("ticketId") int ticketId) {
        TicketResponse existing = ticketService.getTicketById(ticketId);
        if (existing == null) {
            return notFound().build();
        }
        return ok(existing);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<TicketResponse> createTicket(@RequestBody Ticket ticketRequest) {
        return ok(ticketService.createTicket(ticketRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{ticketId}")
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable("ticketId") int ticketId, @RequestBody Ticket ticketRequest) {
        TicketResponse existing = ticketService.getTicketById(ticketId);
        if (existing == null) {
            return notFound().build();
        }
        return ok(ticketService.updateTicket(ticketId, ticketRequest));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("ticketId") int ticketId) {
        Ticket existing = ticketService.getTicketById(ticketId);
        if (existing == null) {
            return notFound().build();
        }
        ticketService.deleteTicket(ticketId);
        return ok().build();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    @GetMapping("/availableMovies")
    public ResponseEntity<List<AvailableMoviesResponse>> getAvailableMovies(){
        return ok(ticketService.getAvailableMovies());
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    @GetMapping("/airTimes/{title}")
    public ResponseEntity<List<AirTimesResponse>> getAirTimes(@PathVariable("title") String title){
        return ok(ticketService.getAirTimes(title));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    @GetMapping("/{ticketId}/seats")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable("ticketId") int ticketId){
        return ok(ticketService.getAvailableSeats(ticketId));
    }
}
