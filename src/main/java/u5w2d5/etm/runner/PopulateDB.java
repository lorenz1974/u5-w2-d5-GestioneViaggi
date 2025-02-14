package u5w2d5.etm.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import u5w2d5.etm.model.*;
import u5w2d5.etm.service.*;
import u5w2d5.etm.exception.*;

@Order(1)
@Component
@Slf4j
@RequiredArgsConstructor
public class PopulateDB implements CommandLineRunner {

    private final Faker faker;
    private final EmployeeService employeeService;
    private final TripService tripService;
    private final BookingService bookingService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Populating Employees with fake data...");
        for (int i = 0; i < 10; i++) {
            String name = faker.name().firstName();
            String surname = faker.name().lastName();
            String email = name.replace(" ", "") +
                    "." +
                    surname.replace(" ", "") +
                    "@" +
                    faker.internet().domainName().replace(" ", "").toLowerCase();

            Employee employee = new Employee();
            employee.setUsername(email);
            employee.setFirstName(name);
            employee.setLastName(surname);
            employee.setEmail(email);

            log.debug("Creating employee: {}", employee);
            employeeService.createEmployee(employee);
        }
        log.info("Created employees");

        log.info("Populating Trips with fake data...");
        for (int i = 0; i < 5; i++) {
            String description = "Viaggio con destinazione " + faker.country().capital();
            LocalDate startDate = faker.date().future(30, java.util.concurrent.TimeUnit.DAYS).toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = startDate.plusDays(faker.number().numberBetween(1, 10));

            Trip trip = new Trip();
            trip.setDescription(description);
            trip.setStartDate(startDate);
            trip.setEndDate(endDate);

            int status = faker.number().numberBetween(0, 3);
            switch (status) {
                case 0:

                    trip.setStatus(TripStatus.SCHEDULED);
                    break;
                case 1:
                    trip.setStatus(TripStatus.IN_PROGRESS);
                    break;
                case 2:
                    trip.setStatus(TripStatus.COMPLETED);
                    break;
                case 3:
                    trip.setStatus(TripStatus.CANCELLED);
                    break;
                default:
                    break;
            }

            log.debug("Creating trip: {}", trip);

            tripService.createTrip(trip);
        }
        log.info("Created trips");

        log.info("Creating Bookings...");
        for (int i = 0; i < 30; i++) {
            Employee employee = employeeService.getEmployeeById((long) faker.number().numberBetween(1, 10));
            Trip trip = tripService.getTripById((long) faker.number().numberBetween(1, 5));

            Booking booking = new Booking();
            booking.setEmployee(employee);
            booking.setTrip(trip);
            booking.setRequestDate(faker.date().past(90, java.util.concurrent.TimeUnit.DAYS).toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            booking.setNotes(faker.lorem().sentence());

            log.debug("Creating booking: {}", booking);
            bookingService.createBooking(booking);
        }
        log.info("Created bookings");
    }

}
