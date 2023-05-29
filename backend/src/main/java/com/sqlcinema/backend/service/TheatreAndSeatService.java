package com.sqlcinema.backend.service;

import com.sqlcinema.backend.model.Seat;
import com.sqlcinema.backend.model.Theatre;
import com.sqlcinema.backend.model.response.TheatreResponse;

import java.util.List;

public interface TheatreAndSeatService {
    List<TheatreResponse> getTheatres();

    TheatreResponse getTheatreById(int theatreId);

    TheatreResponse createTheatre(Theatre theatreRequest);

    TheatreResponse updateTheatre(int theatreId, Theatre theatreRequest);

    void deleteTheatre(int theatreId);
    List<Seat> getSeats(int theatreId);
    void deleteSeat(int theatreId,String seatCode);
    Seat updateSeat(int theatreId, Seat seatRequest);
    Seat createSeat(int theatreId,Seat seatRequest);
    Seat getSeatById(int theatreId,String seatCode);

}
