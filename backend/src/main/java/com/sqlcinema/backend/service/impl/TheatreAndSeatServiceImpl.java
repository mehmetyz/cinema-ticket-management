package com.sqlcinema.backend.service.impl;

import com.sqlcinema.backend.model.Seat;
import com.sqlcinema.backend.model.Theatre;
import com.sqlcinema.backend.model.response.TheatreResponse;
import com.sqlcinema.backend.repository.TheatreAndSeatRepository;
import com.sqlcinema.backend.service.TheatreAndSeatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class TheatreAndSeatServiceImpl implements TheatreAndSeatService {
    private final TheatreAndSeatRepository theatreAndSeatRepository;

    @Override
    public List<TheatreResponse> getTheatres() {
        return theatreAndSeatRepository.getTheatres();
    }

    @Override
    public TheatreResponse getTheatreById(int theatreId) {
        return theatreAndSeatRepository.getTheatreById(theatreId);
    }

    @Override
    public TheatreResponse createTheatre(Theatre theatreRequest) {
        return theatreAndSeatRepository.createTheatre(theatreRequest);
    }

    @Override
    public TheatreResponse updateTheatre(int theatreId, Theatre theatreRequest) {
        return theatreAndSeatRepository.updateTheatre(theatreId,theatreRequest);
    }

    @Override
    public void deleteTheatre(int theatreId) {
        theatreAndSeatRepository.deleteTheatre(theatreId);
    }

    @Override
    public List<Seat> getSeats(int theatreId) {
        return theatreAndSeatRepository.getSeats(theatreId);
    }

    @Override
    public void deleteSeat(int theatreId, String seatCode) {
        theatreAndSeatRepository.deleteSeat(theatreId,seatCode);
    }

    @Override
    public Seat updateSeat(int theatreId, Seat seatRequest) {
        return theatreAndSeatRepository.updateSeat(theatreId,seatRequest);
    }

    @Override
    public Seat createSeat(int theatreId,Seat seatRequest) {
        return theatreAndSeatRepository.createSeat(theatreId,seatRequest);
    }

    @Override
    public Seat getSeatById(int theatreId, String seatCode) {
        return theatreAndSeatRepository.getSeatById(theatreId,seatCode);
    }
}
