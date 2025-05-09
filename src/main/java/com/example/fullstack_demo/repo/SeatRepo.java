package com.example.fullstack_demo.repo;

import com.example.fullstack_demo.model.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepo extends JpaRepository<Seat,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.id= :seatId")
    Seat findByIdAndLock(Long seatId);
}
