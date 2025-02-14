package u5w2d5.etm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import u5w2d5.etm.model.Booking;
import u5w2d5.etm.repository.BookingRepository;
import u5w2d5.etm.response.CreateResponse;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class BookingService {

    private final BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));
    }

    public CreateResponse createBooking(Booking booking) {
        return new CreateResponse(bookingRepository.save(booking).getId());
    }

    public Booking updateBooking(long id, Booking updatedBooking) {
        Booking booking = getBookingById(id);
        booking.setTrip(updatedBooking.getTrip());
        booking.setEmployee(updatedBooking.getEmployee());
        booking.setRequestDate(updatedBooking.getRequestDate());
        return bookingRepository.save(booking);

    }

    public void deleteBooking(long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }
}
