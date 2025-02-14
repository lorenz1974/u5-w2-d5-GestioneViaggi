package u5w2d5.etm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import u5w2d5.etm.model.Employee;
import u5w2d5.etm.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

}