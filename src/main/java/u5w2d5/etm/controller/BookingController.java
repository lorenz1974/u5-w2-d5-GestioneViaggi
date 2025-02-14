package u5w2d5.etm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import u5w2d5.etm.model.Booking;
import u5w2d5.etm.response.CreateResponse;
import u5w2d5.etm.service.BookingService;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public List<Booking> getAll() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateResponse createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
        Booking updatedBooking = bookingService.updateBooking(id, bookingDetails);
        return updatedBooking;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
