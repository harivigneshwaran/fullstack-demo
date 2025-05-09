package com.example.fullstack_demo.service;

import com.example.fullstack_demo.model.Seat;
import com.example.fullstack_demo.repo.SeatRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusTicketBookingServices {

    @Autowired
    private SeatRepo repoObj;

    @Transactional
    public Seat bookSeat(Long seatId) {
        //fetch the existing seat by id
        Seat seat = repoObj.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with id " + seatId));

        System.out.println(Thread.currentThread().getName() + " fetched seat with version " + seat.getVersion());

        if (seat.getBooked()) {
            throw new RuntimeException("Seat already booked !");
        }
        //booking seat
        seat.setBooked(true);
        //version check will occurs here
        return repoObj.save(seat);
    }


    @Transactional
    public void bookSeatWithPessimistic(Long seatId) {

        System.out.println(Thread.currentThread().getName() + " is attempting to fetch the seat");

        //fetch the seat with Pessimistic lock
        Seat seat = repoObj.findByIdAndLock(seatId);

        System.out.println(Thread.currentThread().getName() + " acquired the lock for seat id " + seatId);

        if (seat.getBooked()) {
            System.out.println(Thread.currentThread().getName() + " failed Seat Id " + seatId + " is already booked ");
            throw new RuntimeException("Seat already booked !");
        }
        //booking seat
        System.out.println(Thread.currentThread().getName() + " booking the seat " + seatId);

        seat.setBooked(true);
        //version check will occurs here
        repoObj.save(seat);
        System.out.println(Thread.currentThread().getName() + " successfully book the seat with ID " + seatId);
    }


}
