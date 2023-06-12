package com.sqlcinema.backend.service.impl;

import com.sqlcinema.backend.model.*;
import com.sqlcinema.backend.model.request.ReservationRequest;
import com.sqlcinema.backend.model.response.AirTimesResponse;
import com.sqlcinema.backend.model.response.AvailableMoviesResponse;
import com.sqlcinema.backend.model.response.SeatResponse;
import com.sqlcinema.backend.model.response.TicketResponse;
import com.sqlcinema.backend.repository.CouponRepository;
import com.sqlcinema.backend.repository.TicketRepository;
import com.sqlcinema.backend.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final CouponRepository couponRepository;

    @Override
    public List<TicketResponse> getTickets(int movieId) {
        return ticketRepository.getTickets(movieId);
    }

    @Override
    public List<SeatResponse> getSeats(int ticketId) {
        return ticketRepository.getSeats(ticketId);
    }

    @Override
    public Reservation reserveSeats(int userId, ReservationRequest reservation) {
        
        checkReservation(reservation);
        
        Reservation newReservation = ticketRepository.reserveSeats(userId, reservation);
        if (newReservation == null) {
            return null;
        }
        try {

            
            checkCreditCard(reservation.getPaymentInfo().getCardNumber());
            
            float price = calculatePrice(reservation);

            ticketRepository.approvePayment(newReservation.getReservationId(), getPaymentType(reservation), price);

            if (reservation.getPaymentInfo().getCouponCode() != null) {
                couponRepository.useCoupon(reservation.getPaymentInfo().getCouponCode());
            }
        } catch (Exception e) {
            ticketRepository.cancelPayment(newReservation.getReservationId());
            throw e;
        }

        return newReservation;
    }

    private void checkReservation(ReservationRequest reservation) {
        ticketRepository.checkReservation(reservation);
    }

    private float calculatePrice(ReservationRequest reservation) {
        float price = 0;

        for (String seat : reservation.getSeatCodes()) {
            price += ticketRepository.getSeatPrice(reservation.getTicketId(), seat);
        }

        if (reservation.getPaymentInfo().getCouponCode() != null) {
            Coupon coupon = couponRepository.getCouponByCode(reservation.getPaymentInfo().getCouponCode());
            
            if (coupon == null || !coupon.isValid()) {
                return price;
            }

            if (coupon instanceof AmountCoupon amountCoupon) {
                price = amountCoupon.getMinPrice() <= price ? Math.max(0, price - amountCoupon.getAmount()) : price;
            } else if (coupon instanceof PercentCoupon percentCoupon) {
                price *= percentCoupon.getUpTo() >= price ? 1 - (percentCoupon.getRate()  / 100): 1;
            }
        }

        return price;
    }


    private void checkCreditCard(String creditCardNumber) {
        if (creditCardNumber.length() != 16) {
            throw new IllegalArgumentException("Credit card number must be 16 digits long");
        }

        if (!creditCardNumber.matches("[0-9]+")) {
            throw new IllegalArgumentException("Credit card number must only contain digits");
        }

        int sum = 0;
        for (int i = 0; i < creditCardNumber.length(); i++) {
            int digit = Character.getNumericValue(creditCardNumber.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        if (sum % 10 != 0) {
            throw new IllegalArgumentException("Invalid credit card number");
        }


    }
    
    private String getPaymentType(ReservationRequest reservation) {
        if (reservation.getPaymentInfo().getCardNumber().startsWith("4")) {
            return "Visa";
        } else if (reservation.getPaymentInfo().getCardNumber().startsWith("5")) {
            return "MasterCard";
        } else {
            return "American Express";
        }
    }
}
