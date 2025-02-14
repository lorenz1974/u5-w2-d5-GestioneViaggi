package u5w2d5.etm.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import u5w2d5.etm.exception.ExceptionHandlerClass;
import u5w2d5.etm.model.*;
import u5w2d5.etm.repository.*;
import u5w2d5.etm.response.CreateResponse;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class TripService {

    private final TripRepository tripRepository;
    private final BookingRepository bookingRepository;
    private final EmployeeService employeeService;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with id: " + id));
    }

    public CreateResponse createTrip(Trip trip) {
        if (trip.getStartDate().isAfter(trip.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        return new CreateResponse(tripRepository.save(trip).getId());
    }

    public Trip updateTrip(Long id, Trip tripDetails) {
        if (tripDetails.getStartDate().isAfter(tripDetails.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        Trip trip = getTripById(id);
        trip.setDescription(tripDetails.getDescription());
        trip.setStartDate(tripDetails.getStartDate());
        trip.setEndDate(tripDetails.getEndDate());
        return tripRepository.save(trip);
    }

    public void deleteTrip(Long id) {
        Trip trip = getTripById(id);
        tripRepository.delete(trip);
    }

    public List<Trip> getEmployeeTrips(long employeeId) {

        List<Booking> bookings = bookingRepository.findByEmployeeId(employeeId);
        return bookings.stream().map(Booking::getTrip).toList();

    }
}