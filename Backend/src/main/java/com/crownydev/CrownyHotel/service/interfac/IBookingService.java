package com.crownydev.CrownyHotel.service.interfac;

import com.crownydev.CrownyHotel.dto.BookingDTO;
import com.crownydev.CrownyHotel.dto.Response;

public interface IBookingService {

    // We accept a DTO, not an Entity, for better practice.
    Response saveBooking(Long roomId, Long userId, BookingDTO bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

    Response findBookingByUserId(Long userId);
}