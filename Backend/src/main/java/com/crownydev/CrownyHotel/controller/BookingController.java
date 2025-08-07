package com.crownydev.CrownyHotel.controller;

import com.crownydev.CrownyHotel.dto.BookingDTO;
import com.crownydev.CrownyHotel.dto.Response;
import com.crownydev.CrownyHotel.service.interfac.IBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// @RestController tells Spring this class is an API controller that returns JSON data.
@RestController
// @RequestMapping("/bookings") means all URLs for this controller start with "/bookings".
// For example: http://localhost:8080/bookings/all
@RequestMapping("/bookings")
public class BookingController {

    // We need the BookingService to handle all the logic related to bookings.
    private final IBookingService bookingService;

    // Spring uses Dependency Injection to provide a BookingService object here.
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    // This method finds all bookings made by a specific user.
    // It's a GET request to a URL like "/bookings/user/123" where 123 is the user's ID.
    @GetMapping("/user/{userId}")
    // @PathVariable takes the 'userId' from the URL and uses it as a variable in the method.
    public ResponseEntity<Response> findBookingByUserId(@PathVariable Long userId) {
        // We ask the bookingService to find the bookings for this user.
        Response response = bookingService.findBookingByUserId(userId);
        // We send the result back to the frontend.
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // This method creates a new booking for a room.
    // It's a POST request to a URL like "/bookings/book-room/101/123".
    // @PreAuthorize checks if the user has the 'ADMIN' or 'USER' role before running this method. This is for security.
    @PostMapping("/book-room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBooking(@PathVariable Long roomId,
                                                @PathVariable Long userId,
                                                // @RequestBody takes the JSON data (like check-in/out dates) and turns it into a BookingDTO object.
                                                @RequestBody BookingDTO bookingRequest) {
        // We pass all the information to the bookingService to create the booking.
        Response response = bookingService.saveBooking(roomId, userId, bookingRequest);
        // We return the result of the operation.
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // This method gets a list of all bookings in the hotel.
    // It's a GET request to "/bookings/all".
    // @PreAuthorize makes sure only an ADMIN can access this. It's a protected endpoint.
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings() {
        // We ask the service to fetch all bookings.
        Response response = bookingService.getAllBookings();
        // And send them back.
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // This method finds a single booking using its unique confirmation code.
    // It's a GET request to a URL like "/bookings/confirmation/A1B2-C3D4".
    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        // We ask the service to find the booking by its code.
        Response response = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // This method cancels an existing booking.
    // It's a DELETE request to a URL like "/bookings/cancel/50".
    // @PreAuthorize allows both ADMINs and regular USERs to cancel bookings (likely their own).
    @DeleteMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId) {
        // We ask the service to perform the cancellation.
        Response response = bookingService.cancelBooking(bookingId);
        // We return a confirmation of the cancellation.
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}