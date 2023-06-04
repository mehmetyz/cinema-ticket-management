package com.sqlcinema.backend.repository;

import com.sqlcinema.backend.common.CustomLogger;
import com.sqlcinema.backend.model.Seat;
import com.sqlcinema.backend.model.Ticket;
import com.sqlcinema.backend.model.response.AirTimesResponse;
import com.sqlcinema.backend.model.response.AvailableMoviesResponse;
import com.sqlcinema.backend.model.response.TicketResponse;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static com.sqlcinema.backend.common.Constants.createObjectArray;

@Repository
@AllArgsConstructor
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomLogger logger;

    public List<TicketResponse> getTickets() {
        String query = "SELECT T.*,M.title,Th.name FROM Ticket T NATURAL INNER JOIN Movie M NATURAL INNER JOIN Theatre Th";
        logger.sqlLog(query, createObjectArray(""));
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TicketResponse.class));
    }

    public TicketResponse getTicketById(int ticketId) {
        String query = "SELECT T.*,M.title,Th.name FROM (SELECT * FROM Ticket T WHERE T.ticket_id=?) T NATURAL INNER JOIN Movie M NATURAL INNER JOIN Theatre Th";
        logger.sqlLog(query, createObjectArray(""));
        try{
            return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(TicketResponse.class),ticketId);
        }
        catch (Exception E){
            return null;
        }
    }

    public TicketResponse createTicket(Ticket ticketRequest) {
        String createQuery="CALL create_ticket("+ticketRequest.getMovieId()+","+ticketRequest.getTheatreId()+",STR_TO_DATE(\""+new SimpleDateFormat("dd,MM,yyyy").format(ticketRequest.getShowTime())+"\",\"%d,%m,%Y\")"+","+ticketRequest.getPrice()+")";
        jdbcTemplate.execute(createQuery);
        logger.sqlLog(createQuery);
        return getTicketById(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID(ticket_id) from ticket order by LAST_INSERT_ID(ticket_id) desc limit 1;",int.class));
    }

    public TicketResponse updateTicket(int ticketId, Ticket ticketRequest) {
        String updateQuery="CALL update_ticket("+ticketId+","+ticketRequest.getTheatreId()+",STR_TO_DATE(\""+new SimpleDateFormat("dd,MM,yyyy").format(ticketRequest.getShowTime())+"\",\"%d,%m,%Y\")"+","+ticketRequest.getPrice()+")";
        jdbcTemplate.execute(updateQuery);
        logger.sqlLog(updateQuery);
        return getTicketById(ticketId);
    }

    public void deleteTicket(int ticketId) {
        String deleteQuery="CALL delete_ticket(\""+ticketId+"\")";
        jdbcTemplate.execute(deleteQuery);
        logger.sqlLog(deleteQuery);
    }

    public List<AvailableMoviesResponse> getAvailableMovies() {
        String query = "SELECT * FROM available_movies";
        logger.sqlLog(query, createObjectArray(""));
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(AvailableMoviesResponse.class));
    }

    public List<AirTimesResponse> getAirTimes(String title) {
        String query = "SELECT * FROM get_air_times WHERE title=?";
        logger.sqlLog(query, createObjectArray(""));
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(AirTimesResponse.class),title);
    }
    public List<Seat> getAvailableSeats(int ticketId){
        String query="SELECT * FROM available_seats WHERE ticket_id=?";
        logger.sqlLog(query, createObjectArray(""));
        try{
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Seat.class),ticketId);
        }
        catch (Exception E){
            return Collections.emptyList();
        }
    }
}
