package com.sqlcinema.backend.service;

import com.sqlcinema.backend.model.Ticket;
import com.sqlcinema.backend.model.response.AirTimesResponse;
import com.sqlcinema.backend.model.response.AvailableMoviesResponse;
import com.sqlcinema.backend.model.response.TicketResponse;

import java.util.List;

public interface TicketService {
    List<TicketResponse> getTickets();

    TicketResponse getTicketById(int ticketId);

    TicketResponse createTicket(Ticket ticketRequest);

    TicketResponse updateTicket(int ticketId, Ticket ticketRequest);

    void deleteTicket(int ticketId);

    List<AvailableMoviesResponse> getAvailableMovies();

    List<AirTimesResponse> getAirTimes();
}
