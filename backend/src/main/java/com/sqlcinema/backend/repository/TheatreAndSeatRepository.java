package com.sqlcinema.backend.repository;

import com.sqlcinema.backend.common.CustomLogger;
import com.sqlcinema.backend.model.PercentCoupon;
import com.sqlcinema.backend.model.Seat;
import com.sqlcinema.backend.model.Theatre;
import com.sqlcinema.backend.model.response.TheatreResponse;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sqlcinema.backend.common.Constants.createObjectArray;

@Repository
@AllArgsConstructor
public class TheatreAndSeatRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomLogger logger;

    public List<TheatreResponse> getTheatres() {
        String query = "SELECT T.*,count(*) as seatSize FROM Theatre T NATURAL INNER JOIN Seat GROUP BY T.theatre_id";
        logger.sqlLog(query, createObjectArray(""));
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TheatreResponse.class));
    }

    public TheatreResponse getTheatreById(int theatreId) {
        String query = "SELECT T.*,count(*) as seatSize FROM (SELECT * FROM Theatre T WHERE T.theatre_id= ?) T NATURAL INNER JOIN Seat GROUP BY T.theatre_id";
        logger.sqlLog(query, createObjectArray(""));
        try{
            return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(TheatreResponse.class),theatreId);
        }
        catch (Exception E){
            return null;
        }
    }

    public TheatreResponse createTheatre(Theatre theatreRequest) {
        String createQuery="CALL create_theatre(\""+theatreRequest.getName()+"\")";
        jdbcTemplate.execute(createQuery);
        logger.sqlLog(createQuery);
        return jdbcTemplate.queryForObject("SELECT T.*,0 AS seatSize FROM Theatre T WHERE T.name=?", BeanPropertyRowMapper.newInstance(TheatreResponse.class),theatreRequest.getName());
    }

    public TheatreResponse updateTheatre(int theatreId, Theatre theatreRequest) {
        String updateQuery="CALL update_theatre("+theatreId+",\""+theatreRequest.getName()+"\","+theatreRequest.getIsAvailable()+")";
        jdbcTemplate.execute(updateQuery);
        logger.sqlLog(updateQuery);
        return getTheatreById(theatreId);
    }

    public void deleteTheatre(int theatreId) {
        String deleteQuery="CALL delete_theatre("+theatreId+")";
        jdbcTemplate.execute(deleteQuery);
        logger.sqlLog(deleteQuery);
    }
    public List<Seat> getSeats(int theatreId){
        String query="SELECT * FROM Seat WHERE theatre_id=?";
        logger.sqlLog(query, createObjectArray(""));
        try{
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Seat.class),theatreId);
        }
        catch (Exception E){
            return Collections.emptyList();
        }
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
    public void deleteSeat(int theatreId,String seatCode) {
        String deleteQuery="CALL delete_seat("+theatreId+",\""+seatCode+"\")";
        jdbcTemplate.execute(deleteQuery);
        logger.sqlLog(deleteQuery);
    }
    public Seat updateSeat(int theatreId, Seat seatRequest) {
        String updateQuery="CALL update_seat("+theatreId+",\""+seatRequest.getSeatCode()+"\",\""+seatRequest.getSeatType()+"\")";
        jdbcTemplate.execute(updateQuery);
        logger.sqlLog(updateQuery);
        seatRequest.setTheatreId(theatreId);
        return seatRequest;
    }
    public Seat createSeat(int theatreId,Seat seatRequest) {
        String createQuery="CALL create_seat("+theatreId+",\""+seatRequest.getSeatCode()+"\",\""+seatRequest.getSeatType()+"\")";
        jdbcTemplate.execute(createQuery);
        logger.sqlLog(createQuery);
        seatRequest.setTheatreId(theatreId);
        return seatRequest;
    }

    public Seat getSeatById(int theatreId, String seatCode) {
        String query = "SELECT * FROM Seat WHERE theatre_id=? and seat_code=?";
        logger.sqlLog(query, createObjectArray(""));
        try{
            return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Seat.class),theatreId,seatCode);
        }
        catch (Exception E){
            return null;
        }
    }
}
