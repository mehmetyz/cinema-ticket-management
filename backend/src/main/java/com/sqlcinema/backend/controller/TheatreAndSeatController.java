package com.sqlcinema.backend.controller;

import com.sqlcinema.backend.model.Seat;
import com.sqlcinema.backend.model.Theatre;
import com.sqlcinema.backend.model.response.TheatreResponse;
import com.sqlcinema.backend.service.TheatreAndSeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/TheatreAndSeat")
@AllArgsConstructor
public class TheatreAndSeatController {
    private final TheatreAndSeatService theatreAndSeatService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/Theatre")
    public ResponseEntity<List<TheatreResponse>> getTheatre() {
        return ok(theatreAndSeatService.getTheatres());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/Theatre/{theatreId}")
    public ResponseEntity<TheatreResponse> getTheatreById(@PathVariable("theatreId") int theatreId) {
        TheatreResponse existing = theatreAndSeatService.getTheatreById(theatreId);
        if (existing == null) {
            return notFound().build();
        }
        return ok(existing);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/Theatre")
    public ResponseEntity<TheatreResponse> createTheatre(@RequestBody Theatre theatreRequest) {
        return ok(theatreAndSeatService.createTheatre(theatreRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/Theatre/{theatreId}")
    public ResponseEntity<TheatreResponse> updateTheatre(@PathVariable("theatreId") int theatreId, @RequestBody Theatre theatreRequest) {
        TheatreResponse existing = theatreAndSeatService.getTheatreById(theatreId);
        if (existing == null) {
            return notFound().build();
        }
        return ok(theatreAndSeatService.updateTheatre(theatreId, theatreRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/Theatre/{theatreId}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable("theatreId") int theatreId) {
        Theatre existing = theatreAndSeatService.getTheatreById(theatreId);
        if (existing == null) {
            return notFound().build();
        }
        theatreAndSeatService.deleteTheatre(theatreId);
        return ok().build();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/Seat/{theatreId}")
    public ResponseEntity<List<Seat>> getSeats(@PathVariable("theatreId") int theatreId) {
        return ok(theatreAndSeatService.getSeats(theatreId));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/Seat/{theatreId}")
    public ResponseEntity<Seat> createSeat(@PathVariable("theatreId") int theatreId,@RequestBody Seat seatRequest) {
        return ok(theatreAndSeatService.createSeat(theatreId,seatRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/Seat/{theatreId}")
    public ResponseEntity<Seat> updateSeat(@PathVariable("theatreId") int theatreId, @RequestBody Seat seatRequest) {
        Seat existing = theatreAndSeatService.getSeatById(theatreId,seatRequest.getSeatCode());
        if (existing == null) {
            return notFound().build();
        }
        return ok(theatreAndSeatService.updateSeat(theatreId, seatRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/Seat/{theatreId}")
    public ResponseEntity<Void> deleteSeat(@PathVariable("theatreId") int theatreId, @RequestBody Seat seatCode) {
        Seat existing = theatreAndSeatService.getSeatById(theatreId, seatCode.getSeatCode());
        if (existing == null) {
            return notFound().build();
        }
        theatreAndSeatService.deleteSeat(theatreId,seatCode.getSeatCode());
        return ok().build();
    }

}
