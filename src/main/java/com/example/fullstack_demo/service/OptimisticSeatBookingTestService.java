package com.example.fullstack_demo.service;

import com.example.fullstack_demo.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OptimisticSeatBookingTestService {

    @Autowired
    private BusTicketBookingServices busTcktBookingServ;

    public void testOptimisticLocking(Long seatId) throws InterruptedException {
        // 2 thread

        ExecutorService exc=Executors.newFixedThreadPool(100);
        for( int count=1;count<1000;count++){
            final int threadCount=count;
            exc.submit(() -> {
                System.out.println("Task " + threadCount + " is running on thread " + Thread.currentThread().getName());
                try {
                    System.out.println("Task"+ " is attempting to book the seat");
                    Seat seat = busTcktBookingServ.bookSeat(seatId);
                    System.out.println("Task " + threadCount+ " successfully booked the seat with version " + seat.getVersion());
                } catch (Exception ex) {
                    System.out.println("Task " + threadCount + " failed"+ex.getMessage());

                }
            });
        }
    }
}
