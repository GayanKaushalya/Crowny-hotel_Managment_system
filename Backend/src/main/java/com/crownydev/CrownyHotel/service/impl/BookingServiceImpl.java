package com.crownydev.CrownyHotel.service.impl;

import com.crownydev.CrownyHotel.dto.BookingDTO;
import com.crownydev.CrownyHotel.dto.Response;
import com.crownydev.CrownyHotel.entity.Booking;
import com.crownydev.CrownyHotel.entity.Room;
import com.crownydev.CrownyHotel.entity.User;
import com.crownydev.CrownyHotel.exception.OurException;
import com.crownydev.CrownyHotel.repo.BookingRepository;
import com.crownydev.CrownyHotel.repo.RoomRepository;
import com.crownydev.CrownyHotel.repo.UserRepository;
import com.crownydev.CrownyHotel.service.interfac.IBookingService;
import com.crownydev.CrownyHotel.utils.Utils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Response saveBooking(Long roomId, Long userId, BookingDTO bookingRequest) {
        Response response = new Response();
        try {
            if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
                throw new IllegalArgumentException("Check-in date must come before check-out date");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

            // Check if the room is available for the requested dates
            boolean roomIsAvailable = isRoomAvailable(room, bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
            if (!roomIsAvailable) {
                throw new OurException("Room not available for the selected date range");
            }


            Booking newBooking = new Booking();
            newBooking.setCheckInDate(bookingRequest.getCheckInDate());
            newBooking.setCheckOutDate(bookingRequest.getCheckOutDate());
            newBooking.setNumOfAdults(bookingRequest.getNumOfAdults());
            newBooking.setNumOfChildren(bookingRequest.getNumOfChildren());
            newBooking.setTotalNumOfGuests(bookingRequest.getTotalNumOfGuests());
            newBooking.setRoom(room);
            newBooking.setUser(user);
            newBooking.setBookingConfirmationCode(Utils.generateRandomAlphanumeric(10));

            bookingRepository.save(newBooking);

            response.setStatusCode(200);
            response.setMessage("Room booked successfully");
            response.setBookingConfirmationCode(newBooking.getBookingConfirmationCode());

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error booking room: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();
        try {
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new OurException("Booking not found with confirmation code: " + confirmationCode));

            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRoom(booking, true);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setBooking(bookingDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error finding booking: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();
        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

            // Map the list of entities to a list of DTOs, including user and room info
            List<BookingDTO> bookingDTOList = bookingList.stream()
                    .map(booking -> Utils.mapBookingEntityToBookingDTOPlusBookedRoom(booking, true))
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setBookingList(bookingDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all bookings: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();
        try {
            bookingRepository.findById(bookingId).orElseThrow(() -> new OurException("Booking does not exist"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Booking cancelled successfully");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error cancelling booking: " + e.getMessage());
        }
        return response;
    }
    // Add this entire method inside your BookingServiceImpl class

    @Override
    public Response findBookingByUserId(Long userId) {
        Response response = new Response();
        try {
            // Use the new repository method
            List<Booking> bookingList = bookingRepository.findByUserId(userId);

            // Map the list of entities to a list of DTOs
            List<BookingDTO> bookingDTOList = bookingList.stream()
                    .map(booking -> Utils.mapBookingEntityToBookingDTOPlusBookedRoom(booking, true))
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setBookingList(bookingDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting user bookings: " + e.getMessage());
        }
        return response;
    }

    // A much simpler and more correct way to check for date overlaps
    private boolean isRoomAvailable(Room room, java.time.LocalDate checkInDate, java.time.LocalDate checkOutDate) {
        List<Booking> existingBookings = room.getBookings();
        for (Booking existingBooking : existingBookings) {
            // Overlap happens if: (StartA < EndB) and (EndA > StartB)
            if (checkInDate.isBefore(existingBooking.getCheckOutDate()) &&
                    checkOutDate.isAfter(existingBooking.getCheckInDate())) {
                return false; // Found an overlapping booking
            }
        }
        return true; // No overlaps found, room is available
    }
}