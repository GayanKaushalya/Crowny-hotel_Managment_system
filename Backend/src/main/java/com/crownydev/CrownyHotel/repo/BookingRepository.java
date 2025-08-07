// PASTE THIS FULL CODE INTO YOUR BookingRepository.java FILE

package com.crownydev.CrownyHotel.repo;

import com.crownydev.CrownyHotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Make sure this import is present
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);

    // *** THIS IS THE NEW METHOD ***
    // Finds all bookings associated with a specific user's ID.
    List<Booking> findByUserId(Long userId);
}