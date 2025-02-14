package u5w2d5.etm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import u5w2d5.etm.model.Trip;
import u5w2d5.etm.response.CreateResponse;
import u5w2d5.etm.service.TripService;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    public List<Trip> getAll() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateResponse createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Trip updateTrip(@PathVariable Long id, @RequestBody Trip tripDetails) {
        Trip updatedTrip = tripService.updateTrip(id, tripDetails);
        return updatedTrip;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }
}