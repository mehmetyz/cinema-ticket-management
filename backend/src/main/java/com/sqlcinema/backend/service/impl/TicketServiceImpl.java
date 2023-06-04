package com.sqlcinema.backend.service.impl;

import com.sqlcinema.backend.model.Seat;
import com.sqlcinema.backend.model.Ticket;
import com.sqlcinema.backend.model.response.AirTimesResponse;
import com.sqlcinema.backend.model.response.AvailableMoviesResponse;
import com.sqlcinema.backend.model.response.TicketResponse;
import com.sqlcinema.backend.repository.TicketRepository;
import com.sqlcinema.backend.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    @Override
    public List<TicketResponse> getTickets() {
        return ticketRepository.getTickets();    }

    @Override
    public TicketResponse getTicketById(int ticketId) {
        return ticketRepository.getTicketById(ticketId);
    }

    @Override
    public TicketResponse createTicket(Ticket ticketRequest) {
        return ticketRepository.createTicket(ticketRequest);
    }

    @Override
    public TicketResponse updateTicket(int ticketId, Ticket ticketRequest) {
        return ticketRepository.updateTicket(ticketId,ticketRequest);
    }

    @Override
    public void deleteTicket(int ticketId) {
        ticketRepository.deleteTicket(ticketId);

    }

    @Override
    public List<AvailableMoviesResponse> getAvailableMovies() {
        return ticketRepository.getAvailableMovies();
    }

    @Override
    public List<AirTimesResponse> getAirTimes(String title) {
        return ticketRepository.getAirTimes(title);
    }

    @Override
    public List<Seat> getAvailableSeats(int ticketId) {
        return ticketRepository.getAvailableSeats(ticketId);
    }
}
